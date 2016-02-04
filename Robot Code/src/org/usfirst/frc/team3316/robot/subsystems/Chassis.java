package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.chassis.TankDrive;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Chassis extends DBugSubsystemCC
{

	// Actuators
	private DBugSpeedController leftMotor1, rightMotor2, leftMotor2,
			rightMotor1;

	private DoubleSolenoid longPistons, shortPistonsLeft, shortPistonsRight;

	// Sensors
	private AHRS navx; // For the navX

	private DigitalInput heLeftFront, heLeftBack, heRightFront, heRightBack;

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

		addSpeedController(leftMotor1);
		addSpeedController(leftMotor2);
		addSpeedController(rightMotor1);
		addSpeedController(rightMotor2);

		longPistons = Robot.actuators.longPistons;
		shortPistonsLeft = Robot.actuators.shortPistonsLeft;
		shortPistonsRight = Robot.actuators.shortPistonsRight;

		heLeftFront = Robot.sensors.chassisHELeftFront;
		heLeftBack = Robot.sensors.chassisHELeftBack;
		heRightFront = Robot.sensors.chassisHERightFront;
		heRightBack = Robot.sensors.chassisHERightBack;

		// Sensors
		navx = Robot.sensors.navx;

		// Create moving average
		movingAvg = new MovingAverage(
				(int) config.get("CHASSIS_ANGLE_MOVING_AVG_SIZE"), 20, () ->
				{
					return getPitch();
				});

		// Timer init
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
	public void setMotors(double left, double right)
	{
		leftMotor1.setMotor(left);
		leftMotor2.setMotor(left);

		rightMotor1.setMotor(right);
		rightMotor2.setMotor(right);
	}

	public boolean openLongPistons()
	{
		if (shortPistonsRight.get().equals(Value.kForward)
				|| shortPistonsLeft.get().equals(Value.kForward))
		{
			logger.severe("Tried to open long pistons when short pistons are open. Aborting.");
			return false;
		}
		else
		{
			longPistons.set(Value.kForward);
			return true;
		}
	}
	
	public boolean closeLongPistons()
	{
		longPistons.set(Value.kReverse);
		return true;
	}
	
	public boolean openShortPistons ()
	{
		if (longPistons.get().equals(Value.kReverse))
		{
			logger.severe("Tried to open short pistons when long pistons are closed. Aborting.");
			return false;
		}
		else
		{
			shortPistonsLeft.set(Value.kForward);
			shortPistonsRight.set(Value.kForward);
			return true;
		}
	}
	
	public boolean closeShortPistons()
	{
		shortPistonsLeft.set(Value.kReverse);
		shortPistonsRight.set(Value.kReverse);
		return true;
	}
	
	public boolean getHELeftFront ()
	{
		return heLeftFront.get();
	}
	
	public boolean getHELeftBack ()
	{
		return heLeftBack.get();
	}
	
	public boolean getHERightFront ()
	{
		return heRightFront.get();
	}
	
	public boolean getHERightBack ()
	{
		return heRightBack.get();
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
			if (Math.abs(movingAvg.get()) <= (double) Robot.config
					.get("CHASSIS_DEFENSE_ANGLE_RANGE"))
			{
				counter++;
			}
			else
			{
				counter = 0;
			}

			if (counter >= (int) Math.round((double) ((double) config
					.get("CHASSIS_DEFENSE_ANGLE_TIMEOUT") / 20.0)))
			{ // isTimedOut
				counter = 0;
				isOnDefense = false;
			}
			else if (counter == 0)
			{
				isOnDefense = true;
			}
		}
	}

	public double getPitch()
	{
		return navx.getRoll();
	}
}
