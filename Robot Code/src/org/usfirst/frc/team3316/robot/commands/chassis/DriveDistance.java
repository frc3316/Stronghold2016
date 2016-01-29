package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.motion.MotionPlanner;
import org.usfirst.frc.team3316.robot.chassis.motion.MotionPlanner.Step;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveDistance extends DBugCommand
{
	private PIDController pidRight, pidLeft;
	private double dist;
	private double pidRightSpeed, pidLeftSpeed;
	private Step[] motion;
	private int counter = 0;

	private double initDist = 0;

	public DriveDistance(double dist)
	{
		this.dist = dist;

		motion = MotionPlanner.planMotion(dist).getSteps();

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
				pidRightSpeed = output;
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
				pidLeftSpeed = output;
			}
		});
	}

	protected void init()
	{
		pidRight.setInputRange(
				(double) config.get("CHASSIS_PID_RIGHT_MIN_SPEED"),
				(double) config.get("CHASSIS_PID_RIGHT_MAX_SPEED"));
		pidLeft.setInputRange((double) config.get("CHASSIS_PID_LEFT_MIN_SPEED"),
				(double) config.get("CHASSIS_PID_LEFT_MAX_SPEED"));

		pidRight.setOutputRange(-1, 1);
		pidLeft.setOutputRange(-1, 1);

		pidRight.setPID((double) config.get("CHASSIS_PID_RIGHT_KP"),
				(double) config.get("CHASSIS_PID_RIGHT_KI"),
				(double) config.get("CHASSIS_PID_RIGHT_KD"));
		pidLeft.setPID((double) config.get("CHASSIS_PID_LEFT_KP"),
				(double) config.get("CHASSIS_PID_LEFT_KI"),
				(double) config.get("CHASSIS_PID_LEFT_KD"));

		pidRight.enable();
		pidLeft.enable();

		counter = 0;

		initDist = Robot.chassis.getDistance();
	}

	protected void execute()
	{

		pidRight.setSetpoint(motion[counter].getVelocity());
		pidLeft.setSetpoint(motion[counter].getVelocity());

		Robot.chassis.set(pidLeftSpeed, pidRightSpeed);
	}

	protected boolean isFinished()
	{
		if (counter < motion.length - 1)
		{
			counter++;
		}

		return (Robot.chassis.getDistance() - initDist >= dist);
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
}
