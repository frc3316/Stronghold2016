import numpy as np
# Colors:
LB = np.array([85,220,230]) # Lower bond
UB = np.array([95,255,255]) # Upper bond

# Bounding rectangle
MBR = 200 # The minimum area of a contour to be considered as a possible object (U). the area is calculated using
# cv2.contourArea
maximumBoundingRectangle = 10000 # The maximum area of a contour to be considered as a possible object (U). the area is calculated using
# cv2.contourArea
HWR = 1 # The maximum ratio of height/width of the bounding rectangle of a contour to be considered as a potential contour.

KH = 36 # Known height (of U). The real height of the U in cm.
KW = 50.8 # Known width  (of U). The real width of the U in cm.
enlargementFactor = 55.0/20 # Focal length. NOT actually focal length, focal length is described as P*D/W, where P is the height of the
# object in pixels in the distance D, and W is the real height of the object.

# Robot:
RW = 100 # Robot width in cm, unused right now.
RH = 0 # Robot height in cm.
RL = 100 # Robot length in cm, unused right now.

# Field:
TH = 130 + 36//2 # The height of the tower
CUW = 260 # (Center U Width) The U width as it looks in the camera when it is in the center (in pixels). unused right now.
CUWD = 220 # The distance which the CUW was calculated from. unused right now.

# Camera:
CAMERA_ANGLE = 0 # in degrees
HAX = 53 # The head angle of the camera (x)
HAY = 39.75 # The head angle of the camera (y)

# Camera Settings:
brightness = 0.01
saturation = 1
exposure = 0.9
contrast = 0.01
rotateClockwise = False # Is the image rotated by 90 degrees clockwise or counter clockwise. True is clockwise.
resizedImageWidth = 320 # the width of the resized image.
resizedImageHeight = 240
