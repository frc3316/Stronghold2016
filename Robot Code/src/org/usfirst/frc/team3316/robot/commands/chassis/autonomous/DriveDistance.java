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
public class DriveDistance extends DBugCommand
{
	private PIDController pid;

	private double dist, initDist = 0, currentDist, initTime = 0, currentTime;
	
	private PlannedMotion motion;
	private double profileVelocity;

	public DriveDistance(double dist)
	{
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
				currentDist = Robot.chassis.getDistance() - initDist;
				
				return currentDist;
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kRate;
			}
		}, new PIDOutput()
		{

			public void pidWrite(double output)
			{
				currentTime = Timer.getFPGATimestamp() - initTime;
				profileVelocity = motion.getVelocity(currentTime);		
				
				Robot.chassis.setMotors(output + profileVelocity, output + profileVelocity);
			}
		});
	}

	protected void init()
	{
		pid.setOutputRange(-1, 1);
		
		pid.setAbsoluteTolerance((double) config.get("chassis_DriveDistance_PID_Tolerance"));

		pid.setPID((double) config.get("chassis_DriveDistance_PID_KP") / 1000,
				(double) config.get("chassis_DriveDistance_PID_KI") / 1000,
				(double) config.get("chassis_DriveDistance_PID_KD") / 1000);
		
		pid.setSetpoint(dist);

		pid.enable();

		initTime = Timer.getFPGATimestamp();
		initDist = Robot.chassis.getDistance();
		
		currentTime = Timer.getFPGATimestamp() - initTime;
	}

	protected void execute()
	{}

	protected boolean isFinished() {
		return pid.onTarget();
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
