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

        # adjustment = (as + b * sqrt(a^2 + b^2 - s^2))/(a^2 + b^2)
        # a = UHeight
        # b = UWidth
        # s = rectangle width

# alpha = (1/adjustment)*CUW

        return adjustment  # degrees(asin(radians((DFC/self.UWIDTH)*sin(alpha)-alpha)))

        projectedUWidth = (float(CUH)/float(CUW))*self.UWIDTH
        if projectedUWidth/DFC > 1:
            return 999
        alpha1 = degrees(acos(projectedUWidth/DFC))
        if self.UHEIGHT/projectedUWidth > 1:
            return 444
        alpha2 = degrees(acos(self.UHEIGHT/projectedUWidth))
        print(alpha1,alpha2,projectedUWidth/self.UWIDTH, projectedUWidth, self.UWIDTH)
        return 180 - alpha1 - alpha2

    #print(DFC,CUW,FL)
#print((float(CUW)/float(CUH))*self.UWIDTH)
#remainder = sqrt(self.UHEIGHT**2 + DFC**2)
# currentDistanceCenterUWidth = (float(DFC)/float(self.CUWKnownDistance)) * self.cameraUWidth  # The U width as it looks in
# the camera (in pixels) from the current distance when the camera is in center.

# print(self.UWIDTH,projectedUWidth)
# '''finding the sin of the tilt angle, which equals to
# the size we see divided by the real size (if we face it)'''


# side3 = self.UWIDTH
# side1 = DFC
# currentDistanceCenterUWidth = (self.CUWKnownDistance/DFC)*self.cameraUWidth # The U width as it looks in
# the camera (in pixels) from the current dista nce.
# side2 = DFC + (1-(float(CUW)/float(currentDistanceCenterUWidth)))*self.UWIDTH
# result = (side1**2 + side3**2 - side2**2)/(2*side1*side3)
# if result <= 1:
#     return 180 - degrees(acos(result))
# else: return 999
# This method is not 100% accurate, but it is close to that.