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
from Utils import *
from Constants import *
# Lock File:
lockFile = LockFile("LockFile.loc")

# Networking:

if len(sys.argv) > 1:
    JAVA_HOST = sys.argv[1]
else:
    JAVA_HOST = "roborio-3316-frc.local"

if len(sys.argv) > 2:
    isShowingImage = int(sys.argv[2])
else:
    isShowingImage = 1

# are we going to send data to the java process:
shouldNetwork = True

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

        cam = cv2.VideoCapture(getCameraNumber())
        if not cam.isOpened():
            logger.error("No Camera Found!!!")

        cam.set(cv2.cv.CV_CAP_PROP_BRIGHTNESS, brightness)
        cam.set(cv2.cv.CV_CAP_PROP_SATURATION, saturation)
        cam.set(cv2.cv.CV_CAP_PROP_EXPOSURE, exposure) # not working on the old camera
        # cam.set(cv2.cv.CV_CAP_PROP_BUFFERSIZE, 1)  # Eliminates cv buffer, this way we always recv the latest image
		
        visionManager = VisionManager(LB, UB, MBR, cam, KH, KW, FL, [RH,RW,RL], TH, CUW, CUWD, HAX, HAY)
        networkManager = NetworkManager(JAVA_HOST,8080)
        ###################
        # The code itself #
        ###################
        while True:
            visionManager.updateImage()
            logger.debug("Updated Image")
            visionManager.updateTowerScales()
            logger.debug("Updated Tower Scales")
            visionManager.updateRobotScales()
            logger.debug("Updated Robot Scaled")
            FPSCounter.update()
            logger.debug("Updated FPS Counter")

            if visionManager.isObjectDetected: # if an object was detected
                #######################
                #   Magic Constants   #
                #######################
                DFC = goMagic(visionManager.currentImageObject.distanceFromCamera)
                visionManager.currentImageObject.distanceFromCamera = DFC
                visionManager.robotObject.distanceFromTower = DFC

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
                cv2.rectangle(visionManager.maskedImage, (x, y), (x + w, y + h), (0, 0, 255), 2)

            #############################
            # Send data to java process #
            #############################

            if shouldNetwork:
                if visionManager.isObjectDetected:
                    values = [visionManager.currentImageObject.distanceFromCamera,
                              visionManager.currentImageObject.azimuthalAngle,
                              visionManager.currentImageObject.polarAngle,
                              visionManager.isObjectDetected]
                    names = ['DFC', 'AA', 'PA', 'IOD']
                    networkManager.sendData(values, names)
                else:
                    values = ['3316.00','3316.00','3316.00','0.00']
                    names = ['DFC', 'AA', 'PA', 'IOD']
                    networkManager.sendData(values, names)

            ###################
            # Results logger  #
            ###################

            if visionManager.isObjectDetected:
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
            else:
                logger.debug("Object not detected")

            FPSCounter.stop()
            logger.debug("------------------")
            logger.debug("FPS: " + str(FPSCounter.fps()))
            logger.debug("------------------")
            #if visionManager.isObjectDetected:
               #print visionManager.currentImageObject.distanceFromCamera
               #cv2.imwrite("masked.jpg", visionManager.maskedImage)
            # display:
            if isShowingImage:
                pass
                #cv2.imshow("Current Image", visionManager.currentImage)
                # cv2.imshow("Thresh Image", visionManager.threshImage)
                #cv2.imshow("Masked Image", visionManager.maskedImage)
            
            #########################
            # Wait for key pressing #
            #########################

            # choose only one to be active!

            # save image:
            # k = cv2.waitKey(5) & 0xFF
            # if k == 115: # pressed s
            #     if visionManager.isObjectDetected:
            #         cv2.imwrite("Current Image.png",visionManager.currentImage)

            #  stop
            # k = cv2.waitKey(5) & 0xFF
            # if k == 27:
            #    break

            # sleep(0.01) # so the pi won't crush
    except ValueError:
	logger.error("Error!" + str(ValueError))
    finally:
        logger.debug("----------------")
        logger.debug("Finished Running")
        logger.debug("----------------")
        cv2.destroyAllWindows()
        visionManager.cam.release()
        networkManager.sock.close()
