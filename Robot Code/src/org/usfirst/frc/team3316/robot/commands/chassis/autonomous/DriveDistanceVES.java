package org.usfirst.frc.team3316.robot.commands.chassis.autonomous;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.Utils;

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
    private double [][] values = new double [][] // TODO: Calibrate these values.
    {
        {0, 0.5, 1},
        {0, 0.63, 0.1}
    };
    
	public DriveDistanceVES(double dist)
	{
		super(dist);
	}

	protected void set()
	{
		double profileVelocity = motion.getVelocity(currentTime);
		double profilePosition = motion.getPosition(currentTime);

		pidLeft.setSetpoint(profileVelocity);
		pidRight.setSetpoint(profileVelocity);

		double feedForward = Utils.valueInterpolation(profileVelocity, values);

		Robot.chassis.set(feedForward + pidLeftOutput,
				feedForward + pidRightOutput);
	}

	protected boolean isFinished()
	{
		return currentTime >= motion.getTotalTime();
	}
}
