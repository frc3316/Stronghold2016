package org.usfirst.frc.team3316.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.TankDrive;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends DBugSubsystem
{
	// Actuators
	private SpeedController leftMotor1, rightMotor2, leftMotor2, rightMotor1;

	// Sensors
	private AHRS navx; // For the navX

	// Variables
	public boolean isOnDefense = false; // For the navX
	private int counter = 0; // For the navX

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
					(int) config.get("ANGLE_MOVING_AVG_SIZE"), 20, () ->
					{
						return getPitch();
					});
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
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

	// Timer
	public void timerInit()
	{
		navXTasker = new navX();
		Robot.timer.schedule(navXTasker, 0, 20);
	}

	// navX Class
	private class navX extends TimerTask
	{

		public void run()
		{
			try
			{
				if (Math.abs(movingAvg.get()) <= (double) Robot.config
						.get("DEFENSE_ANGLE_RANGE"))
				{
					counter++;
				}
				else
				{
					counter = 0;
					isOnDefense = true;
				}
			}
			catch (ConfigException e)
			{
				logger.severe(e);
			}
			
			if (counter > 25) {
				counter = 0;
				isOnDefense = false;
			}
		}
	}

	public double getPitch()
	{
		return navx.getRoll();
	}
}
