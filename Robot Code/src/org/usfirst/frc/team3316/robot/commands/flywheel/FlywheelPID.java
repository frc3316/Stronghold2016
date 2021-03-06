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
	private static PIDController pid;
	private static double setpoint;
	
	private boolean reachedTarget = false;

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
			}
		});
	}

	protected void init()
	{
		pid.setPID((double) config.get("flywheel_PID_KP") / 1000,
				(double) config.get("flywheel_PID_KI") / 1000,
				(double) config.get("flywheel_PID_KD") / 1000,
				(double) config.get("flywheel_PID_KF") / 1000);
		
		pid.enable();
		
		reachedTarget = false;
	}

	protected void execute()
	{
		setpoint = Robot.flywheel.getSetPoint();
		pid.setSetpoint(setpoint);
		
		if (Robot.flywheel.isOnTarget() && !reachedTarget)
		{
			logger.info("Flywheel PID reached target after " + timeSinceInitialized());
			reachedTarget = true;
		}
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
	}
}
