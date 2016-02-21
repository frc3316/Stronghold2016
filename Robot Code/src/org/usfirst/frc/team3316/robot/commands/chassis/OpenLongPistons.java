package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class OpenLongPistons extends DBugCommand
{
	protected void init()
	{
		
	}

	protected void execute()
	{
		Robot.chassis.openLongPistons();
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin()
	{
		
	}

	protected void interr()
	{
		
	}
}
