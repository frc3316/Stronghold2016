package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Utils;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HoodPID extends DBugCommand
{
	private static PIDController pid;

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
				isFin = !Robot.hood.setMotors(output);
				config.add("hood_Angle_SetPoint", pid.getSetpoint());
			}
		});

		pid.setOutputRange(-1, 1);
	}

	protected void init()
	{
		pid.setAbsoluteTolerance((double) config.get("hood_PID_Tolerance"));

		pid.setPID((double) config.get("hood_PID_KP") / 1000.0, (double) config.get("hood_PID_KI") / 1000.0,
				(double) config.get("hood_PID_KD") / 1000.0);

		pid.enable();
	}

	protected void execute()
	{
		try
		{
			if (AlignShooter.isObjectDetected())
			{
				// double setPoint = (double) config.get("hood_Angle_SetPoint");

				double setPoint = (double) AlignShooter.getHoodAngle();

				pid.setSetpoint(setPoint);
			}
			else
			{
				isFin = !Robot.hood.setMotors(0);
			}
		}
		catch (Exception e)
		{
			logger.severe(e);
			isFin = !Robot.hood.setMotors(0);
		}

		logger.finest("Hood PID error: " + pid.getError());
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		pid.reset();

		Robot.hood.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

}
