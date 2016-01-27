package org.usfirst.frc.team3316.robot.chassis.motion;

import java.util.ArrayList;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

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

	static Config config = Robot.config;
	static DBugLogger logger = Robot.logger;

	static double maxAccel, maxDecel, maxVelocity, timeStep;

	static
	{
		maxAccel = (double) config.get("motionPlanner_MaxAccel");
		maxDecel = -1 * (double) config.get("motionPlanner_Maxdecel");
		maxVelocity = (double) config.get("motionPlanner_MaxVelocity");
		timeStep = (double) config.get("motionPlanner_TimeStep");
	}

	public static Step[] planMotion(double distance)
	{
		ArrayList<Step> accelList = calculateAccelSteps(maxVelocity, maxAccel);
		ArrayList<Step> decelList = calculateAccelSteps(maxVelocity, maxDecel);

		flipDecelList(decelList, distance);

		double accelDistance = accelList.get(accelList.size() - 1).position
				- accelList.get(0).position; // how much we go in the accel part

		double decelDistance = decelList.get(decelList.size() - 1).position
				- decelList.get(0).position; // how much we go in the decel
												// part

		/*
		 * Distances combined are what we were looking for? Adding the lists is
		 * easy!
		 */
		if (accelDistance + decelDistance == distance)
		{
			ArrayList<Step> finalList = addTwoStepLists(accelList, decelList);

			return finalList.toArray(new Step[0]);
		}
		/*
		 * Distance is too much - We need to trim the lists when velocities are
		 * equal and total distance is okay
		 */
		else if (accelDistance + decelDistance > distance)
		{
			for (int index = 0; index < accelList.size(); index++)
			{
				Step currentStep = accelList.get(index);

				// Distance is too much, no point in finding complement in decel
				// list
				double distanceAfterCurrentStep = (currentStep.position
						- accelList.get(0).position)
						+ currentStep.velocity * timeStep;

				if (distanceAfterCurrentStep > distance)
				{
					continue;
				}

				// else, find the index of complementary step and check
				// velocities
				double distanceToFind = distance - distanceAfterCurrentStep;

				int complementIndex = findIndexByDistanceFromEnd(decelList,
						distanceToFind);

				double accelToCheck = (decelList.get(complementIndex).velocity
						- currentStep.velocity) / (timeStep);

				if (accelToCheck >= (maxDecel * -1) && accelToCheck <= maxAccel) // maxDecel
																					// is
																					// neg
				{
					ArrayList<Step> finalList = addTwoStepLists(
							(ArrayList<Step>) accelList.subList(0, index + 1),
							(ArrayList<Step>) decelList.subList(complementIndex,
									decelList.size()));

					return finalList.toArray(new Step[0]);
				}
			}

			return null; // something is broken
		}
		/*
		 * Distance is too little, need to add a part of constant velocity
		 */
		else
		{
			// We're going to add the new steps to the accel list, and then add
			// to it the decel list

			double distanceToReach = distance - decelDistance;

			Step newStep;
			Step lastStep = accelList.get(accelList.size() - 1);
			
			while (lastStep.position < distanceToReach)
			{
				newStep = new Step(0, 0, 0, 0);
				newStep.velocity = maxVelocity;
				newStep.time = lastStep.time + timeStep;
				newStep.position = lastStep.position + (newStep.velocity * timeStep);
				
				accelList.add(new Step(newStep));
				lastStep = newStep;
			}
			
			ArrayList<Step> finalList = addTwoStepLists(accelList, decelList);
			
			return finalList.toArray(new Step[0]);
		}
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
	private static void flipDecelList(ArrayList<Step> decelList,
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
			step.accel *= -1; // Flip acceleration sign because positive
								// acceleration was used for creating the list.
								// Again, this is assuming trapezoid profile.
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
		double firstTotalTime = firstList.get(firstList.size() - 1).time;

		double firstsPositionInLast = lastList.get(0).position;

		double firstTotalPosition = (firstList
				.get(firstList.size() - 1).position - firstList.get(0).position)
				+ firstList.get(firstList.size() - 1).velocity * timeStep;

		for (Step step : lastList)
		{
			step.time += firstTotalTime;
			step.position = firstTotalPosition
					+ (step.position - firstsPositionInLast);
		}

		firstList.addAll(lastList);

		return firstList;
	}

	/**
	 * Finds in a list of steps the index of the step that has (dx =
	 * distanceToFind), or the closest if cannot find, compared to the last
	 * position. List must be of positive velocity and position.
	 */
	private static int findIndexByDistanceFromEnd(ArrayList<Step> list,
			double distanceToFind)
	{
		int low, high, middle;
		low = 0;
		high = list.size() - 1;

		double currentDistance = 0;

		do
		{
			middle = (low + high) / 2;

			currentDistance = list.get(list.size() - 1).position
					- list.get(middle).position;

			if (currentDistance > distanceToFind)
			{
				low = middle;
			}
			else
			{
				high = middle;
			}

		} while (middle != low);

		return middle;
	}
}
