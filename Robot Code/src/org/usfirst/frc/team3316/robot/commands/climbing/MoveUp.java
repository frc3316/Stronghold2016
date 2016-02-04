package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class MoveUp extends DBugCommand
{

	private double speed, initAngle;
	private boolean onTarget;
	
	public MoveUp() {
		requires(Robot.climbing);
	}

	protected void init()
	{
		initAngle = Robot.climbing.getAngle();
	}

	protected void execute()
	{
		speed = (double) config.get("climbing_Speed");

		isFin = !Robot.climbing.setMotors(speed);

		onTarget = Robot.climbing
				.getAngle() >= ((double) config.get("CLIMBING_POT_THRESHOLD")
						+ initAngle);
	}

	protected boolean isFinished()
	{
		return (isFin || onTarget);
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
