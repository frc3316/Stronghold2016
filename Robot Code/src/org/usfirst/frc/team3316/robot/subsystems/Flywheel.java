package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Talon;

public class Flywheel extends DBugSubsystem
{
	private Talon talon;
	private Counter counter;
	double rate = 0;
	private static Timer timer;
	static {
		timer = new Timer();
	}
	
	private class RateTask extends TimerTask
	{
		public void run()
		{
			double delta = counter.getRate() - rate;
			
			if (delta < 10)
			{
				rate = counter.getRate();
			}
		}
	}
	
	public Flywheel ()
	{
		talon = Robot.actuators.flywheelMotor;
		counter = Robot.sensors.flywheelCounter;
		
		timer.schedule(new RateTask(), 0, 10);
	}
	
	public boolean setMotors (double v)
	{
		talon.set(v);
		
		return true;
	}
	
	public double getRate ()
	{
		return rate;
	}
	
	public void initDefaultCommand() {}
}
