package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollIn;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollOut;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeStopRoll;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallIn;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallOut;

public class EjectBall extends DBugCommandGroup
{
	public EjectBall()
	{
//		addSequential(new CloseIntake());
//		addParallel(new IntakeRollIn());
//		addSequential(new WaitForBallIn());
		addSequential(new OpenIntake());
		addParallel(new IntakeRollOut());
		addSequential(new WaitForBallOut());
//		addSequential(new IntakeStopRoll());
//		addSequential(new CloseIntake());
	}
	
	protected void fin ()
	{
		addSequential(new IntakeStopRoll());
		addSequential(new CloseIntake());
	}
	
	protected void interr ()
	{
		fin();
	}
}
