package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Bang-bang control to reach a the speed specified in the config.
 * 
 * @author D-Bug
 *
 */
public class BangbangFlywheel extends DBugCommand
{
	private double setpoint, onVoltage, offVoltage;

	private double v = 0;

	public BangbangFlywheel()
	{
		requires(Robot.flywheel);
	}

	protected void init()
	{
		onVoltage = (double) config.get("flywheel_Bangbang_OnVoltage");
		offVoltage = (double) config.get("flywheel_Bangbang_OffVoltage");

		v = 0;
	}

	protected void execute()
	{
		setpoint = (double) config.get("flywheel_Bangbang_Setpoint");
		if (Robot.flywheel.getRate() < setpoint)
		{
			v = onVoltage;
		}
		else
		{
			v = offVoltage;
		}

		isFin = !Robot.flywheel.setMotors(v);
	}

	protected boolean isFinished()
	{
		return isFin;
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
