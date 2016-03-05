from math import sin, radians, tan

def normalize_rectangle_skew(bounding_rectangle_height, bounding_rectangle_width,
                             min_area_bounding_rectangle_angle):
    """
    Finds the normalized height of the rectangle by calculating the ratio between the base and the side of the
    minimal area rectangle.

    actual_height = rectangle_height - tan(angle of minimal area rectangle) * rectangle_width
    :param bounding_rectangle_height: bounding rectangle height
    :param bounding_rectangle_width: bounding rectangle width
    :param min_area_bounding_rectangle_angle: angle of bounding rectangle of same object with minimal area
    :return: actual height for distance measurements
    """
    if min_area_bounding_rectangle_angle < -45:
        min_area_bounding_rectangle_angle += 90
    if min_area_bounding_rectangle_angle > 45:
        min_area_bounding_rectangle_angle -= 90
    if abs(min_area_bounding_rectangle_angle) > 45:
        raise ValueError("Image too skewed for adjustment")

    return bounding_rectangle_height - abs(tan(radians(min_area_bounding_rectangle_angle))) * bounding_rectangle_width


def normalize_rectangle_keystone(bounding_rectangle_height, polar_angle, camera_pitch):
    """
    Finds the normalized height of the rectangle by calculating the angle of view and calculating keystone

    actual_height = rectangle_height * sin(goal_polar_angle + 90) / sin(90 - (goal_polar_angle + camera_pitch))
    :param bounding_rectangle_height: bounding rectangle height
    :param polar_angle: polar angle between center of goal image to center of frame
    :param camera_pitch: pitch the camera is mounted on
    :return: actual height for distance measurements
    """
    return (bounding_rectangle_height * sin(radians(polar_angle + 90)) /
            sin(radians(90 - (polar_angle + camera_pitch))))


def get_distance_from_tower(image_height, object_height, enlargement_factor):
    """
    This method calculates the distance of the object from the camera using the following physics:
    - enlargementFactor is described as P*D/W, where P is the height of the object in pixels in the distance D,
    and W is the real height of the object.
    - so the following calculations is actually P*D/OH, where OH is the current object height in pixels, and P,D
    are the same P,D as above.
    :param image_height: The height of the image (in pixels) as viewed in the camera.
    :param object_height: The height of the object (in cm) in real life.
    :param enlargement_factor: enlargement factor
    :return: The distance of the object from the camera.
    """
    return go_magic(float(object_height) * enlargement_factor / image_height)


def get_angle(offset, image_size, frame_size, viewing_angle):
    """
    Get angle from center of goal to center of frame.
    :param offset: offset from start of frame in pixels
    :param image_size: size of image of object in pixels
    :param frame_size: size of whole frame in pixels
    :param viewing_angle: viewing angle for frame in requested coord
    :return:
    """
    return ((float(offset + (image_size / 2)) / frame_size) - 0.5) * viewing_angle


def get_polar_angle(y_offset, image_height, frame_height, viewing_angle):
    return (-1) * get_angle(y_offset, image_height, frame_height, viewing_angle)


def get_azimuth_angle(x_offset, image_width, frame_width, viewing_angle, rotated_rect_angle, azimuthal_go_magic):
    """
    calculates the right azimuthal angle using the angle of the rotated bounding rect of the contour
    :param x_offset: offset from start of frame in pixels
    :param image_width: the width of the image of the object
    :param frame_width: the width of the frame
    :param viewing_angle: viewing angle for frame
    :param rotated_rect_angle: the angle for the rotated bounding rectangle
    :param azimuthal_go_magic: the coefficient for the azimuth correction
    :return:
    """
    if rotated_rect_angle < -45:
        rotated_rect_angle += 90
    if rotated_rect_angle > 45:
        rotated_rect_angle -= 90

    offset = ((rotated_rect_angle/90.0)*frame_width/2.0) * azimuthal_go_magic
    return get_angle(x_offset, image_width + offset, frame_width, viewing_angle)


def go_magic(distance):
    #return 0.5417 * distance + 15 # 30/2 function, doesn't work well in very close distance and in very far distance.
    a1 = -68.952
    a2 = 0.8194
    a3 = -0.0001
    return a3 * (distance ** 2) + a2 * distance + a1
