package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class StopRoll extends DBugCommand
{
	//TODO: Add commenting

	public StopRoll() {
		requires(Robot.intake);
	}
	
	protected void init()
	{}

	protected void execute()
	{
		Robot.intake.setMotors(0);
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
