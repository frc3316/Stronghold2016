package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class CloseIntake extends DBugCommand
{

	protected void init()
	{}

	protected void execute()
	{
		Robot.intake.closeIntake();
	}

	protected boolean isFinished()
	{
		return Robot.intake.isIntakeClose();
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
