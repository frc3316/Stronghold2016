package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Timer;

public class OpenIntake extends DBugCommand
{
	private double timeout;
	protected void init()
	{
		timeout = (double) config.get("intake_OpenIntake_Timeout"); // In seconds
	}

	protected void execute()
	{
		Robot.intake.openIntake();
	}

	public boolean isFinished()
	{
		return timeSinceInitialized() >= timeout;
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
