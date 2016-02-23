package org.usfirst.frc.team3316.robot.commands.transport;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class TransportJoysticks extends DBugCommand
{

	public TransportJoysticks() {
		requires(Robot.transport);
	}
	protected void init()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute()
	{
		Robot.actuators.transportMotor.setMotor(Robot.joysticks.joystickOperator.getY() * -1);
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void fin()
	{
		Robot.actuators.transportMotor.setMotor(0);
	}

	@Override
	protected void interr()
	{
		fin();
	}

}
