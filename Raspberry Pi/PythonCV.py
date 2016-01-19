'''
    This is the main module that handles and uses all other modules for the Python CV in the First Robotics Competition 2016.
    Created by: Leon Agmon Nacht.
    '''
from VisionManager import *
from NetworkManager import *
from FPSCounter import *
import cv2
import numpy as np

LB = np.array([75,230,235]) # Lower bond.
UB = np.array([105,255,255]) # Upper bond.
MBR = 500 # Minimum bounding rectangle
KH = 36 # Known height (of U).
KW = 50.8 # Known width  (of U).
FL = 1240 # Focal length.
RW = 100 # Robot width.
RH = 0 # Robot height.
RL = 100 # Robot length.
TH = 40 # The height of the tower
CUW = 260 # (Center U Width) The U width as it looks in the camera when it is in the center (in pixels).
CUWD = 240 # The distance which the CUW was calculated from.

FPSCounter = FPS()
FPSCounter.start()
brightness = -0.1
saturation = 15
exposure = -1

cam = cv2.VideoCapture(0)
cam.set(cv2.cv.CV_CAP_PROP_BRIGHTNESS, brightness)
cam.set(cv2.cv.CV_CAP_PROP_SATURATION, saturation)
cam.set(cv2.cv.CV_CAP_PROP_EXPOSURE, exposure)


visionManager = VisionManager(LB,UB,MBR,cam,KH,KW,FL,[RH,RW,RL],TH, CUW, CUWD)
#networkManager = NetworkManager("localhost",8080)

while True:
    visionManager.updateImage()
    visionManager.updateTowerScales()
    visionManager.updateRobotScales()
    FPSCounter.update()
    #For Fainaro:
    # if visionManager.currentImageObject is not None:
    #     (x,y,h,w) = (visionManager.currentImageObject.objectX,
    #                  visionManager.currentImageObject.objectY,
    #                  visionManager.currentImageObject.objectHeight,
    #                  visionManager.currentImageObject.objectWidth)
    #     cv2.rectangle(visionManager.maskedImage, (x, y), (x + w, y + h), (0, 255, 0), 2)
    
    # Send data to java process:
    #if visionManager.currentImageObject is not None:
    #    networkManager.sendData([visionManager.currentImageObject.distanceFromCamera,
    #                             visionManager.robotObject.angle,
    #                             visionManager.robotObject.XPosition,
    #                             visionManager.robotObject.Yposition])
    
    # Print results
    if visionManager.currentImageObject is not None:
        
        #print("D",visionManager.currentImageObject.distanceFromCamera)
        #print("A",visionManager.robotObject.angle)
        #print("X",visionManager.robotObject.XPosition)
        #print("Y",visionManager.robotObject.Yposition)
        #print("XShift",visionManager.currentImageObject.XShift)
        #print("YShift",visionManager.currentImageObject.YShift)
        
        FPSCounter.stop()
        print(FPSCounter.fps())
        #cv2.putText(visionManager.currentImage, "fps=%s" % (FPSCounter.fps()),\
        #            (10, 75), cv2.FONT_HERSHEY_SIMPLEX, 1, (255,255,255))

    #display:    
    cv2.imshow("Current Image",visionManager.currentImage)
    #cv2.imshow("Thresh Image", visionManager.threshImage)

    #save image:
    l = cv2.waitKey(5) & 0xFF
    if l == 115:
        cv2.imwrite("Current Image.png",visionManager.currentImage)
    '''
    #stop
    k = cv2.waitKey(5) & 0xFF
    if k == 27:
        break'''

cv2.destroyAllWindows()
visionManager.cam.release()
