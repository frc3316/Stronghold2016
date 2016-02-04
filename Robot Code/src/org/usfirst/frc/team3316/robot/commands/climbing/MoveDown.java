package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class MoveDown extends DBugCommand
{

	private double speed;
	
	public MoveDown() {
		requires(Robot.climbing);
	}
	
	protected void init()
	{}

	protected void execute()
	{
		speed = -(double) config.get("climbing_Speed");

		isFin = !Robot.climbing.setMotors(speed);
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		Robot.climbing.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}

}
