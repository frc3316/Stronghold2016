package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class MoveUp extends DBugCommand
{

	private double speed, initAngle;
	private boolean onTarget;

	public MoveUp()
	{
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

		// The setpoint in the config is the difference in pot angle that we want
		onTarget = Robot.climbing.getAngle() >= ((double) config.get("climbing_Setpoint") + initAngle);
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
