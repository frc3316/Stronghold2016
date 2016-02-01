package org.usfirst.frc.team3316.robot.commands.turret;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.config.Config;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import java.util.Timer;

public class TurretControl
{
	private PIDController pid;
	private double pidSetPoint, pidSource, pidOutput;
	
	private Config config;
	
	private TimerTask exec;
	private static Timer timer;
	static {
		timer = new Timer();
	}

	public TurretControl()
	{
		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
				pidSource = PIDSourceType.kRate;
			}

			public double pidGet()
			{
				return pidSource;
			}

			public PIDSourceType getPIDSourceType()
			{
				return null;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				pidOutput = output;
			}
		});
		
		exec = new exec();
		
		timer.schedule(exec, (long) 0.02);
		
		pid.enable();
	}

	public void setSetPoint(double setPoint)
	{
		setPoint = pidSetPoint;
		pid.setSetpoint(pidSetPoint);
	}

	public void setSource(double source)
	{
		source = pidSource;
	}

	public double getOutput()
	{
		return pidOutput;
	}

	private class exec extends TimerTask
	{
		public void run()
		{
			pid.setPID((double) config.get("turret_Pid_Kp"),
					(double) config.get("turret_Pid_Ki"),
					(double) config.get("turret_Pid_Kd"));
		}
	}
}
