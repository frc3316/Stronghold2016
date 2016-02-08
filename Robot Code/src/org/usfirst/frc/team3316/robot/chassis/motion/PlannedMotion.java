package org.usfirst.frc.team3316.robot.chassis.motion;

import java.util.ArrayList;

/**
 * An object representing a motion with a trapezoid velocity profile.
 * 
 * @author D-Bug
 *
 */
public class PlannedMotion
{
	/**
	 * An object representing a specific time in the motion of the robot, with
	 * its position, velocity and acceleration during a motion in 1D.
	 * 
	 * @author D-Bug
	 *
	 */
	public static class Step
	{
		double accel, velocity, position, time;

		public Step(double accel, double velocity, double position, double time)
		{
			this.accel = accel;
			this.velocity = velocity;
			this.position = position;
			this.time = time;
		}

		public Step(Step other)
		{
			this.accel = other.accel;
			this.velocity = other.velocity;
			this.position = other.position;
			this.time = other.time;
		}

		public String toString()
		{
			return time + "\t" + position + "\t" + velocity + "\t" + accel
					+ "\n";
		}
	}

	private double maxAccel, maxDecel;

	private double vMax;

	private double accelTime;
	private double decelTime;
	private double cruiseTime;

	private double accelDistance;
	private double cruiseDistance;
	private double decelDistance;

	public PlannedMotion(double maxAccel, double maxDecel, double vMax,
			double accelTime, double cruiseTime, double decelTime)
	{
		this.maxAccel = maxAccel;
		this.maxDecel = maxDecel;

		this.vMax = vMax;

		this.accelTime = accelTime;
		this.decelTime = decelTime;
		this.cruiseTime = cruiseTime;

		/*
		 * Calculating some movement constants derived from specified ones for
		 * improved code clearance
		 */
		accelDistance = (maxAccel / 2) * Math.pow(accelTime, 2);
		cruiseDistance = vMax * (cruiseTime);
		decelDistance = vMax * decelTime
				+ (maxDecel / 2) * Math.pow(decelTime, 2);
	}

	/**
	 * Returns the distance traveled from the start of the motion until the
	 * specified time.
	 * 
	 * @param time
	 *            The time specified in seconds.
	 * @return Total distance traveled in meters.
	 */
	public double getPosition(double time)
	{
		if (time <= accelTime)
		{
			// Movement at a constant acceleration
			return (maxAccel / 2) * Math.pow(time, 2);
		}
		else if (time <= accelTime + cruiseTime)
		{
			double timeInCruise = time - accelTime;

			// Movement at a constant speed
			return accelDistance + vMax * (timeInCruise);
		}
		else if (time <= accelTime + cruiseTime + decelTime)
		{
			// Movement at a constant acceleration
			double timeInDecel = time - (accelTime + cruiseTime);

			return accelDistance + cruiseDistance + vMax * timeInDecel
					+ (maxDecel / 2) * Math.pow(timeInDecel, 2);
		}
		else
		{
			return accelDistance + cruiseDistance + decelDistance;
		}
	}

	/**
	 * Returns the velocity at a certain time in the motion.
	 * 
	 * @param time
	 *            The time specified in seconds.
	 * @return The velocity in (m/s) at the specified time.
	 */
	public double getVelocity(double time)
	{
		if (time <= accelTime)
		{
			// Movement at a constant acceleration

			return maxAccel * time;
		}
		else if (time <= accelTime + cruiseTime)
		{
			// Movement at a constant speed

			return vMax;
		}
		else if (time <= accelTime + cruiseTime + decelTime)
		{
			// Movement at a constant acceleration
			double timeInDecel = time - (accelTime + cruiseTime);

			return vMax + maxDecel * timeInDecel;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * Returns the acceleration at a specified time in the motion.
	 * 
	 * @param time
	 *            The time specified in seconds.
	 * @return The acceleration in (m/s^2) at the specified time.
	 */
	public double getAcceleration(double time)
	{
		if (time <= accelTime)
		{
			return maxAccel;
		}
		else if (time <= accelTime + cruiseTime)
		{
			return 0;
		}
		else if (time <= accelTime + cruiseTime + decelTime)
		{
			return maxDecel;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * Returns an array of steps at a constant time step, each containing: time
	 * since start of the movement, total distance traveled, current velocity
	 * and current acceleration.
	 * 
	 * @param timeStep
	 *            The time step between each two steps.
	 * @return An array of all of the steps.
	 */
	public Step[] convertToStepArray(double timeStep)
	{
		ArrayList<Step> steps = new ArrayList<>();

		double currentTime = 0;

		while (currentTime < (accelTime + cruiseTime + decelTime))
		{
			steps.add(getStep(currentTime));

			currentTime += timeStep;
		}

		currentTime = accelTime + cruiseTime + decelTime;

		steps.add(getStep(currentTime));

		return steps.toArray(new Step[0]);
	}

	/**
	 * Returns a Step object of the specified time in this motion.
	 * 
	 * @param time
	 *            The specified time.
	 * @return A step object with the total position, velocity, acceleration and
	 *         time from the start of the motion.
	 */
	public Step getStep(double time)
	{
		return new Step(getAcceleration(time), getVelocity(time),
				getPosition(time), time);
	}
	
	/**
	 * Returns the total time it takes to finish this motion.
	 * @return The total time in seconds.
	 */
	public double getTotalTime ()
	{
		return accelTime + cruiseTime + decelTime;
	}
}
