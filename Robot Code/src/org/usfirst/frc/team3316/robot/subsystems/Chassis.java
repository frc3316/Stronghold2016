package org.usfirst.frc.team3316.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.chassis.TankDrive;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;

public class Chassis extends DBugSubsystem
{
	// navX Class
	private class navX extends TimerTask
	{
		private int counter = 0;

		public void run()
		{
			if (movingAvgPitch.get() <= (double) Robot.config.get("chassis_Defense_Pitch_Thresh")
					&& movingAvgRoll.get() <= (double) Robot.config.get("chassis_Defense_Roll_Thresh"))
			{
				counter++;
			}
			else
			{
				counter = 0;
			}

			if (counter >= (int) Math
					.round((double) ((double) config.get("chassis_Defense_Angle_Timeout") / 20.0)))
			{ // isTimedOut
				counter = 0;
				isOnDefense = false;
			}
			else if (counter == 0)
			{
				isOnDefense = true;
			}
			
			SmartDashboard.putNumber("MovingAvg Pitch", movingAvgPitch.get());
			SmartDashboard.putNumber("MovingAvg Roll", movingAvgRoll.get());
		}
	}

	// Actuators
	private DBugSpeedController leftMotor1, rightMotor2, leftMotor2, rightMotor1;

	private DoubleSolenoid longPistons, shortPistonsLeft, shortPistonsRight;

	// Sensors
	private AHRS navx; // For the navX
	private Encoder leftEncoder;
	private Encoder rightEncoder;

	// Variables
	private boolean isOnDefense = false; // For the navX
	private double pitchOffset, rollOffset;

	// Other
	private MovingAverage movingAvgPitch; // For the navX
	private MovingAverage movingAvgRoll; // For the navX
	private TimerTask navXTasker; // For the navX
	private TimerTask HETasker;
	
	public Chassis()
	{
		// Actuators
		Robot.actuators.ChassisActuators();
		
		leftMotor1 = Robot.actuators.chassisLeft1;
		rightMotor2 = Robot.actuators.chassisRight2;
		leftMotor2 = Robot.actuators.chassisLeft2;
		rightMotor1 = Robot.actuators.chassisRight1;

		longPistons = Robot.actuators.chassisLongPistons;
		shortPistonsLeft = Robot.actuators.chassisShortPistonsLeft;
		shortPistonsRight = Robot.actuators.chassisShortPistonsRight;

		// Sensors
		Robot.sensors.ChassisSensors();
		
		navx = Robot.sensors.navx;
		leftEncoder = Robot.sensors.chassisLeftEncoder;
		rightEncoder = Robot.sensors.chassisRightEncoder;

		// Create moving average
		movingAvgPitch = new MovingAverage((int) config.get("CHASSIS_ANGLE_MOVING_AVG_SIZE"), 20, () ->
		{
			return Math.abs(getPitch());
		});
		movingAvgRoll = new MovingAverage((int) config.get("CHASSIS_ANGLE_MOVING_AVG_SIZE"), 20, () ->
		{
			return Math.abs(getRoll());
		});

		navXTasker = new navX();
		Robot.timer.schedule(navXTasker, 0, 20);
		
		pitchOffset = 0;
		rollOffset = 0;
		
		resetPitch();
		resetRoll();
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new TankDrive());
	}

	/*
	 * Motor methods
	 */
	public void setMotors(double left, double right)
	{
		logger.finest("Setting chassis. left: " + left + ", right: " + right);
		
		leftMotor1.setMotor(left);
		leftMotor2.setMotor(left);

		rightMotor1.setMotor(right);
		rightMotor2.setMotor(right);
	}

	/*
	 * Piston methods
	 */
	public boolean openLongPistons()
	{
		if (areShortPistonsLeftExtended() || areShortPistonsRightExtended())
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

	public boolean openShortPistonsLeft()
	{
		if (!areLongPistonsExtended())
		{
			logger.severe("Tried to open short pistons when long pistons are closed. Aborting.");
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
		if (!areLongPistonsExtended())
		{
			logger.severe("Tried to open short pistons when long pistons are closed. Aborting.");
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
		return closeLongPistons() && closeShortPistonsLeft() && closeShortPistonsRight();
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

	/**
	 * Returns whether the left short pistons are extended
	 */
	public boolean areShortPistonsLeftExtended()
	{
		return shortPistonsLeft.get().equals(Value.kForward);
	}

	/**
	 * Returns whether the right short pistons are extended
	 */
	public boolean areShortPistonsRightExtended()
	{
		return shortPistonsRight.get().equals(Value.kForward);
	}

	/*
	 * Methods related to NavX angle
	 */
	public boolean isOnDefense()
	{
		return isOnDefense;
	}

	public double getPitch()
	{
		return navx.getRoll() + pitchOffset;
	}

	public double getRoll()
	{
		return navx.getPitch() + rollOffset;
	}
	
	public void resetPitch() 
	{
		pitchOffset = pitchOffset - getPitch();
		SmartDashboard.putNumber("Pitch offset", pitchOffset);
	}
	
	public void resetRoll() 
	{
		rollOffset = rollOffset - getRoll();
		SmartDashboard.putNumber("Roll offset", rollOffset);
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

	/*
	 * Encoder Methods
	 */
	public double getLeftDistance()
	{
		return leftEncoder.getDistance();
	}

	public double getRightDistance()
	{
		return rightEncoder.getDistance();
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
}
