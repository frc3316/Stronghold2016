package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class CrossDefense extends DBugCommand
{
	private double speed;
	
	public CrossDefense() {
		requires(Robot.chassis);
	}

	protected void init()
	{}

	protected void execute()
	{
		speed = (double) config.get("chassis_Curret_Defense_Speed");
		
		Robot.chassis.setMotors(speed);
	}

	protected boolean isFinished()
	{
		return !Robot.chassis.isOnDefense();
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
