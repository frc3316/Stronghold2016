package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;

public class WarmShooter extends DBugCommandGroup
{
	public WarmShooter ()
	{
		addSequential(new AccelerateFlywheel());
		addSequential(new FlywheelPID());
	}
}
