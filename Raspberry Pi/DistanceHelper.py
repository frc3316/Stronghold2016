from math import sqrt,sin,cos,radians

class DistanceHelper(object):
    '''
    A class that handles the distance calculating.
    '''

    def __init__(self,knownHeight,focalLength):
        '''
        A class that handles the calculations of the distance from the U to an object(Robot).
        :param knownHeight: The known height of the U.
        :param focalLength: The focal lenght of the U.
        :return: None.
        '''

        self.knownHeight = knownHeight
        self.focalLength = focalLength

    def getDistanceFromTower(self,objectHeight,robotObject,towerHeight = 305):
        '''
        This method calculates the distance of the object from the camera.
        :param objectHeight: The height of the object (in pixels) as viewed in the camera.
        :param robotObject: An instance of RobotObject.
        :param towerHeight: The height of the tower in cm.
        :return: The distance of the object from the camera.
        '''

        remainder = float(self.knownHeight * self.focalLength) / float(objectHeight)
        return sqrt(remainder**2 - (towerHeight-(self.knownHeight/2)-robotObject.height)**2)

    def getXRobotPosition(self,robotObject):
        '''
        Ths method calculates the X position of the robotObject, ((0,0) is the center of the far edge of the field).
        :param robotObject: An instance of RobotObject.
        :return: The X position of the robotObject.
        '''

        return cos(radians(robotObject.angle))*robotObject.distanceFromTower

    def getYRobotPosition(self,robotObject):
        '''
        Ths method calculates the Y position of the robotObject, ((0,0) is the center of the far edge of the field).
        :param robotObject: An instance of RobotObject.
        :return: The Y position of the robotObject.
        '''

        return sin(radians(robotObject.angle))*robotObject.distanceFromTower

    def getXShiftOfTower(self,cameraSize,imageObject):
        '''
        This method calculates the X shift of the imageObject from the center of the frame.
        :param cameraSize: A list that represents the size of the frame:
        - cameraSize[0] = frame width.
        - cameraSize[1] = frame height.
        :param imageObject: The object the method should calculate the shift for.
        :return: The X shift of the imageObject from the center of the frame
        '''

        frameCenter = cameraSize[0]/2
        return frameCenter - (imageObject.objectX + imageObject.objectWidth/2)

        # If the object is left from the center of the frame, the result will be positive.

    def getYShiftOfTower(self,cameraSize,imageObject):
        '''
        This method calculates the Y shift of the imageObject from the center of the frame.
        :param cameraSize: A list that represents the size of the frame:
        - cameraSize[0] = frame width.
        - cameraSize[1] = frame height.
        :param imageObject: The object the method should calculate the shift for.
        :return: The Y shift of the imageObject from the center of the frame
        '''

        frameCenter = cameraSize[1]/2
        return frameCenter - (imageObject.objectY + imageObject.objectHeight/2)
