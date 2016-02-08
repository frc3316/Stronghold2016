import cv2
import time
import thread
from Tkinter import *

def saveImage():
    frame = cam.read()[1]
    cv2.imshow("Saved Image", frame)
    cv2.imwrite(str(time.time()) + ".png", frame)
    with open(str(time.time()) + ".txt",  "w") as f:
        f.write(str(DFC.get()))
        f.close()

def showImage():
    while True:
        cv2.imshow("Current Image", cam.read()[1])

cam = cv2.VideoCapture(0)
cam.set(3,620) # Width of frame.
cam.set(4,480) # Height of frame.

master = Tk()
saveImage = Button(master,text = "Save Image", command = saveImage)
DFC = StringVar() # A variable holding the distance from camera as given from the user in the text box.
distanceTF = Entry(master, textvariable = DFC)
saveImage.pack(); distanceTF.pack()

thread.start_new_thread(showImage, ())
mainloop()