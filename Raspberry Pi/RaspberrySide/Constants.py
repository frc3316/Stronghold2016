import numpy as np

# Colors:
LB = np.array([50,250,160]) # Lower bond
UB = np.array([70,255,255]) # Upper bond

# Bounding rectangle
MBR = 200 # Minimum bounding rectangle
KH = 36 # Known height (of U).
KW = 50.8 # Known width  (of U).
FL = 835 # Focal length.

# Robot:
RW = 100 # Robot width.
RH = 91 # Robot height.
RL = 100 # Robot length.

# Field:
TH = 15 + 36//2 # The height of the tower
CUW = 260 # (Center U Width) The U width as it looks in the camera when it is in the center (in pixels).
CUWD = 220 # The distance which the CUW was calculated from.

# Camera:
HAX = 53 # The head angle of the camera (x)
HAY = 40 # The head angle of the camera (y)

# Camera Settings:
brightness = -0.1
saturation = 15
exposure = -1
rotateClockwise = True # Is the image rotated by 90 degrees clockwise or counter clockwise. True is clockwise.
resizedImageWidth = 320
resizedImageHeight = 180