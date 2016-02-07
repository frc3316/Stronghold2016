package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForRung extends DBugCommand {

	protected void init()
	{
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return Robot.climbing.isOnRung();
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}

}
