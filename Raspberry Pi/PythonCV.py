'''
This is the main module that handles and uses all other modules for the Python CV
'''

from VisionManager import *
from NetworkManager import *
from FPSCounter import *
from time import sleep
import cv2
import numpy as np

#############
# constants #
#############

#Colors:
LB = np.array([37,0,234]) # Lower bond
UB = np.array([120,73,255]) # Upper bond

# Bounding rectangle
MBR = 500 # Minimum bounding rectangle
KH = 36 # Known height (of U).
KW = 50.8 # Known width  (of U).
FL = 1125 # Focal length.

# Robot:
RW = 100 # Robot width.
RH = 0 # Robot height.
RL = 100 # Robot length.

# Field:
TH = 50 # The height of the tower
CUW = 260 # (Center U Width) The U width as it looks in the camera when it is in the center (in pixels).
CUWD = 220 # The distance which the CUW was calculated from.

# Camera:
HAX = 53 # The head angle of the camera (x)
HAY = 40 # The head angle of the camera (y)

##################################
# Camera and FPS counter setting #
##################################

FPSCounter = FPS()
FPSCounter.start()

cam = cv2.VideoCapture(0)

brightness = -0.1
saturation = 15
exposure = -1 

cam.set(cv2.cv.CV_CAP_PROP_BRIGHTNESS, brightness)
cam.set(cv2.cv.CV_CAP_PROP_SATURATION, saturation)
cam.set(cv2.cv.CV_CAP_PROP_EXPOSURE, exposure) # not working on the old camera

visionManager = VisionManager(LB,UB,MBR,cam,KH,KW,FL,[RH,RW,RL],TH, CUW, CUWD)
#networkManager = NetworkManager("localhost",8080)

###################
# The code itself #
###################

while True:
    visionManager.updateImage()
    visionManager.updateTowerScales()
    visionManager.updateRobotScales()
    
    FPSCounter.update()   
    
    if visionManager.currentImageObject is not None: # if an object was detected

        ######################
        # Rectangle creation #
        ######################
        
        (x, y, h, w) = (visionManager.currentImageObject.objectX,
                     visionManager.currentImageObject.objectY,
                     visionManager.currentImageObject.objectHeight,
                     visionManager.currentImageObject.objectWidth)     
        #(x, y) = top left corner, h+ is down, w+ is right
        
        cv2.rectangle(visionManager.maskedImage, (x, y), (x + w, y + h), (0, 255, 0), 2)

        #########################################
        # Azimuthal and polar angle calculation #
        #########################################
        
        (xO, yO) = (x + w/2, y + h/2) # center of the object
        (xC, yC) = (visionManager.imageWidth/2, visionManager.imageHeight/2) # center of the frame

        azimuthalAngle = float((xO-xC)*HAX)/float(visionManager.imageWidth) # x, y plane
        polarAngle = float((yO-yC)*HAY)/float(visionManager.imageHeight) # azimuthal, z plane
        
        print (azimuthalAngle, polarAngle)

    #############################
    # Send data to java process #
    #############################
        
    # if visionManager.currentImageObject is not None:
    #    networkManager.sendData([visionManager.currentImageObject.distanceFromCamera,
    #                             visionManager.robotObject.angle,
    #                             visionManager.robotObject.XPosition,
    #                             visionManager.robotObject.Yposition])
    
    ###################
    # Results printer #
    ###################
    
    # if visionManager.currentImageObject is not None:
        # print("D",visionManager.currentImageObject.distanceFromCamera)
        # print("Angle: ",visionManager.robotObject.angle)
        # print("X",visionManager.robotObject.XPosition)
        # print("Y",visionManager.robotObject.Yposition)
        # print("XShift",visionManager.currentImageObject.XShift)
        # print("YShift",visionManager.currentImageObject.YShift)

        # put the FPS on the picture
        # cv2.putText(visionManager.currentImage, "fps=%s" % (FPSCounter.fps()),
        #       (10, 75), cv2.FONT_HERSHEY_SIMPLEX, 1, (255,255,255))
        
    FPSCounter.stop()
    print(FPSCounter.fps())
    
    # display:
    cv2.imshow("Current Image", visionManager.currentImage)
    # cv2.imshow("Thresh Image", visionManager.threshImage)
    cv2.imshow("Masked Image", visionManager.maskedImage)

    #########################
    # Wait for key pressing #
    #########################

    # choose only one to be active!
    
    # save image:
    k = cv2.waitKey(5) & 0xFF
    if k == 115: # pressed s
        if visionManager.currentImageObject is not None:
            cv2.imwrite("Current Image.png",visionManager.currentImage)
    # stop
    # k = cv2.waitKey(5) & 0xFF
    # if k == 27:
    #     break
    
    sleep (0.01) # so the pi won't crush and cut the connection

cv2.destroyAllWindows()
visionManager.cam.release()
