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
	private PIDController pid;
	private double pidOutput;
	private double pidGet;

	private double dist, initDist = 0, currentDist, initTime = 0, currentTime;
	

	public DriveDistance(double dist)
	{
		this.dist = dist;

		pid = new PIDController(0, 0, 0, new PIDSource()
		{

			public void setPIDSourceType(PIDSourceType pidSource)
			{
				return;
			}

			public double pidGet()
			{
				return pidGet;
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kRate;
			}
		}, new PIDOutput()
		{

			public void pidWrite(double output)
			{
				pidOutput = output;
			}
		});
	}

	protected void init()
	{
		pid.setOutputRange(-1, 1);
		pid.setOutputRange(-1, 1);

		pid.setPID((double) config.get("chassis_DriveDistance_PID_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_KD") / 1000,
				(double) config.get("chassis_DriveDistance_PID_KF") / 1000);

		pid.enable();

		initTime = Timer.getFPGATimestamp();
		initDist = Robot.chassis.getDistance();
		
		currentTime = 0;
	}

	protected void execute()
	{
		currentDist = Robot.chassis.getDistance() - initDist;
		
		currentTime = Timer.getFPGATimestamp() - initTime;
		
		pidGet = dist - currentDist;

		pid.setSetpoint((double) config.get("chassis_DriveDistance_PID_Setpoint"));
		
		Robot.chassis.setMotors(pidOutput, pidOutput);
	}

	protected boolean isFinished() {
		return false;
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
