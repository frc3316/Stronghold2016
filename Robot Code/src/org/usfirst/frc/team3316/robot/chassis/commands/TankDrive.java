package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends Drive 
{
	protected static Joystick joystickLeft, joystickRight;
	
	static boolean invertY, invertX;
	
	static double deadBand = 0.0;
	
	public TankDrive ()
	{
		super();
		joystickLeft = Robot.joysticks.joystickLeft;
		joystickRight = Robot.joysticks.joystickRight;
	}
	
	protected void set() 
	{
		left = getLeftY();
		right = getRightY();
	}
	
	protected static double getLeftY ()
	{
		updateConfigVariables();
		double y = deadBand(joystickLeft.getY());
		if (invertY)
		{
			return -y;
		}
		return y;
	}
	
	protected static double getLeftX ()
	{
		updateConfigVariables();
		double x = deadBand(joystickLeft.getX());
		if (invertX)
		{
			return -x;
		}
		return x;
	}
	
	protected static double getRightY ()
	{
		updateConfigVariables();
		double y = deadBand(joystickRight.getY());
		if (invertY)
		{
			return -y;
		}
		return y; 
	}
	
	protected static double getRightX() 
	{
		updateConfigVariables();
		double x = deadBand(joystickRight.getX());
		if (invertX)
		{
			return -x;
		}
		return x;
	}
	
	private static double deadBand (double x)
	{
		if (Math.abs(x) < deadBand)
		{
			return 0;
		}
		return x;
	}
	
	private static void updateConfigVariables ()
	{
		try
		{
			deadBand = (double)config.get("chassis_TankDrive_DeadBand");
			
			invertX = (boolean)config.get("chassis_TankDrive_InvertX");
			invertY = (boolean)config.get("chassis_TankDrive_InvertY");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
