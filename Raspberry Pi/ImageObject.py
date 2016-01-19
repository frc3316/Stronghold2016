'''
Created by: Leon Agmon Nacht.
'''
class ImageObject(object):
    '''
    This class represents an object in the image, and the object is represented as an rectangle.
    '''
    def __init__(self,W,H,X,Y,DFC):
        '''
        This method initialising the variables for the ImageObject instance.
        :param W: The width of the object in the image.
        :param H: The height of the object in the image.
        :param X: The X coordinate  of the object in the image.
        :param Y: The Y coordinate of the object in the image.
        :param DFC: The distance of the object from the camera.
        :return: None.
        '''

        self.objectWidth = W
        self.objectHeight = H
        self.objectX = X
        self.objectY = Y
        self.distanceFromCamera = DFC
        self.XShift = None # The shift of the ImageObject instance from the center of the camera (In the X axis).
        self.YShift = None # The shift of the ImageObject instance from the center of the camera (In the X axis).
        self.didUpdateVars = False # Did VisionManager updated the var after updating the image?.
