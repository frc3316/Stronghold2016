package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class Flywheel extends DBugSubsystem
{
	private Talon talon;
	private Counter counter;

	double rate = 0;
	private static Timer timer;

	static
	{
		timer = new Timer();
	}

	private double talonCurrent;

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

	public Flywheel()
	{
		talon = Robot.actuators.flywheel;
		counter = Robot.sensors.flywheelCounter;

		timer.schedule(new RateTask(), 0, 10);
	}

	public boolean setMotors(double v)
	{
		talonCurrent = Robot.sensors.pdp.getCurrent(2);

			if (talonCurrent >= (double) Robot.config
					.get("FLYWHEEL_MOTOR_HIGH_THRESH"))
			{
				talon.set(0);
				return false;
			}
			
		talon.set(v);
		return true;
	}

	public double getRate()
	{
		return rate;
	}

	public void initDefaultCommand()
	{
	}
}
