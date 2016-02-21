package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.Counter;

public class Transport extends DBugSubsystemCC
{
	private DBugSpeedController motor;
	
	private Counter counter;
	
	public Transport()
	{
		// Actuators
		Robot.actuators.TransportActuators();
		
		motor = Robot.actuators.transportMotor;
		addSpeedController(motor);
	}

	public void initDefaultCommand()
	{
	}
	
	public double getRate ()
	{
		return counter.getRate();
	}
}
