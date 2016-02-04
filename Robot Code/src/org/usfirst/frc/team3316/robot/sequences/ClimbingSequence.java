package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.climbing.CloseArmPiston;
import org.usfirst.frc.team3316.robot.commands.climbing.MoveUp;
import org.usfirst.frc.team3316.robot.commands.climbing.WaitForRung;

public class ClimbingSequence extends DBugCommandGroup
{
	
	public ClimbingSequence() {
		addSequential(new CloseArmPiston());
		addSequential(new WaitForRung());
		addSequential(new MoveUp());
	}

}
