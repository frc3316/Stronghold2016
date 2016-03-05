package org.usfirst.frc.team3316.robot.commands.chassis.auton;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ReachDefense extends DBugCommand
{
	private boolean reverse;
	private double speed, timeout;
	private int counter;

	public ReachDefense(Direction dir)
	{
		requires(Robot.chassis);

		this.reverse = dir == Direction.FORWARDS ? false : true;
	}

	protected void init()
	{
		speed = (double) config.get("chassis_ReachDefense_Speed");
		timeout = (double) config.get("chassis_ReachDefense_Timeout");
	}

	protected void execute()
	{
		if (Robot.chassis.isOnDefense())
		{
			if (reverse)
			{
				Robot.chassis.setMotors(-speed, -speed);
			}
			else
			{
				Robot.chassis.setMotors(speed, speed);
			}
			counter++;
		}
	}

	protected boolean isFinished()
	{
		return counter / 50 > timeout;
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
