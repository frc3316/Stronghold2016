package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossBrake;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossDefense;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.Direction;

public class CrossingBackSequence extends DBugCommandGroup
{
	public CrossingBackSequence() {
		addSequential(new CrossDefense(Direction.BACKWARDS));
		addSequential(new CrossBrake(Direction.BACKWARDS));
	}
}
