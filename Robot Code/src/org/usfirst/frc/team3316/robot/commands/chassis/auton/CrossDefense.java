package org.usfirst.frc.team3316.robot.commands.chassis.auton;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CrossDefense extends DBugCommand
{
	private double speed;
	private boolean lastIsOnDefense, isFinished, reverse;

	public CrossDefense(boolean reverse)
	{
		requires(Robot.chassis);

		this.reverse = reverse;
	}

	protected void init()
	{
		speed = (reverse ? -1 : 1)
				* (double) config.get("chassis_CrossDefense_Voltage");
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
				speed -= speed > (double) config
						.get("chassis_CrossDefense_MinSpeed") ? (double) config
								.get("chassis_CrossDefense_DownV") : 0.0;
			}
			else
			{
				speed += speed < -(double) config
						.get("chassis_CrossDefense_MinSpeed") ? (double) config
								.get("chassis_CrossDefense_DownV") : 0.0;
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
		SmartDashboard.putBoolean("finished", true);
		Robot.chassis.setMotors(0, 0);
	}

	protected void interr()
	{
		fin();
	}

}
