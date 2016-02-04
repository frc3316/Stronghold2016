package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ToggleOmni extends DBugCommand
{
	private static boolean set = true;
	
	private DBugCommand extend, retract;
	
	public ToggleOmni ()
	{
		extend = new ExtendOmni();
		retract = new RetractOmni();
	}
	
	protected void init()
	{
		if (set)
		{
			extend.start();
		}
		else
		{
			retract.start();
		}
		
		set = !set;
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
