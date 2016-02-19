package org.usfirst.frc.team3316.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;
import org.usfirst.frc.team3316.robot.utils.LowPassFilter;

import edu.wpi.first.wpilibj.Counter;

public class Flywheel extends DBugSubsystemCC
{
	private DBugSpeedController flywheelMotor;
	private Counter counter;
	private double powerSum = 0;
	
	private LowPassFilter counterFilter;

	/**
	 * Class for filtering out noise from the FPGA rate calculation. Essentially, this is a low pass filter.
	 * 
	 * This class doesn't do now anything important because we have the generic LowPassFilter.
	 * 
	 * This class can be deleted when we won't need the power calculation anymore.
	 * 
	 * @author D-Bug
	 *
	 */
	private class RateTask extends TimerTask
	{
		public void run()
		{
			// For power sum
			powerSum =+ Robot.actuators.flywheelMotor.getCurrent()
					* Robot.actuators.flywheelMotor.getVoltage() * Robot.sensors.pdp.getVoltage();
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

		counterFilter = new LowPassFilter((double) config.get("flywheel_CounterFilter_MaxChange"),
				(long) config.get("flywheel_CounterFilter_Period"), () ->
				{
					return counter.getRate();
				});
		
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
		return counterFilter.get();
	}

	public double getPowerSum()
	{
		return powerSum;
	}
}
