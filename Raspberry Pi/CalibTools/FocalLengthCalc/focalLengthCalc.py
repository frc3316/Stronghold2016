from Tkinter import *
from math import sqrt
import numpy as np
import tkFileDialog as tkfd
import cv2

KNOWN_HEIGHT = 36 # the height of the u in real life.

# def calcDistance(focalLength, objectHeight):
#     remainder = float(KNOWN_HEIGHT * focalLength) / float(objectHeight)
#     try:
#         return sqrt(remainder**2 - (KNOWN_HEIGHT/2)**2)
#     except:
#         return None

# find color bounds:
searchPathWindow = tkfd.askopenfile()
im = cv2.imread(searchPathWindow.name)
with open(searchPathWindow.name[:-4] + "data.txt", 'r') as f:
    lines = f.read().splitlines()
d = {}
keys = ["Upper H", "Upper S", "Upper V", "Lower H", "Lower S", "Lower V", "Brightness", "Saturation"]
for i in range(len(lines)):
    d[keys[i]] = float(lines[i])

# get distance:
with open(searchPathWindow.name[:-4] + ".txt", "r") as f:
    distance = float(f.readline().rstrip())

hsv = cv2.cvtColor(im, cv2.COLOR_BGR2HSV)

# Threshold the HSV image to get only blue colors.
lower = np.array([d[keys[3]],d[keys[4]],d[keys[5]]])
upper = np.array([d[keys[0]],d[keys[1]],d[keys[2]]])
mask = cv2.inRange(hsv, lower, upper)

# Bitwise-AND mask and original image.
maskedImage = cv2.bitwise_and(im, im, mask=mask)

thresh = cv2.threshold(mask, 25, 255, cv2.THRESH_BINARY)[1]
# dilate the threshed image to fill in holes, then find contours on thresholded image.
thresh = cv2.dilate(thresh, None, iterations=1)
(cnts, _) = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
if cnts:
    c = max(cnts, key = cv2.contourArea)
    (x, y, w, h) = cv2.boundingRect(c)
    focalLength = (h * distance) / KNOWN_HEIGHT
    print "Focal Length " + str(focalLength)
    print("MARK: This is a physical solution, need to verify by running the vision code and checking distance")
else:
    print "No contour found"
mainloop()