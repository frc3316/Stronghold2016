import numpy as np
# Colors:
LB = np.array([80,210,180]) # Lower bond
UB = np.array([95,255,255]) # Upper bond

# Bounding rectangle
MBR = 200 # Minimum bounding rectangle
maximumBoundingRectangle = 10000
HWR = 1 # The maximum ratio of height/width of the bounding rectangle.

KH = 36 # Known height (of U).
KW = 50.8 # Known width  (of U).
FL = 972 # Focal length.

# Robot:
RW = 100 # Robot width.
RH = 0 # Robot height.
RL = 100 # Robot length.

# Field:
TH = 130 + 36//2 # The height of the tower
CUW = 260 # (Center U Width) The U width as it looks in the camera when it is in the center (in pixels).
CUWD = 220 # The distance which the CUW was calculated from.

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
resizedImageWidth = 320
resizedImageHeight = 240
