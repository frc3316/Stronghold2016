from Tkinter import *
import numpy as np
import cv2

master = Tk()
cam = cv2.VideoCapture(0)
cam.set(3,480) # Width of frame.
cam.set(4,320) # Height of frame.
sliders = []
labels = ["Upper H", "Upper S", "Upper V", "Lower H", "Lower S", "Lower V"]

def updateImage(_):
    upperH = sliders[0].get()
    upperS = sliders[1].get()
    upperV = sliders[2].get()
    lowerH = sliders[3].get()
    lowerS = sliders[4].get()
    lowerV = sliders[5].get()
    didGet, frame = cam.read()
    if didGet:
        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

        # Threshold the HSV image to get only blue colors.
        mask = cv2.inRange(hsv, np.array([lowerH,lowerS,lowerV]), np.array([upperH,upperS,upperV]))

        # Bitwise-AND mask and original image.
        res = cv2.bitwise_and(frame, frame, mask=mask)
        maskedImage = res

        thresh = cv2.threshold(mask, 25, 255, cv2.THRESH_BINARY)[1]
        # dilate the threshed image to fill in holes, then find contours on thresholded image.
        thresh = cv2.dilate(thresh, None, iterations=1)
        threshImage = thresh

        (cnts, _) = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        if cnts:
            c = max(cnts, key = cv2.contourArea)

        cv2.imshow("Thresh Image", threshImage)
        cv2.imshow("Original Image", frame)
        cv2.imshow("Masked Image", maskedImage)

for i in range(6):
    labels.append(Label(master, text = labels[i]))
    labels[-1].pack()
    sliders.append(Scale(master, from_=0, to=255, orient=HORIZONTAL, command = updateImage))
    sliders[-1].pack()

mainloop()
cam.release()
cv2.destroyAllWindows()