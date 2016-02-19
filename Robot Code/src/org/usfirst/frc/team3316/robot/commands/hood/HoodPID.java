package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class HoodPID extends DBugCommand
{
	private static PIDController pid;
	private double pidOutput;

	public HoodPID()
	{

		requires(Robot.hood);

		pid = new PIDController(0, 0, 0, new PIDSource()
		{
			public void setPIDSourceType(PIDSourceType pidSource)
			{
			}

			public double pidGet()
			{
				return Robot.hood.getAngle();
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput()
		{
			public void pidWrite(double output)
			{
				pidOutput = output;
			}
		});

		pid.setOutputRange(-1, 1);
	}

	protected void init()
	{
		pid.setPID((double) config.get("hood_PID_KP"), (double) config.get("hood_PID_KI"),
				(double) config.get("hood_PID_KD"));

		pid.enable();
	}

	protected void execute()
	{
		pid.setPID((double) config.get("hood_PID_KP"),
				(double) config.get("hood_PID_KI"),
				(double) config.get("hood_PID_KD"));
		
		if (AlignShooter.isObjectDetected())
		{
			double setPoint = (double) AlignShooter.getHoodAngle();
			pid.setSetpoint(setPoint);
			
			isFin = !Robot.hood.setMotors(pidOutput);
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
	
	public static boolean onTarget() {
		return pid.onTarget();
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
