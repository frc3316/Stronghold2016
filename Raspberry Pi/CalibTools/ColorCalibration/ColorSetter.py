from Tkinter import *
import tkFileDialog as tkfd
import numpy as np
import cv2

def setAndShowImage(sender):
    brightness = sliders[6].get()/10.0
    saturation = sliders[7].get()/10.0 # so the range will be between -30 to 30 in jumps of 0.1
    cam.set(cv2.cv.CV_CAP_PROP_BRIGHTNESS, brightness)
    cam.set(cv2.cv.CV_CAP_PROP_SATURATION, saturation)

    if not isImageChoosed:
        didGet, frame = cam.read()
    else:
        frame = userImage

    upperH = sliders[0].get()
    upperS = sliders[1].get()
    upperV = sliders[2].get()
    lowerH = sliders[3].get()
    lowerS = sliders[4].get()
    lowerV = sliders[5].get()
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

    cv2.imshow("Thresh Image", cv2.resize(threshImage,(480, 320), interpolation = cv2.INTER_CUBIC))
    cv2.imshow("Original Image", cv2.resize(frame,(480, 320), interpolation = cv2.INTER_CUBIC))
    cv2.imshow("Masked Image", cv2.resize(maskedImage,(480, 320), interpolation = cv2.INTER_CUBIC))

def doneButtonClicked():
    with open(imagePath[:-4] + str("data.txt"), "w") as f:
        values = [sliders[0].get(),
                  sliders[1].get(),
                  sliders[2].get(),
                  sliders[3].get(),
                  sliders[4].get(),
                  sliders[5].get(),
                  sliders[6].get()/10.0,
                  sliders[7].get()/10.0] # so the range will be between -30 to 30 in jumps of 0.1
        full_string = "\n".join([str(values[i]) for i in range(len(values))])
        print(full_string)
        f.write(full_string)
        f.close()


def loadImage():
    global imagePath
    global userImage
    global isImageChoosed
    searchPathWindow = tkfd.askopenfile()
    print("Image Choosed " + searchPathWindow.name)
    userImage = cv2.imread(searchPathWindow.name)
    userImage = cv2.resize(userImage,(480, 320), interpolation = cv2.INTER_CUBIC)
    isImageChoosed = True
    imagePath = searchPathWindow.name

master = Tk()
imagePath = None
cam = cv2.VideoCapture(0)
sliders = []
labels = ["Upper H", "Upper S", "Upper V", "Lower H", "Lower S", "Lower V", "Brightness", "Saturation"]
isImageChoosed = False
userImage = None
thresh = None
masked = None
original = None
for i in range(len(labels)):
    labels.append(Label(master, text = labels[i]))
    labels[-1].pack()
    if i < len(labels)-2:
        sliders.append(Scale(master, from_=0, to=255, orient=HORIZONTAL, command = setAndShowImage))
        sliders[-1].pack()
    else:
        sliders.append(Scale(master, from_=-300, to=300, orient=HORIZONTAL, command = setAndShowImage))
        sliders[-1].pack()

chooseImage = Button(master, text = "Load", command = loadImage)
doneButton = Button(master, text = "Done", command = doneButtonClicked)
chooseImage.pack()
doneButton.pack()
mainloop()
cam.release()
cv2.destroyAllWindows()