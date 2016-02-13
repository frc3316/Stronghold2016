package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class StopCompressor extends DBugCommand
{

	@Override
	protected void init() {}

	@Override
	protected void execute()
	{
		Robot.compressor.stop();
	}

	@Override
	protected boolean isFinished()
	{
		return true;
	}

	@Override
	protected void fin() {}

	@Override
	protected void interr() {}
}
