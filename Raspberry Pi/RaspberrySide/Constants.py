import numpy as np

# Color Filter:
LOWER_COLOR_BOUND = np.array([50, 160, 100]) # Lower bond
UPPER_COLOR_BOUND = np.array([120, 255, 255]) # Upper bond

ROBORIO_MDNS = "roborio-3316-frc.local"
ROBORIO_PORT = 8080

# Bounding rectangle
MIN_BOUND_RECT_AREA = 100 # The minimum area of a contour to be considered as a possible object (U). the area is calculated using
# cv2.contourArea
MAX_BOUND_RECT_AREA = 10000 # The maximum area of a contour to be considered as a possible object (U). the area is calculated using
# cv2.contourArea
MAX_HIGHT_WIDTH_RATIO = 10 # The maximum ratio of height/width of the bounding rectangle of a contour to be considered as a potential contour.
MIN_HIGHT_WIDTH_RATIO = 0 # The maximum ratio of height/width of the bounding rectangle of a contour to be considered as a potential contour.

OBJECT_HEIGHT = 36 # Known height (of U). The real height of the U in cm.
OBJECT_WIDTH = 50.8 # Known width  (of U). The real width of the U in cm.
ENLARGEMENT_FACTOR = 180 * 500 / 170 # Enlargement factor in px -- 180px (height of image) * 500cm (distance to object) / 170cm (height of object)
# object in pixels in the distance D, and W is the real height of the object.

# Robot:
ROBOT_WIDTH = 100 # Robot width in cm, unused right now.
ROBOT_HEIGHT = 0 # Robot height in cm.
ROBOT_LENGTH = 100 # Robot length in cm, unused right now.

# Field:
TOWER_HEIGHT = 130 + 36//2 # The height of the tower

# Camera:
CAMERA_YAW = 0  # in degrees
CAMERA_PITCH = 15  # in degrees
CAMERA_VIEW_ANGLE_Y = 59.8  # in degrees
CAMERA_VIEW_ANGLE_X = 35.2  # in degrees

# Camera Settings:
BRIGHTNESS = 0.01
SATURATION = 1
EXPOSURE = 0.9
CONTRAST = 0.01
ROTATE_CLOCKWISE = False # Is the image rotated by 90 degrees rotateClockWise or counter rotateClockWise. True is rotateClockWise.
RESIZE_IMAGE_WIDTH = 320 # the width of the resized image.
RESIZE_IMAGE_HEIGHT = 240
