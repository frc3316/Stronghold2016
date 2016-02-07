package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossBrake;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossDefense;

public class CrossingForwardSequence extends DBugCommandGroup
{
	public CrossingForwardSequence() {
		addSequential(new CrossDefense(false));
		addSequential(new CrossBrake(false));
	}
}
