package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollIn extends DBugCommand
{
	private double rollInSpeed;
	private boolean isFin = false;
	private int counter;

	public RollIn()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		SmartDashboard.putNumber("Intake Motor", 0.0);
		counter = 0;
	}

	protected void execute()
	{
		rollInSpeed = SmartDashboard.getNumber("Intake Motor");
		isFin = !Robot.intake.setMotor(rollInSpeed);
		if (isFin)
		{
			counter++;
		}
	}

	protected boolean isFinished()
	{
		if (counter > 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}

}
