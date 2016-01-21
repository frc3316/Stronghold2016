'''
Created by: Leon Agmon Nacht.
'''
from math import asin, degrees, radians, acos, sqrt


class AngleHelper(object):
    '''
    A class that handles the angle to U calculating.
    '''

    def __init__(self, UWidth, UHeight, centerUWidth, knownDistance):
        '''
        This method initialising the variables for the AngleHelper instance.
        :param UWidth: The real life width of the U.
        :param centerUWidth: The U width as it looks in the camera when it is in the center (in pixels).
        :param knownDistance: The distance which the centerUWidth wast taken from.
        :return: None
        '''
        self.UWIDTH = UWidth
        self.UHEIGHT = UHeight
        self.cameraUWidth = centerUWidth
        self.CUWKnownDistance = knownDistance

    def getAngle(self, DFC, CUW, FL, CUH):
        '''
        This method returns the angle of the object from the tower.
        The angle that is calculated in this method is described in AngleDraw.
        :param DFC: The distance of the robot from the camera in real life.
        :param CUW: The current U width as it looks in the camera (in pixels).
        :param FL: The Focal length.
        :param CUH: The current U height as it looks in the camera (in pixels).
        :return: The angle of the object from the tower, in degrees.
        '''

        rectangleWidth = CUW*(self.UHEIGHT/CUH)

        adjustment = (self.UWIDTH)*(rectangleWidth) + \
                     self.UHEIGHT * sqrt(self.UWIDTH ** 2 + self.UHEIGHT ** 2 - rectangleWidth ** 2)/(self.UHEIGHT ** 2 + self.UWIDTH ** 2)



# alpha = (1/adjustment)*CUW

        return adjustment  # degrees(asin(radians((DFC/self.UWIDTH)*sin(alpha)-alpha)))


