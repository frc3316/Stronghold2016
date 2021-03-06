package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntakeTransport;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollIn;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollOut;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeStopRoll;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallIn;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallOut;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class EjectBall extends DBugCommandGroup
{
	public EjectBall ()
	{
		addSequential(new OpenIntakeTransport());
		addParallel(new IntakeRollOut());
		addSequential(new WaitForBallOut());
		addSequential(new WaitCommand((double) config.get("ejectBall_WaitForBallIn_Timeout")));
		addSequential(new IntakeStopRoll());
		addSequential(new CloseIntake());
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
