'''
Created by: Leon Agmon Nacht.
'''
class RobotObject():
    '''
    This class represents a robot in the game, and is represented as:
    - 3D rectangle.
    - X,Y position.
    - Distance and angle from tower.
    '''
    def __init__(self,height,width,length):
        '''
        This method initialising the variables for the RobotObject instance.
        :param width: The width of the robot.
        :param length: The length of the robot.
        :param height: The height of the robot.
        :return: None.
        '''
        self.angle = None
        self.distanceFromTower = None
        self.XPosition = None
        self.Yposition = None
        self.width = width
        self.height = height
        self.length = length