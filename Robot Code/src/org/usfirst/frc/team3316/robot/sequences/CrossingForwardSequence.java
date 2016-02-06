package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossBrake;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossDefense;

public class CrossingForwardSequence extends DBugCommandGroup
{
	public CrossingForwardSequence() {
		addSequential(new CrossDefense(false));
		addSequential(new CrossBrake(false));
	}
}
