package org.usfirst.frc.team3316.robot.commands.chassis;

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

	protected void setPIDs()
	{
		double profileVelocity = motion.getVelocity(currentTime);
		double profileAcceleration = motion.getAcceleration(currentTime);
		
		double currentVelocity = (Robot.chassis.getLeftSpeed() + Robot.chassis.getRightSpeed()) / 2;
	}

	protected boolean isFinished()
	{
		return false;
	}

}
