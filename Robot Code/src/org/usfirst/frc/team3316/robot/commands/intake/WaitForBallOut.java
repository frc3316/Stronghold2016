package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class WaitForBallOut extends DBugCommand
{

	protected void init()
	{}

	protected void execute()
	{}

	protected boolean isFinished()
	{
		return Robot.intake.isBallOut();
	}

	protected void fin()
	{}

	protected void interr()
	{}
	
}
