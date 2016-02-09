import cv2
import time
import thread
from Tkinter import *

# TODO: add exposure and saturation default values.
def saveImage():
    timeStamp = time.time()
    frame = cam.read()[1]
    cv2.imshow("Saved Image", cv2.resize(cam.read()[1],(480, 320), interpolation = cv2.INTER_CUBIC))
    cv2.imwrite(str(timeStamp) + ".png", frame)
    with open(str(timeStamp) + ".txt",  "w") as f:
        f.write(str(DFC.get()))
        f.close()

def showImage():
    while True:
        cv2.imshow("Current Image", cv2.resize(cam.read()[1],(480, 320), interpolation = cv2.INTER_CUBIC))

cam = cv2.VideoCapture(0)
# cam.set(3,620) # Width of frame.
# cam.set(4,480) # Height of frame.

master = Tk()
saveImage = Button(master,text = "Save Image", command = saveImage)
DFC = StringVar() # A variable holding the distance from camera as given from the user in the text box.
distanceTF = Entry(master, textvariable = DFC)
saveImage.pack(); distanceTF.pack()

thread.start_new_thread(showImage, ())
mainloop()