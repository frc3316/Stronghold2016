package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollOut;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeStopRoll;
import org.usfirst.frc.team3316.robot.commands.transport.TransportRollIn;
import org.usfirst.frc.team3316.robot.commands.transport.TransportStopRoll;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class TriggerShootingSequence extends DBugCommandGroup
{
	public TriggerShootingSequence()
	{
		addParallel(new TransportRollIn());
		addSequential(new WaitCommand(0.25));
		addParallel(new IntakeRollOut());
		addSequential(new WaitCommand(0.5));
		addParallel(new TransportStopRoll());
		addParallel(new IntakeStopRoll());
	}

	protected void init()
	{
		/*
		 * if (!(Robot.flywheel.isOnTarget() && Robot.hood.isOnTarget() && Robot.turret.isOnTarget() &&
		 * Robot.intake.isReadyToTransfer())) { this.cancel(); }
		 */

		if (!(Robot.flywheel.isOnTarget() && Robot.intake.isReadyToTransfer()))
		{
			this.cancel();
		}
	}
}
