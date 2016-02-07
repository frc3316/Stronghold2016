package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class Stop extends DBugCommand
{
	public Stop ()
	{
		requires(Robot.climbing);
	}
	
	protected void init()
	{
		Robot.climbing.setMotors(0);
	}

	protected void execute()
	{
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
