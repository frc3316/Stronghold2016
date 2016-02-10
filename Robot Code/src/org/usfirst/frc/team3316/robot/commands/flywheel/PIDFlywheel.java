package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDFlywheel extends DBugCommand
{
	// TODO: Add commenting

	private PIDController pid;
	private double v = 0;

	public PIDFlywheel()
	{
		requires(Robot.flywheel);

		// TODO: Add F term to PID

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
				return PIDSourceType.kRate;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				v = output;
			}
		});
	}

	protected void init()
	{
		pid.setPID((double) config.get("flywheel_PID_KP") / 1000,
				(double) config.get("flywheel_PID_KI") / 1000,
				(double) config.get("flywheel_PID_KD") / 1000,
				(double) config.get("flywheel_PID_KF") / 1000);
		
		v = 0;

		pid.enable();
	}

	protected void execute()
	{
		pid.setSetpoint((double) config.get("flywheel_PID_Setpoint"));

		isFin = !Robot.flywheel.setMotors(v);
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		pid.disable();

		Robot.flywheel.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
