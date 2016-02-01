package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class moveLeft extends DBugCommand
{
	private double speed;
	
	protected void init()
	{
		speed = (double) config.get("turret_Left_Speed");
	}

	protected void execute()
	{
		isFin = Robot.turret.setMotors(speed);
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		Robot.turret.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

}
