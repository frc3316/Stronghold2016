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
public class DriveDistanceVS extends DBugCommand
{
	private PIDController pidRight, pidLeft;
	private double pidRightOutput, pidLeftOutput;

	private double dist;

	private PlannedMotion motion;

	private double initTime = 0;
	private double initDist = 0;

	public DriveDistanceVS(double dist)
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

		initTime = Timer.getFPGATimestamp();
		initDist = Robot.chassis.getDistance();
	}

	protected void execute()
	{
		double currentTime = Timer.getFPGATimestamp() - initTime;

		pidRight.setSetpoint(motion.getVelocity(currentTime));
		pidLeft.setSetpoint(motion.getVelocity(currentTime));

		Robot.chassis.set(pidLeftOutput, pidRightOutput);
	}

	protected boolean isFinished()
	{
		return (Robot.chassis.getDistance() - initDist >= dist);
	}

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
