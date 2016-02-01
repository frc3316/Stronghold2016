package org.usfirst.frc.team3316.robot.chassis.motion;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

/**
 * Class that has useful methods for motion planning, assuming the robot has a
 * constant maximum velocity, and it is accelerating and decelerating at
 * constant rates.
 * 
 * @author D-Bug
 *
 */
public class MotionPlanner
{

	static Config config = Robot.config;
	static DBugLogger logger = Robot.logger;

	static double maxAccel, maxDecel, maxVelocity;

	static
	{
		updateParameters();
	}

	public static PlannedMotion planMotion ( double distance )
	{
		updateParameters();

		double maxAccel, maxDecel, maxVelocity;

		maxAccel = MotionPlanner.maxAccel;
		maxDecel = MotionPlanner.maxDecel;
		maxVelocity = MotionPlanner.maxVelocity;

		if (distance < 0)
		{
			maxAccel *= -1;
			maxDecel *= -1;
			maxVelocity *= -1;
		}

		PlannedMotion motion;

		// tTriangle is the time it would take if the velocity graph were a
		// triangle
		double tTriangle = Math.sqrt((2 * distance * (maxAccel - maxDecel))
				/ (maxAccel * -maxDecel));

		// Maximum velocity in a triangle profile
		double vMaxTriangle = tTriangle
				* ((maxAccel * -maxDecel) / (maxAccel - maxDecel));

		double vMax = 0; // The maximum velocity we reach
		double tAccel = 0; // Time of the acceleration part
		double tDecel = 0; // Time of the deceleration part
		double tCruise = 0; // Time of the cruising part (when moving at a
							// constant velocity)
		double tTotal = 0; // Total time for movement

		// If the maximum velocity in a triangular velocity profile is too high,
		// convert it to a trapezoid profile. Otherwise keep it triangular.
		if (Math.abs(vMaxTriangle) > maxVelocity)
		{
			vMax = maxVelocity;
			tAccel = vMax / maxAccel;
			tDecel = vMax / -maxDecel;

			tCruise = (distance / vMax) - ((tAccel + tDecel) / 2);
		}
		else
		{
			tTotal = tTriangle;
			vMax = vMaxTriangle;

			tAccel = tTriangle * (-maxDecel) / (maxAccel - maxDecel);
			tDecel = tTotal - tAccel;
			tCruise = 0;
		}

		motion = new PlannedMotion(maxAccel, maxDecel, vMax, tAccel, tCruise,
				tDecel);

		return motion;
	}

	/**
	 * Assuming a constant deceleration, returns the distance it would take to
	 * brake with the current velocity.
	 * 
	 * @param currentVelocity
	 *            The current velocity
	 * @return The distance it would take to brake. Positive when velocity is
	 *         positive and negative when velocity is negative.
	 */
	public static double distanceToBrake(double currentVelocity)
	{
		// By the equation: v^2 = (v0)^2 + 2a*x
		return Math.signum(currentVelocity) * Math.pow(currentVelocity, 2)
				/ (-2 * maxDecel);
	}

	/**
	 * Updates variables from the config
	 */
	private static void updateParameters()
	{
		maxAccel = (double) config.get("motionPlanner_MaxAccel");
		maxDecel = (double) config.get("motionPlanner_MaxDecel");
		maxVelocity = (double) config.get("motionPlanner_MaxVelocity");
	}
}
