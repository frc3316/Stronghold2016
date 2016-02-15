package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;
import org.usfirst.frc.team3316.robot.Robot;

public class HoodBangbang extends DBugCommand
{
	private double onVoltage, offVoltage;

	public HoodBangbang()
	{
		requires(Robot.hood);
	}

	protected void init()
	{
		onVoltage = (double) config.get("hood_Bangbang_OnVoltage");
		offVoltage = (double) config.get("hood_Bangbang_OffVoltage");
	}

	protected void execute()
	{
<<<<<<< HEAD
		setPoint = (double) config.get("hood_Angle_SetPoint");
		if (Robot.hood.getAngle() <= setPoint)
=======
		if (AlignShooter.isObjectDetected())
>>>>>>> master
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
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		Robot.hood.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
