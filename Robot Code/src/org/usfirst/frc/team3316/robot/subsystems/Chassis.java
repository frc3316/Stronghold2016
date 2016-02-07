package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.chassis.TankDrive;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;

public class Chassis extends DBugSubsystem
{
	// navX Class
	private class navX extends TimerTask
	{
		private int counterPitch = 0;
		private int counterRoll = 0;

		public void run()
		{
			if (Math.abs(movingAvgPitch.get()) <= (double) Robot.config
					.get("chassis_Defense_Pitch_Thresh"))
			{
				counterPitch++;
			}
			else
			{
				counterPitch = 0;
			}

			if (counterPitch >= (int) Math.round((double) ((double) config
					.get("chassis_Defense_Angle_Timeout") / 20.0)))
			{ // isTimedOut
				counterPitch = 0;
				isOnDefense = false;
			}
			else if (counterPitch == 0)
			{
				isOnDefense = true;
			}

			if (Math.abs(movingAvgRoll.get()) <= (double) Robot.config
					.get("chassis_Defense_Roll_Thresh"))
			{
				counterRoll++;
			}
			else
			{
				counterRoll = 0;
			}

			if (counterRoll >= (int) Math.round((double) ((double) config
					.get("chassis_Defense_Angle_Timeout") / 20.0)))
			{ // isTimedOut
				counterRoll = 0;
				isOnDefense = false;
			}
			else if (counterRoll == 0)
			{
				isOnDefense = true;
			}

			SmartDashboard.putNumber("Robot Pitch Angle",
					Math.abs(movingAvgPitch.get()));
		}
	}
	
	// Actuators
	private DBugSpeedController leftMotor1, rightMotor2, leftMotor2,
			rightMotor1;

	private DoubleSolenoid longPistons, shortPistonsLeft, shortPistonsRight;

	// Sensors
	private AHRS navx; // For the navX
	private Encoder leftEncoder;
	private Encoder rightEncoder;

	private DigitalInput heLeftFront, heLeftBack, heRightFront, heRightBack;

	// Variables
	private boolean isOnDefense = false; // For the navX

	private static Timer timer;

	static
	{
		timer = new Timer();
	}

	// Other
	private MovingAverage movingAvgPitch; // For the navX
	private MovingAverage movingAvgRoll; // For the navX
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
		leftEncoder = Robot.sensors.chassisLeftEncoder;
		rightEncoder = Robot.sensors.chassisRightEncoder;

		heLeftFront = Robot.sensors.chassisHELeftFront;
		heLeftBack = Robot.sensors.chassisHELeftBack;
		heRightFront = Robot.sensors.chassisHERightFront;
		heRightBack = Robot.sensors.chassisHERightBack;

		// Create moving average
		movingAvgPitch = new MovingAverage(
				(int) config.get("CHASSIS_ANGLE_MOVING_AVG_SIZE"), 20, () ->
				{
					return getPitch();
				});
		movingAvgRoll = new MovingAverage(
				(int) config.get("CHASSIS_ANGLE_MOVING_AVG_SIZE"), 20, () ->
				{
					return getRoll();
				});

		navXTasker = new navX();
		timer.schedule(navXTasker, 0, 20);
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new TankDrive());
	}

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
			logger.severe(
					"Tried to open long pistons when short pistons are open. Aborting.");
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

	public boolean openShortPistonsLeft()
	{
		if (longPistons.get().equals(Value.kReverse))
		{
			logger.severe(
					"Tried to open short pistons when long pistons are closed. Aborting.");
			return false;
		}
		else
		{
			shortPistonsLeft.set(Value.kForward);
			return true;
		}
	}

	public boolean openShortPistonsRight()
	{
		if (longPistons.get().equals(Value.kReverse))
		{
			logger.severe(
					"Tried to open short pistons when long pistons are closed. Aborting.");
			return false;
		}
		else
		{
			shortPistonsRight.set(Value.kForward);
			return true;
		}
	}

	public boolean closeShortPistonsLeft()
	{
		shortPistonsLeft.set(Value.kReverse);
		return true;
	}

	public boolean closeShortPistonsRight()
	{
		shortPistonsRight.set(Value.kReverse);
		return true;
	}

	/**
	 * Closes all of the chassis pistons.
	 * 
	 * @return Whether all of the closing methods have succeeded.
	 */
	public boolean closeAllPistons()
	{
		return closeLongPistons() && closeShortPistonsLeft()
				&& closeShortPistonsRight();
	}

	/**
	 * Returns whether the long pistons are extended.
	 */
	public boolean areLongPistonsExtended()
	{
		return longPistons.get().equals(Value.kForward);
	}

	/**
	 * Returns whether all of the short pistons are extended.
	 */
	public boolean areShortPistonsExtended()
	{
		return shortPistonsLeft.get().equals(Value.kForward)
				&& shortPistonsRight.get().equals(Value.kForward);
	}

	public boolean getHELeftFront()
	{
		return heLeftFront.get();
	}

	public boolean getHELeftBack()
	{
		return heLeftBack.get();
	}

	public boolean getHERightFront()
	{
		return heRightFront.get();
	}

	public boolean getHERightBack()
	{
		return heRightBack.get();
	}

	public boolean isOnDefense()
	{
		return isOnDefense;
	}



	public double getPitch()
	{
		return navx.getRoll();
	}

	public double getRoll()
	{
		return navx.getPitch();
	}

	public double getYaw()
	{
		return fixYaw(navx.getYaw());
	}

	// Returns the same heading in the range (-180) to (180)
	private static double fixYaw(double heading)
	{
		double toReturn = heading % 360;

		if (toReturn < -180)
		{
			toReturn += 360;
		}
		else if (toReturn > 180)
		{
			toReturn -= 360;
		}
		return toReturn;
	}

	public double getLeftSpeed()
	{
		return leftEncoder.getRate(); // Returns the speed in meter per
										// second units.
	}

	public double getRightSpeed()
	{
		return rightEncoder.getRate(); // Returns the speed in meter per
										// second units.
	}

	public double getDistance()
	{
		return (rightEncoder.getDistance() + leftEncoder.getDistance()) / 2;
	}

	public void resetEncoders()
	{
		rightEncoder.reset();
		leftEncoder.reset();
	}

	public void resetNavX()
	{
		navx.reset();
		navx.resetDisplacement();
	}
}
