package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class AdjustHood extends DBugCommand
{
	
	protected void init()
	{}

	protected void execute()
	{
		Robot.hood.setHoodAdjusting(true);
	}

	protected boolean isFinished()
	{
		return Robot.hood.toAdjustHood();
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
