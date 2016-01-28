from math import sqrt

class RotatedObject(object):
    '''
    A class represents a rotated object in the camera using upperRight, upperLeft, lowerRight, lowerLeft.
    '''
    def __init__(self):
        '''
        Initializing the vars of a RotatedObject instance.
        :return: None.
        '''
        self.upperRight = None
        self.upperLeft = None
        self.lowerRight = None
        self.lowerLeft = None

    def __repr__(self):
        return str((self.upperRight,self.upperLeft,self.lowerRight,self.lowerLeft))

def getDistanceFromPoints(p1,p2):
        '''
        Calculate the distance(in pixels) from p1 to p2, where p1 and p2 are tuples like (x,y).
        :param p1: The first point to calculate the distance from.
        :param p2: The second point to calculate the distance from.
        :return: The distance in pixels from p1 to p2.
        '''
        deltaX = abs(p1[0]-p2[0])
        deltaY = abs(p1[1]-p2[1])
        return sqrt(deltaX**2 + deltaY**2)