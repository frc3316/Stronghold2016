package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;
import org.usfirst.frc.team3316.robot.Robot;

public class HoodBangbang extends DBugCommand
{
	private double onVoltage, offVoltage, setPoint, tolerance, downOffset;
	private boolean movingUp = false;

	public HoodBangbang()
	{
		requires(Robot.hood);
	}

	protected void init()
	{
		onVoltage = (double) config.get("hood_Bangbang_OnVoltage");
		offVoltage = (double) config.get("hood_Bangbang_OffVoltage");
		tolerance = (double) config.get("hood_PID_Tolerance");
		downOffset = (double) config.get("hood_Bangbang_DownOffset");
		
		movingUp = false;
	}

	protected void execute()
	{
		setPoint = (double) config.get("hood_Angle_SetPoint");

		/*
		if (AlignShooter.isObjectDetected())
		{
			setPoint = (double) AlignShooter.getHoodAngle();
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
		
		if ((movingUp == true && Robot.hood.getAngle() <= setPoint) || Robot.hood.getAngle() <= setPoint - downOffset)
		{
			isFin = !Robot.hood.setMotors(onVoltage);
			movingUp = true;
		}
		else
		{
			isFin = !Robot.hood.setMotors(offVoltage);
		}
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
	
	private boolean onTarget ()
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
