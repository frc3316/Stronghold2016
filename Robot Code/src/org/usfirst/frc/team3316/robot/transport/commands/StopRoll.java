package org.usfirst.frc.team3316.robot.transport.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class StopRoll extends DBugCommand
{

	protected void init()
	{}

	protected void execute()
	{
		Robot.transport.setMotor(0);
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
