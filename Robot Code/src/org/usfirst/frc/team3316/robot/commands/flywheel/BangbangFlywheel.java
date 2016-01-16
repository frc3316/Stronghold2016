package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

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
		
		SmartDashboard.putNumber("Setpoint Speed", 0);
		SmartDashboard.putNumber("Bangbang Voltage", 0);
	}

	protected void execute()
	{
		if (Robot.flywheel.getRate() < SmartDashboard.getNumber("Setpoint Speed", 0))
		{
			v = SmartDashboard.getNumber("Bangbang Voltage", 0);
		}
		else
		{
			v = 0;
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
