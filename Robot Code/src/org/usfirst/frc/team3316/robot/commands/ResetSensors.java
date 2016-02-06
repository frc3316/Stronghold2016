package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class ResetSensors extends DBugCommand
{

	protected void init()
	{
		Robot.chassis.resetEncoders();
		Robot.chassis.resetNavX();
	}

	protected void execute()
	{}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin()
	{}
	
	protected void interr()
	{}

}
