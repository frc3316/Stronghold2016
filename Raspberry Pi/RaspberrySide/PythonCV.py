'''
This is the main module that handles and uses all other modules for the Python CV
'''
import sys
from VisionManager import *
from NetworkManager import *
from FPSCounter import *
from time import sleep
import cv2
import numpy as np
from Utils import logger
from Utils import LockFile

#############
# constants #
#############

# Lock File:
lockFile = LockFile("LockFile.loc")

# Networking:

if len(sys.argv) > 1:
    JAVA_IP = sys.argv[1]
    print(JAVA_IP)
else:
    JAVA_IP = "127.0.0.1"

# Colors:
LB = np.array([37,0,231]) # Lower bond
UB = np.array([108,40,255]) # Upper bond

# Bounding rectangle
MBR = 500 # Minimum bounding rectangle
KH = 36 # Known height (of U).
KW = 50.8 # Known width  (of U).
FL = 1295 # Focal length.

# Robot:
RW = 100 # Robot width.
RH = 81 # Robot height.
RL = 100 # Robot length.

# Field:
TH = 255 # The height of the tower
CUW = 260 # (Center U Width) The U width as it looks in the camera when it is in the center (in pixels).
CUWD = 220 # The distance which the CUW was calculated from.

# Camera:
HAX = 53 # The head angle of the camera (x)
HAY = 40 # The head angle of the camera (y)

# Camera Settings:
brightness = -0.1
saturation = 15
exposure = -1

##################################
# Camera and FPS counter setting #
##################################
if __name__ == "__main__":
    if lockFile.is_locked():
        logger.error("Lock File Locked!")
        raise ValueError
    else:
        lockFile.lock()
    try:

        FPSCounter = FPS()
        FPSCounter.start()

        cam = cv2.VideoCapture(0)

        cam.set(cv2.cv.CV_CAP_PROP_BRIGHTNESS, brightness)
        cam.set(cv2.cv.CV_CAP_PROP_SATURATION, saturation)
        cam.set(cv2.cv.CV_CAP_PROP_EXPOSURE, exposure) # not working on the old camera

        visionManager = VisionManager(LB, UB, MBR, cam, KH, KW, FL, [RH,RW,RL], TH, CUW, CUWD, HAX, HAY)
        # networkManager = NetworkManager(JAVA_IP,8080)

        ###################
        # The code itself #
        ###################

        # CR: Add IPC lock
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
                logger.debug("----------------")
                logger.debug("Image Object (U):")
                logger.debug("----------------")
                logger.debug("Current U Width In Pixels: "+str(w))
                logger.debug("Current U Height In Pixels: "+str(h))
                logger.debug("Current U X In Pixels: "+str(x))
                logger.debug("Current U Y In Pixels: "+str(y))
                # (x, y) = top left corner, h+ is down, w+ is right
                cv2.rectangle(visionManager.maskedImage, (x, y), (x + w, y + h), (0, 255, 0), 2)

            #############################
            # Send data to java process #
            #############################

            # if visionManager.currentImageObject is not None:
            #    values = [visionManager.currentImageObject.distanceFromCamera,
            #              visionManager.robotObject.angle,
            #              visionManager.robotObject.XPosition,
            #              visionManager.robotObject.Yposition,
            #              visionManager.currentImageObject.azimuthalAngle,
            #              visionManager.currentImageObject.polarAngle]
            #    names = ["DFC", "A", "X", "Y", "AA", "PA"]
            #    networkManager.sendData(values, names)

            ###################
            # Results printer #
            ###################

            if visionManager.currentImageObject is not None:
                logger.debug("------------------")
                logger.debug("Robot Information:")
                logger.debug("------------------")
                logger.debug("Distance From Tower: " + str(visionManager.currentImageObject.distanceFromCamera))
                logger.debug("Angle From tower: " + str(visionManager.robotObject.angle))
                logger.debug("X Robot Position: " + str(visionManager.robotObject.XPosition))
                logger.debug("Y Robot Position: " + str(visionManager.robotObject.Yposition))
                logger.debug("AzimuthalAngle: " + str(visionManager.currentImageObject.azimuthalAngle))
                logger.debug("PolarAngle: " + str(visionManager.currentImageObject.polarAngle))

                # put the FPS on the picture
                # cv2.putText(visionManager.currentImage, "fps=%s" % (FPSCounter.fps()),
                #       (10, 75), cv2.FONT_HERSHEY_SIMPLEX, 1, (255,255,255))

            FPSCounter.stop()
            logger.debug("------------------")
            logger.debug("FPS: " + str(FPSCounter.fps()))
            logger.debug("------------------")

            # display:
            # cv2.imshow("Current Image", visionManager.currentImage)
            # cv2.imshow("Thresh Image", visionManager.threshImage)
            cv2.imshow("Masked Image", visionManager.maskedImage)

            #########################
            # Wait for key pressing #
            #########################

            # choose only one to be active!

            # save image:
            # k = cv2.waitKey(5) & 0xFF
            # if k == 115: # pressed s
            #     if visionManager.currentImageObject is not None:
            #         cv2.imwrite("Current Image.png",visionManager.currentImage)

            #  stop
            k = cv2.waitKey(5) & 0xFF
            if k == 27:
                break

            # sleep(0.01) # so the pi won't crush and cut the connection
    finally:
        logger.debug("----------------")
        logger.debug("Finished Running")
        logger.debug("----------------")
        cv2.destroyAllWindows()
        visionManager.cam.release()
