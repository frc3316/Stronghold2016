package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Command for releasing the climbing off the rung. Sets the climbing motors until we reach the target angle
 * of the pot.
 * 
 * @author D-Bug
 *
 */
public class MoveDown extends DBugCommand
{
	private double speed, initAngle;
	private boolean onTarget;

	public MoveDown()
	{
		requires(Robot.climbing);
	}

	protected void init()
	{
		initAngle = Robot.climbing.getAngle();
	}

	protected void execute()
	{
		speed = -(double) config.get("climbing_Speed");

		isFin = !Robot.climbing.setMotors(speed);

		// The setpoint in the config is the difference in pot angle that we want
		onTarget = Robot.climbing.getAngle() <= initAngle - ((double) config.get("climbing_Setpoint"));
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
