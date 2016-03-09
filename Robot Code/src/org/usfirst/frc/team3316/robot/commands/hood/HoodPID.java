package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Utils;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.reflect.Array;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;

public class HoodPID extends DBugCommand
{
	private static PIDController pid;

	private double[] angles;
	private int index = 0;

	public HoodPID()
	{
		requires(Robot.hood);
		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
			}

			public double pidGet()
			{
				return Robot.hood.getAngle();
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				isFin = !Robot.hood.setMotors(output);
				config.add("hood_Angle_SetPoint", pid.getSetpoint());
			}
		});

		pid.setOutputRange(-1, 1);
	}

	protected void init()
	{
		angles = new double[20];

		for (int i = 0; i < angles.length; i++)
		{
			angles[i] = 0;
		}

		pid.setAbsoluteTolerance((double) config.get("hood_PID_Tolerance"));

		pid.setPID((double) config.get("hood_PID_KP") / 1000.0, (double) config.get("hood_PID_KI") / 1000.0,
				(double) config.get("hood_PID_KD") / 1000.0);

		pid.enable();
	}

	protected void execute()
	{
		try
		{
			if (AlignShooter.isObjectDetected())
			{
				// double setPoint = (double) config.get("hood_Angle_SetPoint");

				double currentAngle = (double) AlignShooter.getHoodAngle();
				SmartDashboard.putNumber("HOOD PID angle", currentAngle);

				angles[index] = currentAngle;
				index++;
				index %= angles.length;

				for (double a : angles)
				{
//					logger.finest("Angle: " + a);
				}
				double setPoint = getCorrectSetpoint(angles, 0.19); //we found out that this magic constant works
//				logger.finest("Hood angle setpoint: " + setPoint);

				SmartDashboard.putNumber("HOOD PID setpoint", setPoint);
				if (setPoint > 20)
				{
					pid.setSetpoint(setPoint);
				}
				else
				{
					pid.setSetpoint(50);
				}
			}
			else
			{
				isFin = !Robot.hood.setMotors(0);
			}
		}
		catch (Exception e)
		{
			logger.severe(e);
			isFin = !Robot.hood.setMotors(0);
		}

//		logger.finest("Hood PID error: " + pid.getError());
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		pid.reset();

		 Robot.hood.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

	/**
	 * calculates the most accurate distance from a given ArrayList of distances
	 * using the thresh thresh
	 * 
	 * @param angles
	 *            the distances to use for the calculations
	 * @param thresh
	 *            the thresh to check: if delta distance and avg of distances is
	 *            less than thresh
	 * @return double, the most accurate distance
	 */
	protected double getCorrectSetpoint(double[] setpoints, double thresh)
	{
		// a good thresh will be around 15 - 20.
		double sum = 0.0;
		for (double d : setpoints)
		{
//			logger.finest("Received setpoint " + d);
			sum += d;
		}
		double setpointsAvg = sum / setpoints.length;
		
		for (double setpoint : setpoints)
		{
			if (setpoint == 0)
			{
				return setpoints[index];
			}
		}

		return setpointsAvg;
		
//		logger.finest("Setpoints average " + setpointsAvg);

		
		/*
		ArrayList<Double> elementsPassed = new ArrayList<>(); // the elements
																// that passed
																// the delta avg
																// distance <
																// thresh.

		for (int i = 0; i < setpoints.length; i++)
		{
//			logger.finest(
//					"Deviation for setpoint " + setpoints[i] + "is " + Math.pow(setpointsAvg - setpoints[i], 2));
			if (Math.pow(setpointsAvg - setpoints[i], 2) < thresh)
			{
				elementsPassed.add(setpoints[i]);
			}
		}

//		logger.finest("Elements passed: " + elementsPassed);

		if (elementsPassed.size() > 0)
		{
			double newSum = 0.0;
			for (double d : elementsPassed)
			{
				newSum += d;
			}

//			logger.finest("Return successful: " + newSum / elementsPassed.size());

			return newSum / elementsPassed.size();
		}
		else
		{
//			logger.finest("Return failed: " + setpoints[setpoints.length - 1]);
			return setpoints[setpoints.length - 1];
		}
		*/

	}

}
