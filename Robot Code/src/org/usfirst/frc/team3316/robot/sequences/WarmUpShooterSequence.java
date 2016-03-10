package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.WaitForWarmingUp;
import org.usfirst.frc.team3316.robot.commands.flywheel.FlywheelPID;
import org.usfirst.frc.team3316.robot.commands.flywheel.WarmShooter;
import org.usfirst.frc.team3316.robot.commands.hood.HoodBangbang;
import org.usfirst.frc.team3316.robot.commands.hood.HoodPID;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollOut;
import org.usfirst.frc.team3316.robot.commands.transport.TransportRollIn;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;

public class WarmUpShooterSequence extends DBugCommandGroup
{
	public WarmUpShooterSequence()
	{
		addParallel(new TurretPID());
		addParallel(new WarmShooter());
	}
}
