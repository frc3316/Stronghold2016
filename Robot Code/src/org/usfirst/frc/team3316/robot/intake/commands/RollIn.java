package org.usfirst.frc.team3316.robot.intake.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollIn extends DBugCommand
{
	private double rollInSpeed;

	public RollIn()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		/*
		try
		{
			rollInSpeed = (double) Robot.config.get("INATKE_ROLL_IN_SPEED");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
		*/
		SmartDashboard.putNumber("Intake Voltage", 0.0);
	}

	protected void execute()
	{
		rollInSpeed = (double) SmartDashboard.getNumber("Intake Voltage", 0.0);
		Robot.intake.setMotor(rollInSpeed);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
