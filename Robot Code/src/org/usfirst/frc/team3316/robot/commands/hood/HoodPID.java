package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class HoodPID extends DBugCommand
{
	private PIDController pid;
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
		pid.setSetpoint((double) config.get("hood_Angle_SetPoint"));

		isFin = !Robot.hood.setMotors(pidOutput);
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
