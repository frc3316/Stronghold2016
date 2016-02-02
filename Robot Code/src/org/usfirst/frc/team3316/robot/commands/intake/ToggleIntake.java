package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ToggleIntake extends DBugCommand
{
	private static boolean set = true;
	
	private static DBugCommand close, open;
	
	static
	{
		close = new CloseIntake();
		open = new OpenIntake();
	}
	
	protected void init()
	{
		
	}

	protected void execute()
	{
		if (set)
		{
			open.start();
		}
		else
		{
			close.start();
		}
		
		set = !set;
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
