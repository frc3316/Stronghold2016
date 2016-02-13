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
	private double setpoint, onVoltage, offVoltage;

	private double v = 0;

	public BangbangTransport()
	{
		requires(Robot.transport);
	}

	protected void init()
	{
		setpoint = (double) config.get("transport_Bangbang_Setpoint");
		onVoltage = (double) config.get("transport_Bangbang_OnVoltage");
		offVoltage = (double) config.get("transport_Bangbang_OffVoltage");
		
		v = 0;
	}

	protected void execute()
	{
		if (Robot.transport.getRate() < setpoint)
		{
			v = onVoltage;
		}
		else
		{
			v = offVoltage;
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
