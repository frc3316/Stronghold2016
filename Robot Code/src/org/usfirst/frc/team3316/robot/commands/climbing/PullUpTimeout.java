package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Point;
import org.usfirst.frc.team3316.robot.utils.Utils;

/*
 * TO REMOVE AFTER TESTINGS
 */
public class PullUpTimeout extends DBugCommand
{
	public double maxSpeed, currentSpeed, timeout, currentTime;

	public PullUpTimeout()
	{
		requires(Robot.climbing);
	}

	protected void init()
	{
		maxSpeed = (double) config.get("climbing_UpSpeed");
		timeout = (double) config.get("climbing_Timeout") * 1000;
		currentTime = 0;
		currentSpeed = 0;
	}

	protected void execute()
	{
		currentSpeed = Utils.scale(currentTime, new Point(0, 0), new Point(timeout, maxSpeed));
		currentTime += 20.0;

		Robot.climbing.setMotors(currentSpeed);
	}

	protected boolean isFinished()
	{
		return currentTime > timeout;
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
