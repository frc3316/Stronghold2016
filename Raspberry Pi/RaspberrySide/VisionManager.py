import cv2
from AngleHelper import *
from DistanceHelper import *
from ImageObject import *
from RobotObject import *
from Utils import *
class VisionManager(object):
    '''
    A class that manages all the computer vision for the FRC 2016.
    '''

    def __init__(self,minColor,maxColor,minimumBoundingRectSize,cam,knownHeight,knownWidth,focalLength,
                 robotMeasurements,TOWER_HEIGHT,centerUWidth,currentUWidthDistance,HAX,HAY):
        '''
        This method initialising the variables for the VisionManager instance.
        :param minColor: The min color to be passed in the mask. (in the format: np.array([B,G,R])).
        :param maxColor: The max color to be passed in the mask. (in the format: np.array([B,G,R])).
        :param minimumBoundingRectSize: The minimum contour area to be considered as an object.
        :param cam: A cv2.VideoCapture instance, to be used as the input camera.
        :param robotMeasurements: The measurements of the robot, in the format:
        - robotMeasurements[0] is the robot height
        - robotMeasurements[1] is the robot width
        - robotMeasurements[2] is the robot length

        MARK: AngleHelper, DistanceHelper init vars.
        :param focalLength: The focal length of the object (U).
        :param knownHeight: The real life height of the object (U).
        :param knownWidth: The real life width of the object (U).
        :param TOWER_HEIGHT: The height of the tower.
        :param centerUWidth: The U width as it looks in the camera when it is in the center (in pixels).
        :param currentUWidthDistance: The distance which the centerUWidth was calculated from.
        :param HAX: The head angle of the camera (x).
        :param HAY: The head angle of the camera (y).
        :return: None
        '''

        self.angleHelper = AngleHelper(knownWidth,knownHeight,centerUWidth,currentUWidthDistance)
        self.distanceHelper = DistanceHelper(knownHeight,focalLength)
        self.TOWER_HEIGHT = TOWER_HEIGHT
        self.KNOWN_HEIGHT = knownHeight
        self.KNOWN_WIDTH = knownWidth
        # MARK: Images.
        self.currentImage = None
        self.maskedImage = None
        self.threshImage = None
        self.isObjectDetected = False # is self detected an object (U).
        # Mark: Scales.
        self.currentImageObject = None # The current biggest Object in the masked image (probably the tower).
        self.imageHeight = None
        self.imageWidth = None

        self.minimumBoundingRectSize = minimumBoundingRectSize
        self.minColor = minColor
        self.maxColor = maxColor

        self.cam = cam
        self.robotObject = RobotObject(robotMeasurements[0],robotMeasurements[1],robotMeasurements[2])

        self.focalLength = focalLength
        self.HAX = HAX
        self.HAY = HAY

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

        if self.isObjectDetected:
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
            logger.error("Couldn't Read Image from self.cam!")

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
        :return: None if no object, else the bounding rect in the format of (x, y, w, h).
        '''

        self.updateMaskThresh()
        thresh = self.threshImage
        (cnts, _) = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        if cnts:
            c = max(cnts, key = cv2.contourArea)
            if cv2.contourArea(c) > self.minimumBoundingRectSize:
                self.isObjectDetected = True
                return cv2.boundingRect(c)
            else:
                self.isObjectDetected = False
        else:
            self.isObjectDetected = False
        return None

    def updateTowerScales(self):
        '''
        This method updates the vars holding the scales and distance from camera of the object.
        :return: None
        '''

        boundingRect = self.calculateBoundingRect()
        if boundingRect is not None:
            (x, y, w, h) = boundingRect
            if self.currentImageObject is None:
                DFC = self.distanceHelper.getDistanceFromTower(h,self.robotObject,self.TOWER_HEIGHT)
                self.currentImageObject = ImageObject(w,h,x,y,DFC)
            else:
                DFC = self.distanceHelper.getDistanceFromTower(h,self.robotObject,self.TOWER_HEIGHT)
                self.currentImageObject.objectHeight = h
                self.currentImageObject.objectWidth = w
                self.currentImageObject.objectX = x
                self.currentImageObject.objectY = y
                self.currentImageObject.distanceFromCamera = DFC

                azimuthalAngle = self.distanceHelper.getAzimuthalAngle([self.imageWidth, self.imageHeight], self.currentImageObject,self.HAX)
                polarAngle = self.distanceHelper.getPolarAngle([self.imageWidth, self.imageHeight], self.currentImageObject,self.HAY)
                self.currentImageObject.azimathalAngle = azimuthalAngle
                self.currentImageObject.polarAngle = polarAngle

            self.currentImageObject.didUpdateVar = True

    def updateRobotScales(self):
        '''
        This method updates the self.robotObject vars.
        :return: None
        '''

        if self.isObjectDetected:
            if not self.currentImageObject.didUpdateVar:
                self.updateTowerScales()
                self.currentImageObject.didUpdateVar = True
            self.robotObject.distanceFromTower = self.currentImageObject.distanceFromCamera
            self.robotObject.angle = self.angleHelper.getAngle(self.currentImageObject.distanceFromCamera,
                                                            self.currentImageObject.objectWidth,
                                                            self.focalLength, self.currentImageObject.objectHeight)
            self.robotObject.XPosition = self.distanceHelper.getXRobotPosition(self.robotObject)
            self.robotObject.Yposition = self.distanceHelper.getYRobotPosition(self.robotObject)
