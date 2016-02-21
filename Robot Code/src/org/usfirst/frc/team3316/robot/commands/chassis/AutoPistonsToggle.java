package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class AutoPistonsToggle extends DBugCommand
{
	private DBugCommand extendCommand, retractCommand;

	protected void init()
	{
		extendCommand = new ExtendOmni();
		retractCommand = new RetractOmni();
	}

	protected void execute()
	{
		if (Robot.chassis.isOnDefense())
		{
			// The command requires the chassis subsystem, so we don't need to cancel the other command.
			retractCommand.start();
		}
		else
		{
			extendCommand.start();
		}
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
