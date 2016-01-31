package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.motion.MotionPlanner;
import org.usfirst.frc.team3316.robot.chassis.motion.PlannedMotion;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

/**
 * Drive Distance Velocity Setpoint. Drives a certain distance in a straight
 * line by setting a changing velocity setpoint according to a planned motion.
 * 
 * @author D-Bug
 *
 */
public class DriveDistanceVS extends DriveDistance
{
	public DriveDistanceVS(double dist)
	{
		super(dist);
	}
	
	protected void setPIDs()
	{
		pidLeft.setSetpoint(motion.getVelocity(currentTime));
		pidRight.setSetpoint(motion.getVelocity(currentTime));
	}

	protected boolean isFinished()
	{
		return (Robot.chassis.getDistance() - initDist >= dist);
	}
}
