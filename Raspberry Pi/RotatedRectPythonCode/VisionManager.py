import cv2
import numpy as np
from RotatedObject import *
class VisionManager(object):

    def __init__(self, cam, minColor, maxColor, MBR):
        '''
        Inisilazing the varibles of the VisionManager instance.
        :param cam: An instance of cv2.VideoCapture
        :param minColor: The min color to be passed in the mask. (in the format: np.array()).
        :param maxColor: The max color to be passed in the mask. (in the format: np.array()).
        :param MBR: The minimum contour area to be considered as an object.
        :return:
        '''
        self.imageHeight = None
        self.imageWidth = None
        self.currentImage = None

        self.maskedImage = None
        self.threshImage = None
        self.minColor = minColor
        self.maxColor = maxColor

        self.cam = cam
        self.currentImageObject = RotatedObject()
        self.minimumBoundingRectSize = MBR
    def __setImageScales(self):
        '''
        This private method sets the scales of the image(self.currentImage).
        This method is auto called, and should not be called manually.
        :return: None
        '''

        height, width = self.currentImage.shape[:2]
        self.imageHeight = height
        self.imageWidth = width

    def updateImage(self):
        '''
        This method updates the current image of the VisionManager instance.
        :return: None.
        '''

        if self.currentImageObject is not None:
            self.currentImageObject.didUpdateVar = False

        didGetImage,frame = self.cam.read()
        # frame = cv2.resize(frame, (320,240), interpolation = cv2.INTER_AREA)
        if didGetImage:
            if self.currentImage == None and self.imageHeight == None and self.imageWidth == None:
                self.currentImage = frame
                self.__setImageScales()
            else:
                self.currentImage = frame
        else:
            print("Couldn't Read Image from self.cam")

    def updateMaskThresh(self):
        '''
        This method updates self.maskedImage, self.threshImage, using self.currentImage.
        :return: None
        '''

        hsv = cv2.cvtColor(self.currentImage, cv2.COLOR_BGR2HSV)

        # Threshold the HSV image to get only blue colors.
        mask = cv2.inRange(hsv, self.minColor, self.maxColor)

        # Bitwise-AND mask and original image.
        res = cv2.bitwise_and(self.currentImage, self.currentImage, mask=mask)
        self.maskedImage = res

        thresh = cv2.threshold(mask, 25, 255, cv2.THRESH_BINARY)[1]
        # dilate the threshed image to fill in holes, then find contours on thresholded image.
        thresh = cv2.dilate(thresh, None, iterations=2)
        self.threshImage = thresh

    def calculateBoundingRect(self):
        '''
        This method calculates the bounding rect of the biggest object that matches the min-max colors in
        self.current_image.
        :return: None if no object, else the bounding rect in the format of (upperRight,
                                                                             upperLeft,
                                                                             lowerRight
                                                                             lowerLeft).
                where each of the above is a tuple (x,y).
        '''
        self.updateMaskThresh()
        thresh = self.threshImage
        (cnts, _) = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        if cnts != []:
            c = max(cnts, key = cv2.contourArea)
            if cv2.contourArea(c) > self.minimumBoundingRectSize:
                rect = cv2.minAreaRect(c)
                box = np.int0(cv2.cv.BoxPoints(rect))
                lowerRight = (box[0][0],box[0][1])
                lowerLeft = (box[1][0],box[1][1])
                upperLeft = (box[2][0],box[2][1])
                upperRight = (box[3][0],box[3][1])
                return (upperRight,upperLeft,lowerRight,lowerLeft)

        return None

    def updateObjectScales(self, rotatedObject):
        '''
        This method updates the vars holding the angle and distance from camera of the object.
        :var rotatedObject: An instance of RotatedObject to use as the data to be set.
        :return: None
        '''
        if object is None:
            print("rotatedObject is None, couldn't update object scales.")

        (upperRight,upperLeft,lowerRight,lowerLeft) = rotatedBox
        VM.currentImageObject.upperRight = upperRight
        VM.currentImageObject.upperLeft = upperLeft
        VM.currentImageObject.lowerRight = lowerRight
        VM.currentImageObject.lowerLeft = lowerLeft
        UWidth1 = getDistanceFromPoints(upperRight,lowerRight)
        UWidth2 = getDistanceFromPoints(upperLeft,lowerLeft)
        UWIdth = (UWidth1 + UWidth2)/2

LB = np.array([37,0,231]) # Lower bond
UB = np.array([108,40,255]) # Upper bond
MBR = 500
VM = VisionManager(cv2.VideoCapture(0),LB,UB, 500)

while True:
    VM.updateImage()
    rotatedBox = VM.calculateBoundingRect()
    if rotatedBox != None:
        VM.updateObjectScales(rotatedBox)
