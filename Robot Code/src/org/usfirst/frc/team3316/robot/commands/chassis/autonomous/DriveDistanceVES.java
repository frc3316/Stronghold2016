package org.usfirst.frc.team3316.robot.commands.chassis.autonomous;

import org.usfirst.frc.team3316.robot.Robot;

/**
 * Drive Distance Velocity Error Setpoint. Drives a certain distance in a
 * straight line by setting a feed forward term according to the motion profile,
 * and a pid to fix the error compared to the motion profile.
 * 
 * @author D-Bug
 *
 */
public class DriveDistanceVES extends DriveDistance
{
	public DriveDistanceVES(double dist)
	{
		super(dist);
	}

	protected void set()
	{
		double profileVelocity = motion.getVelocity(currentTime);
		double profileAcceleration = motion.getAcceleration(currentTime);

		double currentVelocity = (Robot.chassis.getLeftSpeed()
				+ Robot.chassis.getRightSpeed()) / 2;

		double kV = (double) config.get("chassis_DriveDistance_KV"); // Conversion
																		// variable
																		// between
																		// speed
																		// and %
																		// volts
	
		double kA = (double) config.get("chassis_DriveDistance_KA"); // Conversion
																		// variable
																		// between
																		// speed
																		// and %
																		// volts

		double error = profileVelocity - currentVelocity;

		pidLeft.setSetpoint(error);
		pidRight.setSetpoint(error);

		double feedForward = (profileVelocity * kV)
				+ (profileAcceleration * kA);

		Robot.chassis.set(feedForward + pidLeftOutput,
				feedForward + pidRightOutput);
	}

	protected boolean isFinished()
	{
		return currentTime >= motion.getTotalTime();
	}
}
