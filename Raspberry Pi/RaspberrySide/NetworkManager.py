import socket
from Utils import *
class NetworkManager(object):
    '''
    A class that handles the networking to the Java process.
    '''

    def __init__(self, HOST,PORT):
        '''
        :param HOST: The host name to be connected to.
        :param PORT: The port for the communication.
        :return: None.
        '''

        self.HOST = HOST
        self.PORT = PORT

        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect((HOST, PORT))
        self.sock = sock

    def sendData(self,values,names):
        '''
        This method is responsible on sending data as a string to the Host self.HOST on port self.PORT.
        The data is represented as a dictionary { "name": val, "name": val,...}
        :param values: a list of values to send.
        :param names: a list of the names of the values.
        :return: None
        '''
        try:
            resultDic = {}
            for i in range(len(names)):
                if values[i] is None:
                    strValue = '331600000.00'
                else:
                    strValue = '{0:.2f}'.format(float(values[i]))
                resultDic[names[i]] = strValue
            stringToSend = str(resultDic).replace(" ","")
            print(stringToSend)
            self.sock.sendall(stringToSend + "\n")

        except ValueError:
            logger.warning('Input for sendData invalid, or error in sending data')
