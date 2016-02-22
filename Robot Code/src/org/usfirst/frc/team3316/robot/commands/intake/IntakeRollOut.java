package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class IntakeRollOut extends DBugCommand
{
	private double speed;

	public IntakeRollOut()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		speed = (double) config.get("intake_RollOut_Speed");
	}

	protected void execute()
	{
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
