package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.RetractOmni;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.Direction;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.ReachDefense;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousShootingSequence extends DBugCommandGroup {
	public AutonomousShootingSequence() {
		addSequential(new RetractOmni());
		addParallel(new TurretPID());
		addSequential(new CrossingForwardSequence());
		addSequential(new WaitCommand(0.7)); // We want the robot to stop before
												// targeting the shooter.
		addSequential(new AutonShootingSequence());
		addSequential(new ReachDefense(Direction.BACKWARDS));
	}
}
