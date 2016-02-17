package org.usfirst.frc.team3316.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.Counter;

public class Flywheel extends DBugSubsystemCC
{
	private DBugSpeedController flywheelMotor;
	private Counter counter;
	private double rate = 0;

	/**
	 * Class for filtering out noise from the FPGA rate calculation. Essentially, this is a low pass filter.
	 * 
	 * @author D-Bug
	 *
	 */
	private class RateTask extends TimerTask
	{
		public void run()
		{
			double delta = counter.getRate() - rate;

			if (delta < 10) // random constant, couldv'e been anything (when
							// there is noise the delta is in the scale of 10^5
							// - 10^6).
			{
				rate = counter.getRate();
			}
		}
	}

	public Flywheel()
	{
		// Actuators
		Robot.actuators.FlywheelActuators();
		
		flywheelMotor = Robot.actuators.flywheelMotor;

		addSpeedController(flywheelMotor);

		// Sensors
		Robot.sensors.FlywheelSensors();
		
		counter = Robot.sensors.flywheelCounter;

		// Other stuff
		Robot.timer.schedule(new RateTask(), 0, 10);
	}

	public void initDefaultCommand()
	{
	}

	/**
	 * Returns the turning rate of the flywheel.
	 * 
	 * @return The speed in rounds per second.
	 */
	public double getRate()
	{
		return rate;
	}
}
