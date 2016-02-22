import cv2
cam = cv2.VideoCapture(0)
cam.set(cv2.cv.CV_CAP_PROP_BRIGHTNESS, -1)
cam.set(cv2.cv.CV_CAP_PROP_SATURATION, 1)
cam.set(cv2.cv.CV_CAP_PROP_EXPOSURE, -1) # not working on the old camera
f = cam.read()[1]
print cam.isOpened()
counter = 0
while counter < 20:
    counter +=1
    cv2.imwrite("output" + str(counter) + ".jpg", f)
cam.release()
