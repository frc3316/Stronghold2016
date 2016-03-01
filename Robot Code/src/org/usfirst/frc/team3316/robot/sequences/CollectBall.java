package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntakeTransport;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollIn;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeStopRoll;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallIn;

public class CollectBall extends DBugCommandGroup
{
	public CollectBall() 
	{
		addSequential(new OpenIntake());
		addParallel(new IntakeRollIn());
		addSequential(new WaitForBallIn());
		addSequential(new IntakeStopRoll());
		addSequential(new CloseIntakeTransport());
	}

	protected void fin ()
	{
	}

	protected void interr ()
	{
		(new IntakeStopRoll()).start();
		(new CloseIntake()).start();
	}
}
