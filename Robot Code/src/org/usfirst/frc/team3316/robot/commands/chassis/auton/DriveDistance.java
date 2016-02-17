package org.usfirst.frc.team3316.robot.commands.chassis.auton;

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
public class DriveDistance extends DBugCommand
{
	private PIDController pid;

	private double dist, initDist = 0, initTime = 0;

	private PlannedMotion motion;

	public DriveDistance(double dist)
	{
		requires(Robot.chassis);
		
		this.dist = dist;

		motion = MotionPlanner.planMotion(dist);

		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
				return;
			}

			public double pidGet()
			{
				double currentDist = Robot.chassis.getDistance() - initDist;

				// REMOVE AFTER TESTINGS
				SmartDashboard.putNumber("Current Distance", currentDist);

				return currentDist;
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput()
		{

			public void pidWrite(double output)
			{
				double currentTime = Timer.getFPGATimestamp() - initTime;
				double profileVelocity = motion.getVelocity(currentTime);

				double velocity = output + profileVelocity * (double) config.get("chassis_DriveDistance_KV");
				Robot.chassis.setMotors(velocity, velocity);
			}
		}, 0.02);
	}

	protected void init()
	{
		pid.setOutputRange(-1, 1);
		
		// TODO: Check if onTarget() works.
		pid.setAbsoluteTolerance(
				(double) config.get("chassis_DriveDistance_PID_Tolerance"));

		pid.setPID((double) config.get("chassis_DriveDistance_PID_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_KD") / 1000);

		pid.setSetpoint(dist);

		initTime = Timer.getFPGATimestamp();
		initDist = Robot.chassis.getDistance();

		pid.enable();
	}

	protected void execute()
	{}

	protected boolean isFinished()
	{
		double measuredSpeed = (Robot.chassis.getLeftSpeed() + Robot.chassis.getRightSpeed()) / 2;
		return measuredSpeed == 0 && pid.onTarget();
	}

	protected void fin()
	{
		pid.reset();
		pid.disable();

		Robot.chassis.setMotors(0, 0);
	}

	protected void interr()
	{
		fin();
	}
}
