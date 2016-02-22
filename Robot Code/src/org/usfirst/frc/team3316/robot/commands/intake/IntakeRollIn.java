package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class IntakeRollIn extends DBugCommand
{
	private double speed;
	
	public IntakeRollIn()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		speed = (double) config.get("intake_RollIn_Speed");
	}

	protected void execute()
	{
		Robot.intake.setMotors(speed);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
		Robot.intake.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
