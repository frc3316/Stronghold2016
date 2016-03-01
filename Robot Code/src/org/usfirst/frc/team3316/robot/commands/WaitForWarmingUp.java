package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class WaitForWarmingUp extends DBugCommand
{

	protected void init()
	{
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return 
//				Robot.flywheel.isOnTarget() && 
				Robot.hood.isOnTarget()
				&& Robot.turret.isOnTarget()
				&& Robot.intake.isReadyToTransfer();
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}

}
