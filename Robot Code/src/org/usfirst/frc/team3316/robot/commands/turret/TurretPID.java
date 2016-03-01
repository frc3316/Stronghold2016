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
	private static double setPoint;
	private double lastTowerAngle;
	private static double tolerance;

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
		setPoint = 0.0;
		pidOutput = 0.0;
		lastTowerAngle = Double.MAX_VALUE;
		tolerance = (double) config.get("turret_PID_Tolerance");

		pid.setAbsoluteTolerance(tolerance);

		pid.setSetpoint(setPoint);

		pid.enable();
	}

	protected void execute()
	{
		pid.setPID((double) config.get("turret_PID_KP") / 1000, (double) config.get("turret_PID_KI") / 1000,
				(double) config.get("turret_PID_KD") / 1000);

		double towerAngle = AlignShooter.getTowerAngle();
		double currentAngle = Robot.turret.getAngle();

//		if (AlignShooter.isObjectDetected())
//		{
//			if (towerAngle != lastTowerAngle && towerAngle != 3316.0)
//			{
//				logger.finest("Frame updated, so I'm updating ");
//				setPoint = towerAngle + currentAngle;
//				lastTowerAngle = towerAngle;
//
//				// For PID Testing purposes, need to be changed back
//				setPoint = (double) config.get("turret_Angle_SetPoint");
//
//				pid.setSetpoint(setPoint);
//			}
//
//			isFin = !Robot.turret.setMotors(pidOutput);
//			logger.finest("PIDOutput: " + pidOutput);
//		}
//		else
//		{
//			isFin = !Robot.turret.setMotors(0);
//			logger.finest("isFin cockblocked me");
//		}

		setPoint = (double) config.get("turret_Angle_SetPoint");

		pid.setSetpoint(setPoint);
		isFin = !Robot.turret.setMotors(pidOutput);
	}

	public static boolean onTarget()
	{
		if (Math.abs(Robot.turret.getAngle() - setPoint) < tolerance)
		{
			return true;
		}
		else
		{
			return false;
		}
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
