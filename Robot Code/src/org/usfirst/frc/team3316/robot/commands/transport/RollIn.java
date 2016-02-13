package org.usfirst.frc.team3316.robot.commands.transport;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Sets the transport to roll at the % voltage specified in the config
 * 
 * @author D-Bug
 *
 */
public class RollIn extends DBugCommand
{
	private double rollInSpeed;

	public RollIn()
	{
		requires(Robot.transport);
	}

	protected void init()
	{
		rollInSpeed = (double) Robot.config.get("transport_RollIn_Speed");
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
		Robot.transport.setMotors(0);
	}

	protected void interr()
	{
	}

}
