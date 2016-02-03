package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class OpenIntake extends DBugCommand
{
	//TODO: Add commenting

	protected void init()
	{}

	protected void execute()
	{
		Robot.intake.openIntake();
	}

	protected boolean isFinished()
	{
		return Robot.intake.isIntakeOpen();
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
