package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.intake.commands.CloseIntake;
import org.usfirst.frc.team3316.robot.intake.commands.OpenIntake;
import org.usfirst.frc.team3316.robot.intake.commands.RollIn;
import org.usfirst.frc.team3316.robot.intake.commands.StopRoll;
import org.usfirst.frc.team3316.robot.intake.commands.WaitForBallIn;

public class CollectBall extends DBugCommandGroup
{
	public CollectBall() {
		addSequential(new OpenIntake());
		addParallel(new RollIn());
		addSequential(new WaitForBallIn());
		addSequential(new StopRoll());
		addSequential(new CloseIntake());
	}
}
