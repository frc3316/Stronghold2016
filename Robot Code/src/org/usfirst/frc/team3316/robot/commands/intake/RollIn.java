package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollIn extends DBugCommand
{
	private double speed;
	
	public RollIn()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
	}

	protected void execute()
	{
		speed = (double) config.get("intake_RollIn_Speed");
		isFin = !Robot.intake.setMotors(speed);
	}

	protected boolean isFinished()
	{
		return isFin;
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
