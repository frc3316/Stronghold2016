package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.TankDrive;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;

public class Chassis extends DBugSubsystem
{
	// Actuators
	private SpeedController leftMotor1, rightMotor2, leftMotor2, rightMotor1;

	// Sensors
	private AHRS navx; // For the navX

	// Variables
	private boolean isOnDefense = false; // For the navX
	
	private static Timer timer;
	
	static
	{
		timer = new Timer();		
	}

	// Other
	private MovingAverage movingAvg; // For the navX
	private TimerTask navXTasker; // For the navX

	public Chassis()
	{
		// Actuators
		leftMotor1 = Robot.actuators.leftChassis1;
		rightMotor2 = Robot.actuators.rightChassis2;
		leftMotor2 = Robot.actuators.leftChassis2;
		rightMotor1 = Robot.actuators.rightChassis1;

		// Sensors
		navx = Robot.sensors.navx;

		// Create moving average
		try
		{
			movingAvg = new MovingAverage(
					(int) config.get("CHASSIS_ANGLE_MOVING_AVG_SIZE"), 20, () ->
					{
						return getPitch();
					});
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
		
		//Timer init
		navXTasker = new navX();
		timer.schedule(navXTasker, 0, 20);
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new TankDrive());
	}

	/*
	 * Set Method
	 */
	public void set(double left, double right)
	{
		leftMotor1.set(left);
		leftMotor2.set(left);

		rightMotor1.set(-right);
		rightMotor2.set(-right);
	}

	/*
	 * GET Methods
	 */
	public boolean isOnDefense()
	{
		return isOnDefense;
	}

	// navX Class
	private class navX extends TimerTask
	{
		private int counter = 0; // For the navX

		public void run()
		{
			try
			{
				if (Math.abs(movingAvg.get()) <= (double) Robot.config
						.get("CHASSIS_DEFENSE_ANGLE_RANGE"))
				{
					counter++;
				}
				else
				{
					counter = 0;
				}

				if (counter >= (int) Math.round(
						(double) ((double) config.get("CHASSIS_DEFENSE_ANGLE_TIMEOUT") / 20.0)))
				{ // isTimedOut
					counter = 0;
					isOnDefense = false;
				}
				else if (counter == 0)
				{
					isOnDefense = true;
				}
			}
			catch (ConfigException e)
			{
				logger.severe(e);
			}
		}
	}

	public double getPitch()
	{
		return navx.getRoll();
	}
}