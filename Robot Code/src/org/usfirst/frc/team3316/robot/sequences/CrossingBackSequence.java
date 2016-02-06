package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossBrake;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossDefense;

public class CrossingBackSequence extends DBugCommandGroup
{
	public CrossingBackSequence() {
		addSequential(new CrossDefense(true));
		addSequential(new CrossBrake(true));
	}
}
