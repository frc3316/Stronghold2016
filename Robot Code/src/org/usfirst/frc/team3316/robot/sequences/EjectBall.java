package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.intake.commands.CloseIntake;
import org.usfirst.frc.team3316.robot.intake.commands.OpenIntake;
import org.usfirst.frc.team3316.robot.intake.commands.RollOut;
import org.usfirst.frc.team3316.robot.intake.commands.StopRoll;
import org.usfirst.frc.team3316.robot.intake.commands.WaitForBallIn;
import org.usfirst.frc.team3316.robot.intake.commands.WaitForBallOut;

public class EjectBall extends DBugCommandGroup
{
	public EjectBall()
	{
		addSequential(new WaitForBallIn());
		addSequential(new OpenIntake());
		addParallel(new RollOut());
		addSequential(new WaitForBallOut());
		addSequential(new StopRoll());
		addSequential(new CloseIntake());
	}
}
