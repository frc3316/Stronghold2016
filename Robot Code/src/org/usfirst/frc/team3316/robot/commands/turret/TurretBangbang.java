package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;
import org.usfirst.frc.team3316.robot.Robot;

public class TurretBangbang extends DBugCommand
{
	private double setPoint, onVoltage, offVoltage, tolerance;

	public TurretBangbang()
	{
		requires(Robot.turret);
	}

	protected void init()
	{
		onVoltage = (double) config.get("turret_Bangbang_OnVoltage");
		offVoltage = (double) config.get("turret_Bangbang_OffVoltage");
		tolerance = (double) config.get("turret_PID_Tolerance");
	}

	protected void execute()
	{
//		setPoint = (double) AlignShooter.getTurretAngle();
		setPoint = (double) config.get("turret_Angle_SetPoint");
		
		if (Robot.turret.getAngle() <= setPoint)
		{
			isFin = !Robot.turret.setMotors(onVoltage);
		}
		else
		{
			isFin = !Robot.turret.setMotors(offVoltage);
		}
	}

	protected boolean isFinished()
	{
		return isFin || onTarget();
	}

	protected void fin()
	{
		Robot.turret.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

	private boolean onTarget ()
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
