package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollIn extends DBugCommand
{	
	//TODO: Add commenting

	private double rollInSpeed;

	public RollIn()
	{
		requires(Robot.intake);
	}

	protected void init()
	{
		rollInSpeed = (double) config.get("INTAKE_ROLL_IN_SPEED");
	}

	protected void execute()
	{
		rollInSpeed = SmartDashboard.getNumber("Intake Motor");
		isFin = !Robot.intake.setMotor(rollInSpeed);
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
