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

import java.util.ArrayList;

public class HoodPID extends DBugCommand
{
	private static PIDController pid;

	private double [] distances;
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
		distances = new double [0];

		for (int i = 0; i < distances.length; i++)
		{
			distances[i] = 0;
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

				double distance = (double) AlignShooter.getHoodAngle();
				distances[index] = distance;
				index++;
				index %= distances.length;


				double setPoint = getCorrectDistance(distances, 17.5);

				pid.setSetpoint(setPoint);
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

		logger.finest("Hood PID error: " + pid.getError());
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
	 * calculates the most accurate distance from a given ArrayList of distances using the thresh thresh
	 * @param distances the distances to use for the calculations
	 * @param thresh the thresh to check: if delta distance and avg of distances is less than thresh
     * @return double, the most accurate distance
     */
	protected double getCorrectDistance(double [] distances, double thresh) {
		// a good thresh will be around 15 - 20.
		double distancesSum = 0.0;
		for ( double d : distances) { distancesSum += d; }
		double distancesAvg = distancesSum/distances.length;

		ArrayList<Double> elementsPassed = new ArrayList<>(); // the elements that passed the delta avg distance < thresh.

		for (int i = 0; i < distances.length; i ++)
		{
			if (Math.pow(distancesAvg - distances[i], 2) < thresh) {
				elementsPassed.add(distances[i]);
			}
		}

		if (elementsPassed.size() > 0)
		{
			double newDistancesSum = 0.0;
			for ( double d : elementsPassed) { newDistancesSum += d; }
			return newDistancesSum/elementsPassed.size();
		}
		else {
			return distances[distances.length - 1];
		}

	}

}
