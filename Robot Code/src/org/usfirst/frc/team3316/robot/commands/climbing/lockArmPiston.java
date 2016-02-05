package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class lockArmPiston extends DBugCommand
{

	protected void init()
	{}

	protected void execute()
	{
		Robot.climbing.lockArmPiston();
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
