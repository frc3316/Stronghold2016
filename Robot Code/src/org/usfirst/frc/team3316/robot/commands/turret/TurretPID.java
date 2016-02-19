package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class TurretPID extends DBugCommand
{
	private static PIDController pid;
	private double pidOutput;

	public TurretPID()
	{

		requires(Robot.turret);

		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
			}

			public double pidGet()
			{
				return Robot.turret.getAngle();
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kDisplacement;
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
		pid.setPID((double) config.get("turret_PID_KP"), (double) config.get("turret_PID_KI"),
				(double) config.get("turret_PID_KD"));

		pid.enable();
	}

	protected void execute()
	{
		pid.setPID((double) config.get("turret_PID_KP"),
				(double) config.get("turret_PID_KI"),
				(double) config.get("turret_PID_KD"));
		
		pid.setAbsoluteTolerance((double) config.get("turret_PID_Tolerance"));
		
		if (AlignShooter.isObjectDetected())
		{
			double setPoint = (double) AlignShooter.getTurretAngle();
			pid.setSetpoint(setPoint);
			
			isFin = !Robot.turret.setMotors(pidOutput);
		}
		else
		{
			isFin = !Robot.turret.setMotors(0);
		}
	}
	
	public static boolean onTarget() {
		return pid.onTarget();
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		pid.reset();
		
		Robot.turret.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

}
