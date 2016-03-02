"""
This is the main module that handles and uses all other modules for the Python CV
"""
import argparse
import traceback

from VisionManager import *
from NetworkManager import *
from FPSCounter import *
import cv2
import numpy as np
from Utils import *
from Constants import *

# Lock File:
lockFile = LockFile("LockFile.loc")


def parse_arguments():
    parser = argparse.ArgumentParser()
    parser.add_argument("--host", type=str, default=ROBORIO_MDNS,
                        help="Roborio host address")
    parser.add_argument("--port", type=int, default=ROBORIO_PORT,
                        help="Roborio host port")
    parser.add_argument("--dump_image", action="store_true", default=False,
                        help="Dump images to current working directory")
    parser.add_argument("--enable_network", action="store_true", default=False,
                        help="Enable network communications")
    return parser.parse_args()


if __name__ == "__main__":
    if lockFile.is_locked():
        raise ValueError("Lock File Locked!")
    else:
        lockFile.lock()

    args = parse_arguments()

    try:
        vision_manager = VisionManager()
    except ValueError, ex:
        logger.error("Failed initializing VisionManager: %s", ex.message)
        raise

    if args.enable_network:
        network_manager = NetworkManager(args.host, args.port)
    else:
        network_manager = None

    FPSCounter = FPS()
    FPSCounter.start()

    try:
        read_frames = 0  # the number of frames read so far

        while True:
            if args.dump_image and (read_frames % 30 == 0):
                save_frame_path = "current.png"
                save_mask_path = "masked.png"
            else:
                save_frame_path = None
                save_mask_path = None

            goal_image = vision_manager.get_goal_image(save_frame_path=save_frame_path,
                                                       save_mask_path=save_mask_path)
            if not goal_image:
                if network_manager:
                    network_manager.send_no_data()

            FPSCounter.update()
            read_frames += 1

            if network_manager:
                network_manager.send_data(goal_image)

    except Exception, ex:
        logger.error("Unhandled Exception:\n" + traceback.format_exc())
        raise

    finally:
        cv2.destroyAllWindows()
        vision_manager.cam.release()
        if network_manager:
            network_manager.sock.close()
