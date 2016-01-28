package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

public class Transport extends DBugSubsystem
{
	//TODO: Add commenting

	private DBugSpeedController transportMotor1, transportMotor2;

	public Transport()
	{
		transportMotor1 = Robot.actuators.transportMotor1;
		transportMotor2 = Robot.actuators.transportMotor2;

		addSpeedController(transportMotor1);
		addSpeedController(transportMotor2);
	}

	public void initDefaultCommand()
	{
	}

	public boolean setMotors(double v)
	{
		return setMotors(v);
	}

}
