package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.StopRoll;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallIn;

public class CollectBall extends DBugCommandGroup
{
	//Has this been checked with the prototype?
	
	public CollectBall() {
		addSequential(new OpenIntake());
		addParallel(new RollIn());
		addSequential(new WaitForBallIn());
		addSequential(new StopRoll());
		addSequential(new CloseIntake());
	}
}
