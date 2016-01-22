package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDFlywheel extends DBugCommand
{
	PIDController pid;
	double v = 0;

	public PIDFlywheel()
	{
		requires(Robot.flywheel);

		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
				return;
			}

			public double pidGet()
			{
				return 0;
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kRate;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				v = output;
			}
		});
	}

	protected void init()
	{
		v = 0;

		Robot.sdb.putConfigVariableInSDB("flywheel_PID_setpoint");
		Robot.sdb.putConfigVariableInSDB("flywheel_PID_KP");
		Robot.sdb.putConfigVariableInSDB("flywheel_PID_KI");
		Robot.sdb.putConfigVariableInSDB("flywheel_PID_KD");

		pid.enable();
	}

	protected void execute()
	{
		try
		{
			pid.setPID((double) Robot.config.get("flywheel_PID_KP") / 1000,
					(double) Robot.config.get("flywheel_PID_KI") / 1000,
					(double) Robot.config.get("flywheel_PID_KD") / 1000);
			pid.setSetpoint((double) Robot.config.get("flywheel_PID_setpoint"));
			Robot.flywheel.setMotors(-v);
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
		pid.disable();

		Robot.flywheel.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
