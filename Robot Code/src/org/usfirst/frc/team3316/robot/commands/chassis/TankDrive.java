package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends Drive
{
	// TODO: Add commenting

	protected static Joystick joystickLeft, joystickRight;

	static boolean invertY, invertX;

	static double deadBand = 0.0;

	public TankDrive()
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

	protected static double getLeftY()
	{
		updateConfigVariables();
		double y = deadBand(joystickLeft.getY());
		if (invertY)
		{
			return -y;
		}
		return y;
	}

	protected static double getLeftX()
	{
		updateConfigVariables();
		double x = deadBand(joystickLeft.getX());
		if (invertX)
		{
			return -x;
		}
		return x;
	}

	protected static double getRightY()
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

	private static double deadBand(double x)
	{
		if (Math.abs(x) < deadBand)
		{
			return 0;
		}
		return x;
	}

	/*
	 * Here we call the get method of the config every execute because we want the variables to update without
	 * needing to cancel the commands.
	 */
	private static void updateConfigVariables()
	{
		deadBand = (double) config.get("chassis_TankDrive_DeadBand");

		invertX = (boolean) config.get("chassis_TankDrive_InvertX");
		invertY = (boolean) config.get("chassis_TankDrive_InvertY");
	}
}
