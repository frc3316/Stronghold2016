package org.usfirst.frc.team3316.robot.commands.transport;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Sets the transport to roll at the % voltage specified in the config
 * 
 * @author D-Bug
 *
 */
public class TransportRollOut extends DBugCommand
{
	private double rollOutSpeed;

	public TransportRollOut()
	{
		requires(Robot.transport);
	}

	protected void init()
	{
		rollOutSpeed = (double) Robot.config.get("transport_RollOut_Speed");
	}

	protected void execute()
	{
		isFin = !Robot.transport.setMotors(rollOutSpeed);
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
		fin();
	}

}
