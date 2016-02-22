package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;
import org.usfirst.frc.team3316.robot.Robot;

public class TurretBangbang extends DBugCommand
{
	private double setPoint, onVoltage, offVoltage, tolerance;

	private double lastTowerAngle = Double.MAX_VALUE;

	public TurretBangbang()
	{
		requires(Robot.turret);
	}

	double towerAngle;
	
	protected void init()
	{
		onVoltage = (double) config.get("turret_Bangbang_OnVoltage");
		offVoltage = (double) config.get("turret_Bangbang_OffVoltage");
		tolerance = (double) config.get("turret_PID_Tolerance");

		towerAngle = (double) AlignShooter.getTowerAngle();
	}

	protected void execute()
	{
		if (AlignShooter.isObjectDetected())
		{
			logger.finest("Object detected");

			setPoint = towerAngle + Robot.turret.getAngle();

			logger.finest("Setpoint is: " + setPoint);
			// setPoint = (double) config.get("turret_Angle_SetPoint");

			if (towerAngle != 3316.0)
			{
				if (Robot.turret.getAngle() <= setPoint)
				{
					logger.finest("Giving on voltage");
					isFin = !Robot.turret.setMotors(onVoltage);
				}
				else
				{
					logger.finest("Giving off voltage");
					isFin = !Robot.turret.setMotors(offVoltage);
				}
			}
		}
		else
		{
			Robot.turret.setMotors(0);
		}
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		Robot.turret.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

	private boolean onTarget()
	{
		if (Math.abs(Robot.turret.getAngle() - setPoint) < tolerance)
		{
			logger.fine("Turret banbang on target: " + true);
			return true;
		}
		else
		{
			logger.fine("Turret banbang on target: " + false);
			return false;
		}
	}
}
