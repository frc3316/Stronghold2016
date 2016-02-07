package org.usfirst.frc.team3316.robot.commands.chassis.auton;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CrossDefense extends DBugCommand
{
	private double speed, minSpeed, DownV;
	private boolean lastIsOnDefense, isFinished, reverse;

	public CrossDefense(Direction dir)
	{
		requires(Robot.chassis);

		this.reverse = dir == Direction.FORWARDS ? false : true;
	}

	protected void init()
	{
		speed = (reverse ? -1 : 1)
				* (double) config.get("chassis_CrossDefense_Voltage");
		minSpeed = (double) config.get("chassis_CrossDefense_MinSpeed");
		DownV = (double) config.get("chassis_CrossDefense_DownV");
	}

	protected void execute()
	{
		Robot.chassis.setMotors(speed, speed);

		if (Robot.chassis.isOnDefense())
		{
			// We want the speed to decrease/increase every iteration until it
			// equals to the MinSpeed.
			if (!reverse)
			{
				speed -= speed > minSpeed ? DownV : 0.0;
			}
			else
			{
				speed += speed < -minSpeed ? DownV : 0.0;
			}
		}
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
