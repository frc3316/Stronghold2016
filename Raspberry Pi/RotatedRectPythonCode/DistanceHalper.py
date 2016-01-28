from math import sqrt,sin,cos,radians

class DistanceHelper(object):
    '''
    A class that handles the distance calculating.
    '''

    def __init__(self,knownHeight,focalLength):
        '''
        A class that handles the calculations of the distance from the U to an object(Robot).
        :param knownHeight: The known height of the U.
        :param focalLength: The focal length of the U.
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