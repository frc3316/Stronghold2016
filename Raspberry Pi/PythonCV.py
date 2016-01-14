'''
This is the main module that handles and uses all other modules for the Python CV in the First Robotics Competition 2016.
Created by: Leon Agmon Nacht.
'''
from VisionManager import *
from NetworkManager import *
import cv2
import numpy as np
UB = np.array([200,200,200])
LB = np.array([0,0,0])
MBR = 500 # Minimum bounding rect.
KH = 36 # Known height. (of U).
KW = 36 # Known width.  (of U).
FL = 1400 # Focal length.
RW = 70 # Robot width.
RH = 300 # Robot height.
RL = 70 # Robot length.
TH = 305 # The height of the tower
CUW = 100 # (Center U Width) The U width as it looks in the camera when it is in the center (in pixels).
cam = cv2.VideoCapture(0)
visionManager = VisionManager(LB,UB,MBR,cam,KH,KW,FL,[RH,RW,RL],TH, CUW)
#networkManager = NetworkManager("localhost",8080)

while True:
    visionManager.updateImage()
    visionManager.updateTowerScales()
    visionManager.updateRobotScales()
    #if visionManager.currentImageObject is not None:
    #    networkManager.sendData([visionManager.currentImageObject.distanceFromCamera,
    #                             visionManager.robotObject.angle,
    #                             visionManager.robotObject.XPosition,
    #                             visionManager.robotObject.Yposition])
    #print("D",visionManager.currentImageObject.distanceFromCamera)
    #print("A",visionManager.robotObject.angle)
    #print("X",visionManager.robotObject.XPosition)
    #print("Y",visionManager.robotObject.Yposition)
    cv2.imshow("Current Image",visionManager.maskedImage)
    k = cv2.waitKey(5) & 0xFF
    if k == 27:
        break

cv2.destroyAllWindows()
visionManager.cam.release()