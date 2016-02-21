package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.flywheel.FlywheelPID;
import org.usfirst.frc.team3316.robot.commands.hood.HoodPID;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;

public class WaitForWarming extends DBugCommand
{

	protected void init()
	{
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return FlywheelPID.onTarget() && HoodPID.onTarget() && TurretPID.onTarget() && Robot.intake.isReadyToTransfer();
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}

}
