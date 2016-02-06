package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ToggleOmni extends DBugCommand
{
	private DBugCommand extend, retract;

	public ToggleOmni()
	{
		extend = new ExtendOmni();
		retract = new RetractOmni();
	}

	protected void init()
	{
		// We want to allow the driver to cancel the extension command at any time
		if (Robot.chassis.areLongPistonsExtended())
		{
			retract.start();
		}
		else
		{
			extend.start();
		}
	}

	protected void execute()
	{
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
