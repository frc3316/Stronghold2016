package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class StopWinch extends DBugCommand
{
	public StopWinch ()
	{
		requires(Robot.climbing);
	}
	
	protected void init()
	{
	}

	protected void execute()
	{
		Robot.climbing.setMotors(0);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
	
}
