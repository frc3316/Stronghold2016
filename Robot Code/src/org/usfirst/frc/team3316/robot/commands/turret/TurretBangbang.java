package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;
import org.usfirst.frc.team3316.robot.Robot;

public class TurretBangbang extends DBugCommand
{

	private double setPoint, onVoltage, offVoltage;

	public TurretBangbang()
	{
		requires(Robot.turret);
	}

	protected void init()
	{
	}

	protected void execute()
	{
		onVoltage = (double) config.get("turret_Bangbang_OnVoltage");
		offVoltage = (double) config.get("turret_Bangbang_OffVoltage");
		setPoint = (double) AlignShooter.getTurretAngle();

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

}
