package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.flywheel.FlywheelPID;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;
import org.usfirst.frc.team3316.robot.utils.LowPassFilter;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel extends DBugSubsystemCC
{
	private class updateSetpoint extends TimerTask {
		public void run()
		{
			setpoint = (double) config.get("flywheel_PID_Setpoint");
			tolerance = (double) config.get("flywheel_PID_Tolerance");
		}	
	}
	
	private DBugSpeedController flywheelMotor;
	private Counter counter;
	private double powerSum = 0, setpoint, tolerance;
	private static Timer timer;
	static {
		timer = new Timer();
	}

	private LowPassFilter counterFilter;

	public Flywheel()
	{
		// Actuators
		Robot.actuators.FlywheelActuators();

		flywheelMotor = Robot.actuators.flywheelMotor;

		addSpeedController(flywheelMotor);

		// Sensors
		Robot.sensors.FlywheelSensors();

		counter = Robot.sensors.flywheelCounter;

		counterFilter = new LowPassFilter(
				(double) config.get("flywheel_CounterFilter_MaxChange"),
				(long) config.get("flywheel_CounterFilter_Period"), () ->
				{
					return counter.getRate() > 200 ? Double.MAX_VALUE
							: counter.getRate();
				});
		
		timer.schedule(new updateSetpoint(), 0, 20);
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
	
	public double getSetPoint() {
		return setpoint;
	}
	
	public double getTolerance() {
		return tolerance;
	}
	
	public boolean isOnTarget() {
		if (Math.abs(getRate() - setpoint) < tolerance)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
