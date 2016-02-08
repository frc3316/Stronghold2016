package org.usfirst.frc.team3316.robot.chassis.motion;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Timer;

public class MeasureKinematics extends DBugCommand
{
	double maxVelocity = 0;
	double timeToDrive = 0;

	double initialDecelTime = -1;

	boolean isAccelerating = true;

	double currentSpeed = -1;

	public MeasureKinematics(double time)
	{
		this.timeToDrive = time;
	}

	protected void init()
	{
		maxVelocity = 0;

		initialDecelTime = -1;

		isAccelerating = true;

		currentSpeed = -1;
		
		setTimeout(timeToDrive);
	}

	protected void execute()
	{
		if (!isTimedOut())
		{
			isAccelerating = true;

			Robot.chassis.setMotors(1, 1);
		}
		else
		{
			isAccelerating = false;
			Robot.chassis.setMotors(-0.08, -0.08);

			if (initialDecelTime == -1)
			{
				initialDecelTime = Timer.getFPGATimestamp();
			}
		}

		currentSpeed = (Robot.chassis.getLeftSpeed()
				+ Robot.chassis.getRightSpeed()) / 2;

		if (maxVelocity < currentSpeed)
		{
			maxVelocity = currentSpeed;
		}
	}

	protected boolean isFinished()
	{
		return isTimedOut() && (currentSpeed == 0) && !isAccelerating;
	}

	protected void fin()
	{
		double accel = maxVelocity / timeToDrive;
		double decel = -maxVelocity
				/ (Timer.getFPGATimestamp() - initialDecelTime);

		logger.info("Max Velocity: " + maxVelocity);
		logger.info("Accel: " + accel);
		logger.info("Decel: " + decel);
	}

	protected void interr()
	{
		fin();
	}
}
