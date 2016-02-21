package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.commands.intake.StopRoll;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallIn;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallOut;

public class EjectBall extends DBugCommandGroup
{
	public EjectBall()
	{
		addSequential(new CloseIntake());
		addParallel(new RollIn());
		addSequential(new WaitForBallIn());
		addSequential(new OpenIntake());
		addParallel(new RollOut());
		addSequential(new WaitForBallOut());
		addSequential(new StopRoll());
		addSequential(new CloseIntake());
	}
}
