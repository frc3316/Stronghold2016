package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class TurretPID extends DBugCommand
{
	private PIDController pid;
	private double output;

	protected void init()
	{
		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
				pidSource = PIDSourceType.kRate;
			}
			public double pidGet()
			{
				return Robot.sensors.turretPot.get();
			}

			public PIDSourceType getPIDSourceType()
			{
				return null;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				TurretPID.this.output = output;
			}
		});
		
	}

	protected void execute()
	{
		pid.setPID((double) config.get("turret_Pid_Kp"),
					(double) config.get("turret_Pid_Ki"),
					(double) config.get("turret_Pid_Kd"));
	}

	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	protected void fin()
	{
		// TODO Auto-generated method stub
		
	}

	protected void interr()
	{
		// TODO Auto-generated method stub
		
	}

}
