package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Watchdog command for the turret. Prevents a situation where a command sets
 * the subsystem to a certain speed, finishes and doesn't set it to 0.
 * 
 * @author D-Bug
 *
 */
public class StopTurret extends DBugCommand
{
	public StopTurret()
	{
		requires(Robot.turret);
	}

	protected void init()
	{
	}

	protected void execute()
	{
		Robot.turret.setMotors(0);
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
