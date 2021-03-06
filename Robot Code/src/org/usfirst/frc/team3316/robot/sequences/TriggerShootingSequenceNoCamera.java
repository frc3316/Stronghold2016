package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;
import org.usfirst.frc.team3316.robot.commands.hood.HoodJoysticks;
import org.usfirst.frc.team3316.robot.commands.hood.StopHood;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollOut;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeStopRoll;
import org.usfirst.frc.team3316.robot.commands.transport.TransportRollIn;
import org.usfirst.frc.team3316.robot.commands.transport.TransportStopRoll;
import org.usfirst.frc.team3316.robot.commands.turret.StopTurret;
import org.usfirst.frc.team3316.robot.commands.turret.TurretJoysticks;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class TriggerShootingSequenceNoCamera extends DBugCommandGroup
{
	public TriggerShootingSequenceNoCamera()
	{
		addParallel(new TransportRollIn());
		addSequential(new WaitCommand((double) config.get("triggetShooting_IntakeRollIn_Timeout")));
		addParallel(new IntakeRollOut());
		addSequential(new WaitCommand((double) config.get("triggetShooting_IntakeRollOut_Timeout")));
		addParallel(new TransportStopRoll());
		addParallel(new IntakeStopRoll());
	}
}
