package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollOut extends DBugCommand
{
	//TODO: Add commenting

	private double rollOutSpeed;

	public RollOut()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		rollOutSpeed = (double) config.get("INTAKE_ROLL_OUT_SPEED");
	}

	protected void execute()
	{
		rollOutSpeed = SmartDashboard.getNumber("Intake Motor");
		isFin = !Robot.intake.setMotor(rollOutSpeed);
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}

}
