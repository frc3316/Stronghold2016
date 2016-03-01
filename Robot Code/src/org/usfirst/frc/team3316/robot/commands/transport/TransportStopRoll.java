package org.usfirst.frc.team3316.robot.commands.transport;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class TransportStopRoll extends DBugCommand
{
	//TODO: Add commenting

	public TransportStopRoll()
	{
		requires(Robot.transport);
	}
	
	protected void init()
	{}

	protected void execute()
	{
		Robot.transport.setMotors(0.0);
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
