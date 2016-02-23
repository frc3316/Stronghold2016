package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.LowPassFilter;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;
import org.usfirst.frc.team3316.robot.Robot;

public class TurretBangbang extends DBugCommand
{
	private double onVoltage, offVoltage, tolerance;

	private double lastTowerAngle = Double.MAX_VALUE;
	
	MovingAverage movingAverage;
	
	public TurretBangbang()
	{
		requires(Robot.turret);
	}

	double setpoint;
	
	protected void init()
	{
		onVoltage = (double) config.get("turret_Bangbang_OnVoltage");
		offVoltage = (double) config.get("turret_Bangbang_OffVoltage");
		tolerance = (double) config.get("turret_PID_Tolerance");

		setpoint = Robot.turret.getAngle();
		
		movingAverage = new MovingAverage(20, 100, () -> {return AlignShooter.getTowerAngle();});
	}

	protected void execute()
	{
		logger.finest("setpoint: " + setpoint);
		double towerAngle = AlignShooter.getTowerAngle();
		logger.finest("Tower Angle: " + towerAngle);
		double currentAngle = Robot.turret.getAngle();
		logger.finest("Current Angle: " + currentAngle);
		
		if (AlignShooter.isObjectDetected())
		{
			logger.finest("Object detected");
			
			if (towerAngle != lastTowerAngle)
			{
				logger.finest("Frame updated, so I'm updating ");
				setpoint = towerAngle + currentAngle;
				lastTowerAngle = towerAngle;
				
				// setpoint = (double) config.get("turret_Angle_setpoint");
			}
		}
		if (towerAngle != 3316.0)
		{
			if (Math.abs(currentAngle - setpoint) <= tolerance)
			{
				logger.finest("Reached Set Point, giving 0 voltage");
				isFin = !Robot.turret.setMotors(0);
			}
			else
			{
				double vScale = 1;
				double error = Math.abs(setpoint - currentAngle);
				
				if (error > (double) config.get("turret_TurretBangbang_BigError"))
				{
					vScale = (double) config.get("turret_TurretBangbang_VScale");
				}
				
				if (currentAngle < setpoint)
				{
					logger.finest("Giving on voltage");
					isFin = !Robot.turret.setMotors(onVoltage * vScale);
				}
				else
				{
					logger.finest("Giving off voltage");
					isFin = !Robot.turret.setMotors(offVoltage * vScale);
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
		if (Math.abs(Robot.turret.getAngle() - setpoint) < tolerance)
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
