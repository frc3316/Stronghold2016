package org.usfirst.frc.team3316.robot.intake.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class CloseIntake extends DBugCommand
{
	
	public CloseIntake() {
		requires(Robot.intake);
	}

	protected void init()
	{}

	protected void execute()
	{
		Robot.intake.closeIntake();
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
