package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Watchdog command for the hood. Prevents a situation where a command sets
 * the subsystem to a certain speed, finishes and doesn't set it to 0.
 * 
 * @author D-Bug
 *
 */
public class StopHood extends DBugCommand
{
	public StopHood()
	{
		requires(Robot.hood);
	}

	protected void init()
	{
		Robot.hood.setMotors(0);
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
}
