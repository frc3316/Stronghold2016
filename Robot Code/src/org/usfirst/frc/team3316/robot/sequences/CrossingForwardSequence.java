package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossBrake;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossDefense;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.Direction;

public class CrossingForwardSequence extends DBugCommandGroup
{
	public CrossingForwardSequence() {
		addSequential(new CrossDefense(Direction.FORWARDS));
		addSequential(new CrossBrake(Direction.FORWARDS));
	}
}
