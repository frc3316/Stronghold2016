package test_package;
//package org.usfirst.frc.team3316.robot.chassis.motion;

import java.util.ArrayList;

/*
import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
*/

public class MotionPlanner
{
	/*
	static Config config = Robot.config;
	static DBugLogger logger = Robot.logger;
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

	public static class PlannedMotion
	{
		private double maxAccel, maxDecel, maxVelocity;

		private double vMax;

		private double accelTime;
		private double decelTime;
		private double cruiseTime;

		private double accelDistance;
		private double cruiseDistance;
		private double decelDistance;

		private PlannedMotion(double maxAccel, double maxDecel,
				double maxVelocity, double vMax, double accelTime,
				double cruiseTime, double decelTime)
		{
			this.maxAccel = maxAccel;
			this.maxDecel = maxDecel;
			this.maxVelocity = maxVelocity;

			this.vMax = vMax;

			this.accelTime = accelTime;
			this.decelTime = decelTime;
			this.cruiseTime = cruiseTime;

			/*
			 * Calculating some movement constants derived from specified ones
			 * for improved code clearance
			 */
			accelDistance = (maxAccel / 2) * Math.pow(accelTime, 2);
			cruiseDistance = vMax * (cruiseTime);
			decelDistance = vMax * decelTime
					+ (maxDecel / 2) * Math.pow(decelTime, 2);
		}

		/**
		 * Returns the distance traveled from the start of the motion until the
		 * specified time
		 * 
		 * @param time
		 *            The time specified in seconds
		 * @return Total distance traveled in meters
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
		 * Returns the velocity at a certain time in the motion
		 * 
		 * @param time
		 *            The time specified in seconds
		 * @return The velocity in (m/s) at the specified time
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
		 * Returns the acceleration at a specified time in the motion
		 * 
		 * @param time
		 *            The time specified in seconds
		 * @return The acceleration in (m/s^2) at the specified time
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
		 * Returns an array of steps at a constant time step, each containing:
		 * time since start of the movement, total distance traveled, current
		 * velocity and current acceleration
		 * 
		 * @param timeStep
		 *            The time step between each two steps
		 * @return An array of all of the steps
		 */
		public Step[] convertToStepArray(double timeStep)
		{
			ArrayList<Step> steps = new ArrayList<>();

			double currentTime = 0;

			while (currentTime < (accelTime + cruiseTime + decelTime))
			{
				steps.add(new Step(getAcceleration(currentTime),
						getVelocity(currentTime), getPosition(currentTime),
						currentTime));
				
				currentTime += timeStep;
			}
			
			currentTime = accelTime + cruiseTime + decelTime;
			
			steps.add(new Step(getAcceleration(currentTime),
					getVelocity(currentTime), getPosition(currentTime),
					currentTime));

			return steps.toArray(new Step[0]);
		}
	}

	static double maxAccel, maxDecel, maxVelocity, timeStep;

	static
	{
		//updateParameters();
	}

	public static PlannedMotion planMotion(double distance)
	{
		//updateParameters();

		PlannedMotion motion;

		// tTriangle is the time it would take if the velocity graph was a
		// triangle
		double tTriangle = Math.sqrt((2 * distance * (maxAccel - maxDecel)) / (maxAccel * -maxDecel));
		
		System.out.println("T triangle: " + tTriangle);

		// Maximum velocity in a triangle profile
		double vMaxTriangle = tTriangle
				* ((maxAccel * -maxDecel) / (maxAccel - maxDecel));

		double vMax = 0; // The maximum velocity we reach
		double tAccel = 0; // Time of the acceleration part
		double tDecel = 0; // Time of the deceleration part
		double tCruise = 0; // Time of the cruising part (when moving at a
							// constant velocity)
		double tTotal = 0; // Total time for movement

		if (vMaxTriangle > maxVelocity)
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
		System.out.println("Total time: " + tTotal);
		
		System.out.println("Accel time: " + tAccel);
		System.out.println("Cruise time: " + tCruise);
		System.out.println("Decel time: " + tDecel);
		
		System.out.println("Maximum velocity reached:" + vMax);

		motion = new PlannedMotion(maxAccel, maxDecel, maxVelocity, vMax,
				tAccel, tCruise, tDecel);

		return motion;
	}

	public static void setStuff (double maxAccel, double maxDecel, double maxVelocity)
	{
		MotionPlanner.maxAccel = maxAccel;
		MotionPlanner.maxDecel = maxDecel;
		MotionPlanner.maxVelocity = maxVelocity;
	}
	
	/**
	 * Updates variables from the config
	 */
	/*
	private static void updateParameters()
	{
		maxAccel = (double) config.get("motionPlanner_MaxAccel");
		maxDecel = (double) config.get("motionPlanner_Maxdecel");
		maxVelocity = (double) config.get("motionPlanner_MaxVelocity");
		timeStep = (double) config.get("motionPlanner_TimeStep");
	}
	*/
	
}
