package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;
import org.usfirst.frc.team3316.robot.Robot;

public class HoodBangbang extends DBugCommand
{
	private double onVoltage, offVoltage;
	private static double setPoint;
	private static double tolerance;

	public HoodBangbang()
	{
		requires(Robot.hood);
	}

	protected void init()
	{
		onVoltage = (double) config.get("hood_Bangbang_OnVoltage");
		offVoltage = (double) config.get("hood_Bangbang_OffVoltage");
		tolerance = (double) config.get("hood_PID_Tolerance");
	}

	protected void execute()
	{
		setPoint = (double) config.get("hood_Angle_SetPoint");
		if (Robot.hood.getAngle() <= setPoint)
		{
			isFin = !Robot.hood.setMotors(onVoltage);
		}
		else
		{
			isFin = !Robot.hood.setMotors(offVoltage);
		}
		/*
		if (AlignShooter.isObjectDetected())
		{
			double setPoint = (double) AlignShooter.getHoodAngle();
			if (Robot.hood.getAngle() <= setPoint)
			{
				isFin = !Robot.hood.setMotors(onVoltage);
			}
			else
			{
				isFin = !Robot.hood.setMotors(offVoltage);
			}
		}
		else
		{
			isFin = !Robot.hood.setMotors(0);
		}
		*/
	}

	protected boolean isFinished()
	{
		return isFin || onTarget();
	}

	protected void fin()
	{
		Robot.hood.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
	
	public static boolean onTarget()
	{
		if (Math.abs(Robot.hood.getAngle() - setPoint) < tolerance)
		{
			logger.fine("Hood banbang on target: " + true);
			return true;
		}
		else
		{
			logger.fine("Hood banbang on target: " + false);
			return false;
		}
	}
}
