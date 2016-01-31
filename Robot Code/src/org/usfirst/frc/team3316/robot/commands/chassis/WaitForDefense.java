package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForDefense extends DBugCommand
{
	//TODO: Add commenting

	protected void init()
	{}

	protected void execute()
	{}

	protected boolean isFinished()
	{
		return !Robot.chassis.isOnDefense();
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
