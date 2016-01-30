package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollOut extends DBugCommand
{
	private double rollOutSpeed;

	public RollOut()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		SmartDashboard.putNumber("Intake Motor", 0.0);
	}

	protected void execute()
	{
		rollOutSpeed = SmartDashboard.getNumber("Intake Motor");
		Robot.intake.setMotor(rollOutSpeed);
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
