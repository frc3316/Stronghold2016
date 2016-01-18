package org.usfirst.frc.team3316.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;

public class Chassis extends DBugSubsystem
{
	
	// Actuators
	private Talon leftMotor, rightMotor;
	
	// Sensors
	private Encoder leftEncoder, rightEncoder;
	private AHRS navX; // For the navX
	
	// Variables
	public boolean isOnDefense = false; // For the navX
	private int counter = 0; // For the navX
	
	// Other
	private MovingAverage movingAvg; // For the navX
	private TimerTask navXTasker; // For the navX

	public Chassis()
	{
		// Actuators
		leftMotor = Robot.actuators.leftChassisTalon;
		rightMotor = Robot.actuators.rightChassisTalon;

		// Sensors
		leftEncoder = Robot.sensors.leftChassisEncoder;
		rightEncoder = Robot.sensors.rightChassisEncoder;
		navX = Robot.sensors.navX;
		
		// Create moving average
		try
		{
			movingAvg = new MovingAverage(
					(int) config.get("ANGLE_MOVING_AVG_SIZE"), 20, () ->
					{
						return navX.getPitch();
					});
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}

	public void initDefaultCommand()
	{}

	/*
	 * SET Methods
	 */
	public void setMotors(double v)
	{
		rightMotor.set(v);
		leftMotor.set(-v);
	}

	/*
	 * GET Methods
	 */
	public double getDistance()
	{
		try
		{
			double leftDist = leftEncoder.getRaw()
					* (double) Robot.config.get("CHASSIS_WHEEL_DIAMETER") * Math.PI;
			double rightDist = rightEncoder.getRaw()
					* (double) Robot.config.get("CHASSIS_WHEEL_DIAMETER") * Math.PI;

			return (leftDist + rightDist) / 2;

		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}

		return 0;
	}

	//Timer
	public void timerInit ()
	{
		navXTasker = new navX();
		Robot.timer.schedule(navXTasker, 0, 20);
	}
	
	// navX Class
	private class navX extends TimerTask {
		
		public void run()
		{
			try {
				if (Math.abs(movingAvg.get()) <= (double) Robot.config.get("DEFENSE_ANGLE_RANGE")) {
					counter++;
				} else {
					counter = 0;
					isOnDefense = true;
				}
			} catch (ConfigException e) {
				logger.severe(e);
			}
			
			try {
				if (counter >= (int)Math.round((double)Robot.config.get("DEFENSE_ANGLE_TIMEOUT") / 20)) // isTimedOut
				{
					isOnDefense = false;
					counter = 0;
				}
			} catch (ConfigException e) {
				logger.severe(e);
			}
		}
		
	}

}
