package org.usfirst.frc.team3316.robot.chassis.motion;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

public class MotionPlanner
{
	static Config config = Robot.config;
	static DBugLogger logger = Robot.logger;

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
		private Step[] steps;

		public PlannedMotion(Step[] steps)
		{
			this.steps = steps;
		}

		public Step[] getSteps()
		{
			return steps;
		}

		public String toString()
		{
			String toReturn = "";

			toReturn += "Number of steps: " + steps.length + "\n";
			toReturn += "Time\tPosition\tVelocity\tAcceleration\n";

			for (int i = 0; i < steps.length; i++)
			{
				toReturn += steps[i].toString();
			}

			return toReturn;
		}
	}

	static double maxAccel, maxDecel, maxVelocity, timeStep;

	static
	{
		maxAccel = (double) config.get("motionPlanner_MaxAccel");
		maxDecel = (double) config.get("motionPlanner_Maxdecel");
		maxVelocity = (double) config.get("motionPlanner_MaxVelocity");
		timeStep = (double) config.get("motionPlanner_TimeStep");
	}

	public static PlannedMotion planMotion(double distance)
	{
		updateParameters();
	
		ArrayList<Step> accelList = calculateAccelSteps(maxVelocity, maxAccel);
		ArrayList<Step> decelList = calculateDecelSteps(maxVelocity, maxDecel);

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
			List<Step> finalList = addTwoStepLists(accelList, decelList);

			return new PlannedMotion(finalList.toArray(new Step[0]));
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

				if (accelToCheck >= (maxDecel) && accelToCheck <= maxAccel) // maxDecel
																			// is
																			// neg
				{
					List<Step> finalList = addTwoStepLists(
							accelList.subList(0, index + 1), decelList.subList(
									complementIndex, decelList.size()));

					return new PlannedMotion(finalList.toArray(new Step[0]));
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

			double distanceToReach = distance
					- (decelDistance + maxVelocity * timeStep);

			Step newStep;
			Step lastStep = accelList.get(accelList.size() - 1);

			while (lastStep.position < distanceToReach)
			{
				newStep = new Step(0, 0, 0, 0);
				newStep.velocity = maxVelocity;
				newStep.time = lastStep.time + timeStep;
				newStep.position = lastStep.position
						+ (newStep.velocity * timeStep);

				accelList.add(new Step(newStep));
				lastStep = newStep;
			}

			List<Step> finalList = addTwoStepLists(accelList, decelList);

			return new PlannedMotion(finalList.toArray(new Step[0]));
		}
	}

	/**
	 * Updates variables from the config
	 */
	private static void updateParameters()
	{
		maxAccel = (double) config.get("motionPlanner_MaxAccel");
		maxDecel = (double) config.get("motionPlanner_Maxdecel");
		maxVelocity = (double) config.get("motionPlanner_MaxVelocity");
		timeStep = (double) config.get("motionPlanner_TimeStep");
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

		Step currentStep = new Step(accel, 0, 0, 0);
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

	private static ArrayList<Step> calculateDecelSteps(double maxVelocity,
			double decel)
	{
		ArrayList<Step> steps = new ArrayList<>();

		Step currentStep = new Step(decel, maxVelocity, 0, 0);

		steps.add(new Step(currentStep));

		while (currentStep.velocity > 0)
		{
			currentStep.accel = decel; // Trapezoid profile

			currentStep.velocity += currentStep.accel * timeStep;

			if (currentStep.velocity < 0)
			{
				currentStep.velocity = 0;
			}

			currentStep.position += currentStep.velocity * timeStep;

			currentStep.time += timeStep;

			steps.add(new Step(currentStep));
		}

		return steps;
	}

	/**
	 * Adds 2 lists so that their time steps are coordinated.
	 * 
	 * @return A reference to the first list (which is now the combined list).
	 *         Ruins the 2 lists in the process.
	 */
	private static List<Step> addTwoStepLists(List<Step> firstList,
			List<Step> lastList)
	{
		//how much excess time there is between the two lists
		double timeOffset = (lastList.get(0).time
				- firstList.get(firstList.size() - 1).time) - timeStep; 
		
		//how much excess position there is between the two lists
		double positionOffset = (lastList.get(0).position
				- firstList.get(firstList.size() - 1).position)
				- firstList.get(firstList.size() - 1).velocity * timeStep; 

		for (Step step : lastList)
		{
			step.time -= timeOffset;
			step.position -= positionOffset;
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
		int index = -1;
		double minDifferenceDistance = Double.MAX_VALUE;

		for (int i = 0; i < list.size(); i++)
		{
			Step step = list.get(i);
			double distance = list.get(list.size() - 1).position
					- step.position;

			double differenceDistance = Math.abs(distance - distanceToFind);

			if (differenceDistance < minDifferenceDistance)
			{
				minDifferenceDistance = differenceDistance;
				index = i;
			}
		}

		return index;
	}
}
