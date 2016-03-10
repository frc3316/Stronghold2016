package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.flywheel.WarmShooter;
import org.usfirst.frc.team3316.robot.commands.hood.HoodPIDNoCamera;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;

public class WarmUpShooterSequenceNoCamera extends DBugCommandGroup
{
	public WarmUpShooterSequenceNoCamera ()
	{
		addParallel(new HoodPIDNoCamera());
		addParallel(new TurretPID());
		addParallel(new WarmShooter());
	}
}
