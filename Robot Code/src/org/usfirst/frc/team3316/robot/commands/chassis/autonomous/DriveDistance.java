package org.usfirst.frc.team3316.robot.commands.chassis.autonomous;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.motion.MotionPlanner;
import org.usfirst.frc.team3316.robot.chassis.motion.PlannedMotion;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
				return PIDSourceType.kDisplacement;
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
				return PIDSourceType.kDisplacement;
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

		// PID values are the same for all subclasses for until we finish
		// testings
		pidRight.setPID((double) config.get("chassis_PIDRight_KP") / 1000,
				(double) config.get("chassis_PIDRight_KI") / 1000,
				(double) config.get("chassis_PIDRight_KF") / 1000,
				(double) config.get("chassis_PIDRight_KF") / 1000);

		pidLeft.setPID((double) config.get("chassis_PIDLeft_KP") / 1000,
				(double) config.get("chassis_PIDLeft_KI") / 1000,
				(double) config.get("chassis_PIDLeft_KD") / 1000,
				(double) config.get("chassis_PIDLeft_KF") / 1000);

		pidRight.enable();
		pidLeft.enable();

		initTime = Timer.getFPGATimestamp();
		initDist = Robot.chassis.getDistance();
	}

	protected void execute()
	{
		currentTime = Timer.getFPGATimestamp() - initTime;
		
		SmartDashboard.putNumber("Motion profile velocity",
				motion.getVelocity(currentTime));

		set();
	}

	protected abstract void set();

	protected abstract boolean isFinished();

	protected void fin()
	{
		pidLeft.reset();
		pidRight.reset();
		
		pidLeft.disable();
		pidRight.disable();

		Robot.chassis.set(0, 0);
	}

	protected void interr()
	{
		fin();
	}
}
