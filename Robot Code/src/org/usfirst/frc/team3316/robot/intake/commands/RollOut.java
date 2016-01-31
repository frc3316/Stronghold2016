package org.usfirst.frc.team3316.robot.intake.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RollOut extends DBugCommand
{
	private double speed;

	public RollOut()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
	}

	protected void execute()
	{
		speed = (double) Robot.config.get("intake_RollOut_Speed");

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
