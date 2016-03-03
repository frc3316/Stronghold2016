import socket

from Utils import *
from VisionManager import ImageObject


class NetworkManager(object):
    """
    A class that handles the networking to the Java process.
    """

    ERROR_VALUE = ImageObject(tower_distance=3316,
                              azimuth_angle=3316,
                              polar_angle=3316)

    def __init__(self, host, port):
        """
        :param host: The host name to be connected to.
        :param port: The port for the communication.
        :return: None.
        """
        self._address = (socket.gethostbyname(host), port)
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    @staticmethod
    def format_parameter(param):
        return '{0:.2f}'.format(float(param))

    def format_data(self, goal_image=None):
        if goal_image is not None:
            identified = 1
        else:
            identified = 0
            goal_image = self.ERROR_VALUE

        return dict(DFC=NetworkManager.format_parameter(goal_image.tower_distance),
                    AA=NetworkManager.format_parameter(goal_image.azimuth_angle),
                    PA=NetworkManager.format_parameter(goal_image.polar_angle),
                    IOD=NetworkManager.format_parameter(identified))

    def send_data(self, goal_image=None):
        """
        This method is responsible on sending data as a string to the Host self.HOST on port self.PORT.
        The data is represented as a dictionary { "name1": val1, "name2": val2,...}
        :param goal_image: goal image object to send
        :return: None
        """
        formatted_data = str(self.format_data(goal_image))
        logger.debug(str(formatted_data))
        logger.debug("Address: " + str(self._address))
        self.sock.sendto(formatted_data.replace(' ', '') + '\n', self._address)

    def send_no_data(self):
        self.send_data()
