import logging
import time
import os.path
from os import getpid
from glob import glob

##################
#     Logger     #
##################
loggerFileName = (str(time.ctime()) + ".log").replace(" ", "_")
if not os.path.exists('/var/log/roboticsVision'):
    os.mkdir('/var/log/roboticsVision')
logging.basicConfig(filename='/var/log/roboticsVision/' + loggerFileName, level=logging.DEBUG)
logger = logging.getLogger()
#################
# Sections Keys #
#################

# Image Object (U):
# Robot Information:

##################
#    Lock File   #
##################

class LockFile():
    '''
    A class helps handling a lock file.
    1 is locked, 0 is unlocked.
    '''

    def __init__(self, pathToFile):
        '''
        Init the variables of the class.
        :param pathToFile: The path to the lock file
        :return: None
        '''
        self.pathToFile = pathToFile

    def lock(self):
        with open(self.pathToFile, "w") as f:
            f.write(str(os.getpid()))

    def is_locked(self):
        '''
        A method returns if the file is locked.
        :return: A bool value True is locked, False is unlocked.
        '''
        if not os.path.exists(self.pathToFile):
            return False
        with open(self.pathToFile, 'r') as f:
            firstLine = f.readline()
            if firstLine == "":
                return False
            return check_pid(firstLine)


def check_pid(pid):
    """ Check For the existence of a unix pid. """
    try:
        os.kill(int(pid), 0)
    except OSError:
        return False
    else:
        return True


def getCameraNumber():
    '''
    finds the camera number from /dev
    return: Int, the camera number
    '''
    allCamerasNames = glob("/dev/video*")
    if allCamerasNames:
        fullName = allCamerasNames[0]
        startIndex = fullName.find("video")
        return int(fullName[startIndex + len("video"):])
    else:
        return 0  # Default camera value
