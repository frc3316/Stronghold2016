package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RollIn extends DBugCommand
{
	private double rollInSpeed;

	public RollIn()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		rollInSpeed = (double) Robot.config.get("INATKE_ROLL_IN_SPEED");
	}

	protected void execute()
	{
		Robot.intake.setMotor(rollInSpeed);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}

}
