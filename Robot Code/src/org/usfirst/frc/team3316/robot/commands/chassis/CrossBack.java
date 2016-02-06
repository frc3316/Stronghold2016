package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class CrossBack extends DBugCommand
{
	private double speed;

	protected void init()
	{
		speed = (double) config.get("chassis_CrossDefense_Back_Voltage");
	}

	protected void execute()
	{
		Robot.chassis.setMotors(speed, speed);
	}

	protected boolean isFinished()
	{
		return Robot.chassis.isOnDefense();
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
