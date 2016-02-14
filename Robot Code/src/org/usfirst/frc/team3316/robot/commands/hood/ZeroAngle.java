package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ZeroAngle extends DBugCommand
{

	protected void init()
	{}

	protected void execute()
	{
		Robot.hood.setOffset(-Robot.hood.getAngle());
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
