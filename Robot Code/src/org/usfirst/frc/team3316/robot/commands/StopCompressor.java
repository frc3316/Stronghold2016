package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class StopCompressor extends DBugCommand
{
	protected void init() {}

	protected void execute()
	{
		Robot.actuators.compressor.stop();
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin() {}

	protected void interr() {}
}
