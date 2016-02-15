package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.Encoder;

public class Transport extends DBugSubsystemCC
{
	private DBugSpeedController motor;
	
	private Encoder encoder;
	
	public Transport()
	{
//		motor = Robot.actuators.transportMotor;
		addSpeedController(motor);
		
		encoder = Robot.sensors.transportEncoder;
	}

	public void initDefaultCommand()
	{
	}
	
	public double getRate ()
	{
		return encoder.getRate();
	}
}
