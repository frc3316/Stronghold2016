package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.WaitForWarmingUp;

public class AutonShootingSequence extends DBugCommandGroup
{
	public AutonShootingSequence()
	{
		addParallel(new WarmUpShooterSequence());
		addSequential(new WaitForWarmingUp());
		addSequential(new AutonShootingSequence());
	}

	protected void init()
	{
		setTimeout(6.5);
	}

	protected boolean isFinished()
	{
		return super.isFinished() || isTimedOut();
	}
}
