package org.usfirst.frc.team3316.robot.sequences;
//TODO: Move this to a new package of autonomous sequences.

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.WaitForWarmingUp;
import org.usfirst.frc.team3316.robot.commands.chassis.RetractOmni;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossDefense;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.Direction;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.DriveDistance;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.ReachDefense;
import org.usfirst.frc.team3316.robot.commands.flywheel.WarmShooter;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousSequence extends DBugCommandGroup {
	public AutonomousSequence() {
		addSequential(new RetractOmni());
		addSequential(new CrossDefense(Direction.FORWARDS));
		addSequential(new WaitCommand(1.0));
		addSequential(new ReachDefense(Direction.BACKWARDS));
	}
}
