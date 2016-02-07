package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossBack;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossDefense;

public class CrossingSequence extends DBugCommandGroup
{
	public CrossingSequence() {
		addSequential(new CrossDefense(false));
		addSequential(new CrossBack());
	}
}
