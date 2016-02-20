package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class CloseIntake extends DBugCommand
{
	private double timeout;
	protected void init()
	{
		timeout = (double) config.get("intake_CloseIntake_Timeout"); // In seconds
	}

	protected void execute()
	{
		Robot.intake.closeIntake();
	}

	protected boolean isFinished()
	{
		return timeSinceInitialized() > timeout;
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
