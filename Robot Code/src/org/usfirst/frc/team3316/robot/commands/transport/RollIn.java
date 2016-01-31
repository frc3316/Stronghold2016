package org.usfirst.frc.team3316.robot.commands.transport;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class RollIn extends DBugCommand
{
	//TODO: Add commenting

	private double rollInSpeed;

	public RollIn()
	{
		requires(Robot.transport);
	}

	protected void init()
	{
		rollInSpeed = (double) Robot.config.get("TRANSPORT_ROLL_IN_SPEED");
	}

	protected void execute()
	{
		isFin = !Robot.transport.setMotors(rollInSpeed);
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
