package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class JoystickWinchControl extends DBugCommand
{
	protected void init()
	{}

	protected void execute()
	{
		isFin = Robot.climbing.setMotors(-Robot.joysticks.joystickOperator.getRawAxis(5)); // Coz flight
																							// simulators
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		Robot.climbing.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
