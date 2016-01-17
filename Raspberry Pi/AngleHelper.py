'''
Created by: Leon Agmon Nacht.
'''
from math import acos,degrees,radians

class AngleHelper(object):
    '''
    A class that handles the angle to U calculating.
    '''
    def __init__(self,UWidth,centerUWidth,knownDistance):
        '''
        This method initialising the variables for the AngleHelper instance.
        :param UWidth: The real life width of the U.
        :param centerUWidth: The U width as it looks in the camera when it is in the center (in pixels).
        :param knownDistance: The distance which the centerUWidth wast taken from.
        :return: None
        '''
        self.UWIDTH = UWidth
        self.cameraUWidth = centerUWidth
        self.CUWKnownDistance = knownDistance

    def getAngle(self,DFC,CUW):
        '''
        This method returns the angle of the object from the tower.
        The angle that is calculated in this method is described in AngleDraw.
        :return: The angle of the object from the tower, in degrees.
        :param DFC: The distance of the robot from the camera.
        :param CUW: The current U width as it looks in the camera (in pixels).
        '''
        side3 = self.UWIDTH
        side1 = DFC
        currentDistanceCenterUWidth = (self.CUWKnownDistance/DFC)*self.cameraUWidth # The U width as it looks in
        # the camera (in pixels) from the current distance.
        side2 = DFC + (1-(float(CUW)/float(currentDistanceCenterUWidth)))*self.UWIDTH
        return 180 - degrees(acos(radians((side1**2 + side3**2 - side2**2)/(2*side1*side3))))
        # This method is not 100% accurate, but it is close to that.