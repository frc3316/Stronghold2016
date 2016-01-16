package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class Flywheel extends DBugSubsystem
{
	Talon talon;
	Counter counter;
	
	public Flywheel ()
	{
		talon = Robot.actuators.flywheel;
		counter = Robot.sensors.flywheelCounter;
	}
	
	public boolean setMotors (double v)
	{
		talon.set(v);
		
		return true;
	}
	
	public double getRate ()
	{
		return counter.getRate();
	}
	
	public void initDefaultCommand() {}
}
