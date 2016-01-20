package org.usfirst.frc.team3316.robot.intake.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForBallIn extends DBugCommand
{

	protected void init()
	{}

	protected void execute()
	{}

	protected boolean isFinished()
	{
		return Robot.intake.isBallIn();
	}

	protected void fin()
	{}

	protected void interr()
	{}
	
}
