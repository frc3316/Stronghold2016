package org.usfirst.frc.team3316.robot.sequences;
//TODO: Move this to a new package of autonomous sequences.

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.WaitForWarmingUp;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.DriveDistance;
import org.usfirst.frc.team3316.robot.commands.flywheel.WarmShooter;

public class AutonomousSequence extends DBugCommandGroup {
	public AutonomousSequence() {
		addSequential(new CrossingForwardSequence());
		addSequential(new AutonShootingSequence());
		addSequential(new CrossingBackSequence());
//		addSequential(new DriveDistance((double) config.get("chassis_Autonomous_Distance")));
	}
}
