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
 * Abstract class for testing autonomous driving with motion planner. Reduces
 * code duplication.
 * 
 * @author D-Bug
 *
 */
public abstract class DriveDistance extends DBugCommand
{
	protected PIDController pidRight, pidLeft;
	protected double pidRightOutput, pidLeftOutput;

	protected double dist;

	protected PlannedMotion motion;

	protected double initTime = 0;
	protected double initDist = 0;
	protected double currentTime = 0;

	public DriveDistance(double dist)
	{
		this.dist = dist;

		motion = MotionPlanner.planMotion(dist);

		pidRight = new PIDController(0, 0, 0, new PIDSource()
		{

			public void setPIDSourceType(PIDSourceType pidSource)
			{
				return;
			}

			public double pidGet()
			{
				return Robot.chassis.getRightSpeed();
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kRate;
			}
		}, new PIDOutput()
		{

			public void pidWrite(double output)
			{
				pidRightOutput = output;
			}
		});

		pidLeft = new PIDController(0, 0, 0, new PIDSource()
		{

			public void setPIDSourceType(PIDSourceType pidSource)
			{
				return;
			}

			public double pidGet()
			{
				return Robot.chassis.getLeftSpeed();
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kRate;
			}
		}, new PIDOutput()
		{

			public void pidWrite(double output)
			{
				pidLeftOutput = output;
			}
		});
	}

	protected void init()
	{
		pidRight.setOutputRange(-1, 1);
		pidLeft.setOutputRange(-1, 1);

		//PID values are the same for all subclasses for until we finish testings
		pidRight.setPID((double) config.get("chassis_PIDRight_Kp"),
				(double) config.get("chassis_PIDRight_Ki"),
				(double) config.get("chassis_PIDRight_Kd"),
				(double) config.get("chassis_PIDRight_Kf"));
		
		pidLeft.setPID((double) config.get("chassis_PIDLeft_Kp"),
				(double) config.get("chassis_PIDLeft_Ki"),
				(double) config.get("chassis_PIDLeft_Kd"),
				(double) config.get("chassis_PIDLeft_Kf"));

		pidRight.enable();
		pidLeft.enable();

		initTime = Timer.getFPGATimestamp();
		initDist = Robot.chassis.getDistance();
	}

	protected void execute()
	{
		currentTime = Timer.getFPGATimestamp() - initTime;

		setPIDs();

		Robot.chassis.set(pidLeftOutput, pidRightOutput);
	}

	protected abstract void setPIDs();

	protected abstract boolean isFinished();

	protected void fin()
	{
		pidRight.reset();
		pidLeft.reset();

		Robot.chassis.set(0, 0);
	}

	protected void interr()
	{
		fin();
	}
}
