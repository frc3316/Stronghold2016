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
		
		public Step (double accel, double velocity, double position, double time)
		{
			this.accel = accel;
			this.velocity = velocity;
			this.position = position;
			this.time = time;
		}
		
		public Step (Step other)
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
	
	static double maxAccel, maxDeccel, maxVelocity, timeStep;
	
	static
	{
		maxAccel = (double) config.get("motionPlanner_MaxAccel");
		maxDeccel = -1 * (double) config.get("motionPlanner_MaxDeccel");
		maxVelocity = (double) config.get("motionPlanner_MaxVelocity");
		timeStep = (double) config.get("motionPlanner_TimeStep");
	}
	
	public static PlannedMotion planMotion (double distance)
	{
		ArrayList <Step> accelList = calculateAccelSteps(maxVelocity, maxAccel);
		ArrayList <Step> deccelList = calculateAccelSteps(maxVelocity, maxDeccel);
		
		return null;
	}
	
	private static ArrayList<Step> calculateAccelSteps (double maxVelocity, double accel)
	{
		ArrayList<Step> steps = new ArrayList<>();
		
		Step currentStep = new Step (0, 0, accel, 0);
		steps.add(new Step(currentStep));
		
		while (currentStep.velocity < maxVelocity)
		{
			currentStep.accel = accel; //Trapezoid profile
			
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
	
	private static void flipDeccelList (ArrayList<Step> deccelList, double maxPosition)
	{
		double maxVelocity = deccelList.get(deccelList.size() - 1).velocity; //velocity of last step
		
		/*
		 * Flipping distances and velocities
		 */
		for (Step step : deccelList)
		{
			step.position = maxPosition - step.position;
			step.velocity = maxVelocity - step.velocity;
			step.accel
		}
	}
}
