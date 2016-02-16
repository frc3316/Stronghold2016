package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class OpenShortPistons extends DBugCommand
{
	protected void init()
	{
		
	}

	protected void execute()
	{
		Robot.chassis.openShortPistonsLeft();
		Robot.chassis.openShortPistonsRight();
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
