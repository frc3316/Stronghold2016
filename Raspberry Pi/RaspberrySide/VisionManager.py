import cv2
import numpy as np
from Constants import *
from DistanceHelper import *
from Utils import *
from collections import namedtuple

BoundingRect = namedtuple('BoundingRect', ('contour', 'width', 'height', 'x_offset', 'y_offset'))
ImageObject = namedtuple('ImageObject', ('tower_distance', 'azimuth_angle', 'polar_angle'))


class VisionManager(object):
    """
    A class that manages all the computer vision for the FRC 2016.
    """
    READ_BUFFER_AMOUNT = 4  # Amount of frames to read in each update in order to clear buffer

    def __init__(self):
        self.currentImage = None
        self.maskedImage = None
        self.cam = VisionManager.init_camera()

    @staticmethod
    def init_camera():
        cam = cv2.VideoCapture(getCameraNumber())
        if not cam.isOpened():
            raise ValueError("Camera Not Found")

        if not cam.set(3, RESIZE_IMAGE_WIDTH):
            logger.warning("Set width failed")

        if not cam.set(4, RESIZE_IMAGE_HEIGHT):
            logger.warning("Set height failed")

        if not cam.set(cv2.cv.CV_CAP_PROP_BRIGHTNESS, BRIGHTNESS):
            logger.warning("Set brightness failed")

        if not cam.set(cv2.cv.CV_CAP_PROP_SATURATION, SATURATION):
            logger.warning("Set saturation failed")

        if not cam.set(cv2.cv.CV_CAP_PROP_EXPOSURE, EXPOSURE):
            logger.warning("Set exposure failed")

        if not cam.set(cv2.cv.CV_CAP_PROP_CONTRAST, CONTRAST):
            logger.warning("Set contrast failed")

        return cam

    def capture_frame(self):
        """
        This method updates the current image of the VisionManager instance.
        :return: None.
        """

        got_image, new_frame = self.cam.read()
        if not got_image:  # Can't event grab one frame - might be a problem
            logger.error("Couldn't Read Image from self.cam!")
            return

        for _ in xrange(self.READ_BUFFER_AMOUNT - 1):  # minus one, since we already grabbed one frame.
            frame = new_frame
            got_image, new_frame = self.cam.read()  # Grab another frame

            if not got_image:
                break  # Seems like the buffer is empty - lets use the last frame

        else:  # this occurs if the loop wasn't broken - lets use the new frame
            frame = new_frame

        return np.rot90(frame, 1) if ROTATE_CLOCKWISE else np.rot90(frame, 3)

    @staticmethod
    def get_masked_frame(frame):
        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

        # Threshold the HSV image to get only blue colors.
        mask = cv2.inRange(hsv, LOWER_COLOR_BOUND, UPPER_COLOR_BOUND)

        # Bitwise-AND mask and original image.
        return cv2.bitwise_and(frame, frame, mask=mask)

    @staticmethod
    def get_masked_frame_threshold(masked_frame):
        """
        This method updates self.maskedImage, self.threshImage, using self.currentImage.
        :return: None
        """
        thresh = cv2.threshold(masked_frame, 25, 255, cv2.THRESH_BINARY)[1]
        # dilate the threshed image to fill in holes, then find contours on thresholded image.
        thresh = cv2.dilate(thresh, None, iterations=0)

        return thresh

    def get_bounding_rects(self, thresh):
        """
        This method calculates the bounding rect of the biggest object that matches the min-max colors in
        self.current_image.
        :return: None if no object, else the bounding rect in the format of (x, y, w, h).
        """

        (contours, _) = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

        if not contours:
            return

        rects = []
        for contour in contours:
            area = cv2.contourArea(contour)
            if (MIN_BOUND_RECT_AREA > area) or (MAX_BOUND_RECT_AREA < area):
                continue

            (x_offset, y_offset, width, height) = cv2.boundingRect(contour)
            ratio = height / width
            if (MIN_HIGHT_WIDTH_RATIO > ratio) or (MIN_HIGHT_WIDTH_RATIO < ratio):
                continue

            rects.append(
                BoundingRect(contour=contour, height=height, width=width, x_offset=x_offset, y_offset=y_offset))

        return rects

    def get_goal_image(self, save_frame_path=None, save_mask_path=None):
        """
        This method updates the vars holding the scales and distance from camera of the object.
        :return: None
        """
        frame = self.capture_frame()
        masked_frame = VisionManager.get_masked_frame(frame)
        thresh = VisionManager.get_masked_frame_threshold(masked_frame)

        bounding_rects = self.get_bounding_rects(thresh)
        if len(bounding_rects) == 0:
            # There are no rects to calculate area for
            return

        bounding_rects = sorted(bounding_rects, key=lambda r: r.height * r.width, reverse=True)
        best_bounding_rect = bounding_rects[0]

        min_area_bounding_rect = cv2.minAreaRect(best_bounding_rect.contour)

        try:
            azimuth_angle = get_azimuth_angle(x_offset=best_bounding_rect.x_offset,
                                              image_width=best_bounding_rect.width,
                                              frame_width=frame.shape[1],
                                              viewing_angle=CAMERA_VIEW_ANGLE_X)

            polar_angle = get_polar_angle(y_offset=best_bounding_rect.y_offset,
                                          image_height=best_bounding_rect.height,
                                          frame_height=frame.shape[0],
                                          viewing_angle=CAMERA_VIEW_ANGLE_Y)

            best_bounding_rect.height = normalize_rectangle_skew(
                bounding_rectangle_height=best_bounding_rect.height,
                bounding_rectangle_width=best_bounding_rect.width,
                min_area_bounding_rectangle_angle=min_area_bounding_rect[2])

            best_bounding_rect.height = normalize_rectangle_keystone(
                bounding_rectangle_height=best_bounding_rect.height,
                polar_angle=polar_angle,
                camera_pitch=CAMERA_PITCH)

            distance_from_tower = get_distance_from_tower(image_height=best_bounding_rect.height,
                                                          object_height=OBJECT_HEIGHT,
                                                          enlargement_factor=ENLARGEMENT_FACTOR)
        except ValueError, ex:
            logger.warning("Failed normalizing rectangle height: %s", ex.message)
            return

        if save_frame_path:
            cv2.imwrite(save_frame_path, frame)

        if save_mask_path:
            self.draw_target_square(x_offset=best_bounding_rect.x_offset,
                                    y_offset=best_bounding_rect.y_offset,
                                    height=best_bounding_rect.height,
                                    width=best_bounding_rect.width)
            cv2.imwrite(save_mask_path, masked_frame)

        return ImageObject(tower_distance=distance_from_tower,
                           azimuth_angle=azimuth_angle,
                           polar_angle=polar_angle)

    def draw_target_square(self, x_offset, y_offset, height, width, color=(0, 0, 255), stroke=2):
        """
        Draws a red rectangle around the detected goal image
        :param x_offset: offset x from left of frame
        :param y_offset: offset y from top of frame
        :param height: height of goal image
        :param width: width of goal image
        :param color: color of rectangle (default: red)
        :param stroke: stroke width (default: 2px)
        :return: None
        """
        cv2.rectangle(self.maskedImage, (x_offset, y_offset), (x_offset + width, y_offset + height), color, stroke)
