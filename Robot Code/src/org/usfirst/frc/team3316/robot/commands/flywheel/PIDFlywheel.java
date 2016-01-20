package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDFlywheel extends DBugCommand
{
	PIDController pid;
	double v = 0;

	public PIDFlywheel()
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
				return 0;
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
		v = 0;

		SmartDashboard.putNumber("Setpoint Speed", 0);

		SmartDashboard.putNumber("PID P", 0);
		SmartDashboard.putNumber("PID I", 0);
		SmartDashboard.putNumber("PID D", 0);
		
		pid.enable();
	}

	protected void execute()
	{
		pid.setPID(SmartDashboard.getNumber("PID P", 0)*0.001,
				SmartDashboard.getNumber("PID I", 0)*0.001,
				SmartDashboard.getNumber("PID D", 0)*0.001);
		
		pid.setSetpoint(SmartDashboard.getNumber("Setpoint Speed", 0));
		Robot.flywheel.setMotors(-v);
	}

	protected boolean isFinished()
	{
		return false;
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
