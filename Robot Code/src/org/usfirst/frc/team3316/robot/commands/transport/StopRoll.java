package org.usfirst.frc.team3316.robot.commands.transport;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class StopRoll extends DBugCommand
{

	public StopRoll()
	{
		requires(Robot.transport);
	}
	
	protected void init()
	{}

	protected void execute()
	{
		Robot.transport.setMotor(0);
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
