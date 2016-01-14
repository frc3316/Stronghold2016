'''
Created by: Leon Agmon Nacht.
'''

'''
Testing Comments with Rafael ;)
'''
from math import acos,degrees,radians

class AngleHelper(object):
    '''
    A class that handles the angle to U calculating.
    '''
    def __init__(self,UWidth,centerUWidth):
        '''
        This method initialising the variables for the AngleHelper instance.
        :param UWidth: The real life width of the U.
        :param centerUWidth: The U width as it looks in the camera when it is in the center (in pixels).
        :return: None
        '''
        self.UWIDTH = UWidth
        self.CameraUWidth = centerUWidth

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
        side2 = DFC + (1-(CUW/self.CameraUWidth))*self.UWIDTH
        return 180 - degrees(acos(radians((side1**2 + side3**2 - side2**2)/(2*side1*side3))))
        # This method is not 100% accurate, but it is close to that.