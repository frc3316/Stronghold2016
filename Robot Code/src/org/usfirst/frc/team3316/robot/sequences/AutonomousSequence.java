package org.usfirst.frc.team3316.robot.sequences;
//TODO: Move this to a new package of autonomous sequences.

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossDefense;

public class AutonomousSequence extends DBugCommandGroup {
	public AutonomousSequence() {
		addSequential(new CrossDefense(false));
		//addSequential(new DriveDistance(0.2, false));
		//addSequential(new ShootingSequence());
		addSequential(new CrossDefense(true));
		//addSequential(new DriveDistance(0.2, true));
	}
}
