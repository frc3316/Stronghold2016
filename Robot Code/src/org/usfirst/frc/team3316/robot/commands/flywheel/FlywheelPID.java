package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlywheelPID extends DBugCommand
{
	// TODO: Add commenting

	private static PIDController pid;
	private double v = 0;
	private static double tolerance, setpoint;
	
	private boolean reachedTarget;

	public FlywheelPID()
	{
		requires(Robot.flywheel);

		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
				return;
			}

			public double pidGet()
			{
				logger.fine("flywheel pid get: " + Robot.flywheel.getRate());
				return Robot.flywheel.getRate();
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				isFin = !Robot.flywheel.setMotors(output);
				logger.finest("This is flywheel's v: " + output);
			}
		});
	}

	protected void init()
	{
		pid.setPID((double) config.get("flywheel_PID_KP") / 1000,
				(double) config.get("flywheel_PID_KI") / 1000,
				(double) config.get("flywheel_PID_KD") / 1000,
				(double) config.get("flywheel_PID_KF") / 1000);
		
		tolerance = Robot.flywheel.getTolerance();
		pid.setAbsoluteTolerance(tolerance); 
		
		v = 0;

		reachedTarget = false;
		
		logger.fine("Flywheel PID initialized.");
		
		pid.enable();
	}

	protected void execute()
	{
		setpoint = Robot.flywheel.getSetPoint();
		pid.setSetpoint(setpoint);
		
		SmartDashboard.putBoolean("PID on target", onTarget());
		
		if (onTarget() && !reachedTarget)
		{
			logger.info("Flywheel PID has reached target after " + timeSinceInitialized() + " seconds.");
			reachedTarget = true;
		}
	}
	
	public static boolean onTarget() 
	{
		return pid.onTarget();
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		pid.reset();

		Robot.flywheel.setMotors(0);
	}

	protected void interr()
	{
		fin();
		logger.fine("Flywheel PID interrupted.");
	}
}
