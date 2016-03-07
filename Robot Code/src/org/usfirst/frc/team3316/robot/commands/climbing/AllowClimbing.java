package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Climbing;

/**
 * This is the best command ever <3
 *
 */
public class AllowClimbing extends DBugCommand
{
	protected void init() {}

	protected void execute()
	{
		Climbing.canClimb = true;
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin() {}

	protected void interr() {}
}
