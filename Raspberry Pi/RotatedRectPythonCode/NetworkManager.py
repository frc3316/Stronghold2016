import socket
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

    def sendData(self,lst):
        '''
        This method is responsible on sending data as a string to the Host self.HOST on port self.PORT.
        :param lst: The list to be sent.
        :return: None
        '''

        try:
            for i in range(len(lst)):
                lst[i] = str(int(lst[i]))
        except ValueError:
            print ('Input for sendData invalid, need to be a list of floats/ints/strings')

        self.sock.sendall(','.join(lst) + "\n")
        data = self.sock.recv(1024)

        if data[:2] != "Ok":
            print ("Problem Sending Results")
