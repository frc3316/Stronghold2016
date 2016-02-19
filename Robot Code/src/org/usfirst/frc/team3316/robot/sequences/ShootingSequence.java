package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.WaitForWarming;
import org.usfirst.frc.team3316.robot.commands.flywheel.FlywheelPID;
import org.usfirst.frc.team3316.robot.commands.hood.HoodPID;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.commands.transport.RollIn;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;

public class ShootingSequence extends DBugCommandGroup
{
	public ShootingSequence()
	{
		addParallel(new HoodPID());
		addParallel(new TurretPID());
		addParallel(new FlywheelPID());
		addSequential(new WaitForWarming());
		addParallel(new RollOut());
		addParallel(new RollIn());
	}
}
