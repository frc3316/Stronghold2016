package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class TurretPID extends DBugCommand
{
	private PIDController pid;
	private double pidOutput;

	public TurretPID() {
		
		requires(Robot.turret);
		
		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
				pidSource = PIDSourceType.kRate;
			}
			public double pidGet()
			{
				return Robot.turret.getAngle();
			}

			public PIDSourceType getPIDSourceType()
			{
				return null;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				pidOutput = output;
			}
		});
		
		pid.setOutputRange(-1, 1);
	}

	protected void init()
	{
		pid.enable();
	}

	protected void execute()
	{
		pid.setPID((double) config.get("turret_Pid_Kp"),
					(double) config.get("turret_Pid_Ki"),
					(double) config.get("turret_Pid_Kd"));
		
		pid.setSetpoint((double) config.get("turret_Pid_Angle"));
		
		Robot.turret.setMotors(pidOutput);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
		Robot.turret.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

}
