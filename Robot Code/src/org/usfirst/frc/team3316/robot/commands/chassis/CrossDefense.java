package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CrossDefense extends DBugCommand
{
	private double rightSpeed, leftSpeed;
	private PIDController pid;
	private boolean lastIsOnDefense, isFinished, reverse;

	public CrossDefense(boolean reverse)
	{
		requires(Robot.chassis);

		this.reverse = reverse;

		pid = new PIDController(0, 0, 0, new PIDSource()
		{

			public void setPIDSourceType(PIDSourceType pidSource)
			{
			}

			public double pidGet()
			{
				return Robot.chassis.getYaw();
			}

			public PIDSourceType getPIDSourceType()
			{
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput()
		{

			public void pidWrite(double output)
			{
				if (reverse)
				{
					leftSpeed += output;
					rightSpeed -= output;
				}
				else
				{
					leftSpeed -= output;
					rightSpeed += output;
				}
			}
		});

		pid.setOutputRange(-1, 1);

		leftSpeed = (reverse ? -1 : 1)
				* (double) config.get("chassis_Crossing_Defense_Left_Speed");
		rightSpeed = (reverse ? -1 : 1)
				* (double) config.get("chassis_Crossing_Defense_Right_Speed");
	}

	protected void init()
	{
		pid.enable();
	}

	protected void execute()
	{
		pid.setPID((double) config.get("chassis_CrossDefense_Pid_Kp"),
				(double) config.get("chassis_CrossDefense_Pid_Ki"),
				(double) config.get("chassis_CrossDefense_Pid_Kd"));

		pid.setSetpoint(
				(double) config.get("chassis_CrossDefense_Pid_Setpoint"));

		Robot.chassis.setMotors(leftSpeed, rightSpeed);
	}

	protected boolean isFinished()
	{
		isFinished = (lastIsOnDefense && !Robot.chassis.isOnDefense());
		lastIsOnDefense = Robot.chassis.isOnDefense();

		return isFinished;
	}

	protected void fin()
	{
		Robot.chassis.setMotors(0, 0);
	}

	protected void interr()
	{
		fin();
	}

}
