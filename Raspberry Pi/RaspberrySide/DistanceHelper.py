from math import sqrt,sin,cos,radians, tan

class DistanceHelper(object):
    '''
    A class that handles the distance calculating.
    '''

    def __init__(self,knownHeight,enlargementFactor):
        '''
        A class that handles the calculations of the distance from the U to an object(Robot).
        :param knownHeight: The known height of the U.
        :param enlargementFactor: The enlargement factor of the U.
        :return: None.
        '''

        self.knownHeight = knownHeight
        self.enlargementFactor = enlargementFactor

    def normalize_rectangle_height(self, bounding_rectangle, min_rectangle_angle):
        """
        Finds the normalized height of the rectangle by calculating the ratio between the base and the side of the
        minimal area rectangle.

        actual_height = rectangle_height - tan(angle of minimal area rectangle) * rectangle_width
        :param bounding_rectangle: whole bounding rectangle
        :param min_rectangle_angle: angle of a minimal area rectangle
        :return: actual height for distance messurements
        """
        normalize_height = bounding_rectangle.height - abs(tan(radians(min_rectangle_angle))) * bounding_rectangle.width
        if normalize_height < 10000: # a very big number, meaning impossible
            return normalize_height
        else:
            return 3316

    def getDistanceFromTower(self,objectHeight,robotObject,towerHeight = 305):
        '''
        This method calculates the distance of the object from the camera using the following physics:
        - enlargementFactor is described as P*D/W, where P is the height of the object in pixels in the distance D,
        and W is the real height of the object.
        - so the following calculations is actually P*D/OH, where OH is the current object height in pixels, and P,D
        are the same P,D as above.
        :param objectHeight: The height of the object (in pixels) as viewed in the camera.
        :param robotObject: An instance of RobotObject.
        :param towerHeight: The height of the tower in cm.
        :return: The distance of the object from the camera.
        '''

        remainder = float(self.knownHeight * self.enlargementFactor) / float(objectHeight)
        return remainder

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
        :type robotObject: object
        :param robotObject: An instance of RobotObject.
        :return: The Y position of the robotObject.
        '''

        return sin(radians(robotObject.angle))*robotObject.distanceFromTower
    # TODO: move those to angle helper:
    def getAzimuthalAngle(self,cameraSize,imageObject,HAX):
        '''
        This method calculates the azimuthal angle of the imageObject from the center of the frame.
        :param cameraSize: A list that represents the size of the frame:
         - cameraSize[0] = frame width.
         - cameraSize[1] = frame height.
        :param imageObject: The object the method should calculate the azimuthal angle for.
        :param HAX: The head angle of the camera (x).
        :return: The azimuthal angle of the imageObject from the center of the frame
        '''
        (xC, yC) = (cameraSize[0]/2, cameraSize[1]/2) # Center of the frame
        # Center of the object:
        (xO, yO) = (imageObject.objectX + imageObject.objectWidth/2.0, imageObject.objectY + imageObject.objectHeight/2.0)
        return float((xO-xC)*HAX)/float(cameraSize[0]) # x,y plane.
    
    def getPolarAngle(self,cameraSize,imageObject,HAY):
        '''
        This method calculates the polar angle of the imageObject from the center of the frame.
        :param cameraSize: A list that represents the size of the frame:
        - cameraSize[0] = frame width.
        - cameraSize[1] = frame height.
        :param imageObject: The object the method should calculate the polar angle for.
        :param HAY: The head angle of the camera (y).
        :return: The polar angle of the imageObject from the center of the frame
        '''

        (xC, yC) = (cameraSize[0]/2, cameraSize[1]/2) # Center of the frame
        # Center of the object:
        (xO, yO) = (imageObject.objectX + imageObject.objectWidth/2.0, imageObject.objectY + imageObject.objectHeight/2.0)
        return float((yO-yC)*HAY)/float(cameraSize[1]) # Azimuthal, z plane.

   