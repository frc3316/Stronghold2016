package org.usfirst.frc.team3316.robot.chassis.motion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Queue;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;

public class MotionPlanner
{
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
	}

	public static class PlannedMotion
	{

	}

	static Config config = Robot.config;
	static DBugLogger logger = Robot.logger;

	static double maxAccel, maxdecel, maxVelocity, timeStep;

	static
	{
		maxAccel = (double) config.get("motionPlanner_MaxAccel");
		maxdecel = -1 * (double) config.get("motionPlanner_Maxdecel");
		maxVelocity = (double) config.get("motionPlanner_MaxVelocity");
		timeStep = (double) config.get("motionPlanner_TimeStep");
	}

	public static Step[] planMotion(double distance)
	{
		ArrayList<Step> accelList = calculateAccelSteps(maxVelocity, maxAccel);
		ArrayList<Step> decelList = calculateAccelSteps(maxVelocity, maxdecel);

		flipdecelList(decelList, distance);

		double accelDistance = accelList.get(accelList.size() - 1).position
				- accelList.get(0).position; // how much we go in the accel part

		double decelDistance = decelList.get(decelList.size() - 1).position
				- decelList.get(0).position; // how much we go in the decel
												// part

		/*
		 * Distances combined are what we were looking for? Combining is easy!
		 */
		if (accelDistance + decelDistance == distance)
		{
			ArrayList<Step> finalList = addTwoStepLists(accelList, decelList);

			return finalList.toArray(new Step[0]);
		}
		/*
		 * Distance is too much, need to trim lists when velocities are equal and total distance is okay
		 */
		else if (accelDistance + decelDistance > distance)
		{
			for (Step step : accelList)
			{
				
			}
		}
		/*
		 * Distance is too little, need to add a part of constant velocity
		 */
		else
		{

		}

		return null;
	}

	/**
	 * Given a max velocity and a constant acceleration, returns a list of the
	 * steps required to reach that max velocity. Assumes velocity and
	 * acceleration are positive and starting at position and velocity 0.
	 */
	private static ArrayList<Step> calculateAccelSteps(double maxVelocity,
			double accel)
	{
		ArrayList<Step> steps = new ArrayList<>();

		Step currentStep = new Step(0, 0, accel, 0);
		steps.add(new Step(currentStep));

		while (currentStep.velocity < maxVelocity)
		{
			currentStep.accel = accel; // Trapezoid profile

			currentStep.velocity += currentStep.accel * timeStep;

			if (currentStep.velocity > maxVelocity)
			{
				currentStep.velocity = maxVelocity;
			}

			currentStep.position += currentStep.velocity * timeStep;

			currentStep.time += timeStep;

			steps.add(new Step(currentStep));
		}
		return steps;
	}

	/**
	 * Flips a list of accelerating steps into decelerating steps.
	 * 
	 * @param decelList
	 *            The list to flip.
	 * @param maxPosition
	 *            The final distance of the total motion.
	 */
	private static void flipdecelList(ArrayList<Step> decelList,
			double maxPosition)
	{
		double maxVelocity = decelList.get(decelList.size() - 1).velocity; // velocity
																			// of
																			// last
																			// step

		/*
		 * Flipping distances, velocities and acceleration
		 */
		for (Step step : decelList)
		{
			step.position = maxPosition - step.position;
			step.velocity = maxVelocity - step.velocity;
			step.accel *= -1; // flip acceleration sign because positive
								// acceleration was used for creating the list
		}
	}

	/**
	 * Adds 2 lists so that their time steps are coordinated.
	 * 
	 * @return A reference to the first list (which is now the combined list).
	 *         Ruins the 2 lists in the process.
	 */
	private static ArrayList<Step> addTwoStepLists(ArrayList<Step> firstList,
			ArrayList<Step> lastList)
	{
		double firstTime = firstList.get(firstList.size() - 1).time;

		for (Step step : lastList)
		{
			step.time += firstTime;
		}

		firstList.addAll(lastList);

		return firstList;
	}
}
