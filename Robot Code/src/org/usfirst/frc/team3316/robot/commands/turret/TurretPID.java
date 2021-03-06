package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretPID extends DBugCommand
{
	private static PIDController pid;
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
				isFin = !Robot.turret.setMotors(output);
			}
		});

		pid.setOutputRange(-1, 1);
	}

	protected void init()
	{
		setPoint = 0.0;
		lastTowerAngle = Double.MAX_VALUE;
		tolerance = (double) config.get("turret_PID_Tolerance");

		pid.setAbsoluteTolerance(tolerance);

		pid.setSetpoint(setPoint);

		pid.enable();
	}

	protected void execute()
	{
		pid.setPID((double) config.get("turret_PID_KP") / 1000, 
				(double) config.get("turret_PID_KI") / 1000,
				(double) config.get("turret_PID_KD") / 1000);

		double currentAngle = Robot.turret.getAngle();

		/*
		 * This code is with setpoint set by the vision
		 */
		if (AlignShooter.isObjectDetected())
		{
			double towerAngle = AlignShooter.getTowerAngle();
			
			if (towerAngle != lastTowerAngle && towerAngle != 3316.0)
			{
				
				setPoint = towerAngle + currentAngle;
				
				logger.finest("Turret setPoint: " + setPoint);
				
				lastTowerAngle = towerAngle;

				pid.setSetpoint(setPoint);
			}
		}
		else
		{
			pid.reset();
			pid.enable();
			
			isFin = !Robot.turret.setMotors(0);
		}

		config.add("turret_Angle_SetPoint", pid.getSetpoint());
		
		/*
		 * This code is with setpoint set by the config
		 */
//		setPoint = (double) config.get("turret_Angle_SetPoint");
//
//		pid.setSetpoint(setPoint);
//		isFin = !Robot.turret.setMotors(pidOutput);
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
