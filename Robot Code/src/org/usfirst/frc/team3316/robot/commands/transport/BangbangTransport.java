package org.usfirst.frc.team3316.robot.commands.transport;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Bangbang control to roll the transport at the specified speed in the config
 * 
 * @author D-Bug
 *
 */
public class BangbangTransport extends DBugCommand
{
	private double v = 0;

	public BangbangTransport()
	{
		requires(Robot.transport);
	}

	protected void init()
	{
		v = 0;
	}

	protected void execute()
	{
		double speedSetpoint = (double) config
				.get("transport_Bangbang_Setpoint");

		if (Robot.transport.getRate() < speedSetpoint)
		{
			v = (double) config.get("transport_Bangbang_OnVoltage");
		}
		else
		{
			v = (double) config.get("transport_Bangbang_OffVoltage");
		}

		isFin = !Robot.transport.setMotors(v);
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
