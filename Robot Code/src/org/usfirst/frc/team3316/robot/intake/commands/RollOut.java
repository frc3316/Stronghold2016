package org.usfirst.frc.team3316.robot.intake.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RollOut extends DBugCommand
{
	private double rollOutSpeed;
	
	public RollOut() {
		requires(Robot.intake);
	}
	
	protected void init()
	{
		try
		{
			rollOutSpeed = (double) Robot.config.get("INATKE_ROLL_OUT_SPEED");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}

	protected void execute()
	{
		Robot.intake.setMotor(rollOutSpeed);
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
