package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.climbing.ReleaseArmPiston;
import org.usfirst.frc.team3316.robot.commands.climbing.PullUp;
import org.usfirst.frc.team3316.robot.commands.climbing.WaitForRung;

public class ClimbingSequence extends DBugCommandGroup {

	public ClimbingSequence()
	{
		addSequential(new ReleaseArmPiston());
		addSequential(new WaitForRung());
		addSequential(new PullUp());
	}

}
