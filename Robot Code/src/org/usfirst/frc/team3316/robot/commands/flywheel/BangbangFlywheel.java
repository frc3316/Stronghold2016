package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BangbangFlywheel extends DBugCommand
{
	double v = 0;

	public BangbangFlywheel()
	{
		requires(Robot.flywheel);
	}

	protected void init()
	{
		v = 0;
		
		Robot.sdb.putConfigVariableInSDB("flywheel_bangbang_setpoint");
		Robot.sdb.putConfigVariableInSDB("flywheel_bangbang_voltage");
	}

	protected void execute()
	{
		try
		{
			if (Robot.flywheel.getRate() < (double) config.get("flywheel_bangbang_setpoint"))
			{
				v = (double) config.get("flywheel_bangbang_voltage");
			}
			else
			{
				v = 0;
			}
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
		
		Robot.flywheel.setMotors(v);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
		Robot.flywheel.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
