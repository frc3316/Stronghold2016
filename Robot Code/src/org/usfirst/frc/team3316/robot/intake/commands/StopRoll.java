package org.usfirst.frc.team3316.robot.intake.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class StopRoll extends DBugCommand
{
	public StopRoll() {
		requires(Robot.intake);
	}
	
	protected void init()
	{}

	protected void execute()
	{
		Robot.intake.setMotor(0);
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
