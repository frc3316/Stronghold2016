package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Command for climbing. Sets the climbing motors until we reach the target angle of the pot.
 * 
 * @author D-Bug
 *
 */
public class PullUp extends DBugCommand
{
	private double speed, initAngle;
	private boolean onTarget;

	public PullUp()
	{
		requires(Robot.chassis);
		requires(Robot.climbing);
		requires(Robot.flywheel);
		requires(Robot.intake);
		requires(Robot.transport);
		initAngle = Robot.climbing.getAngle();
	}

	protected void init()
	{
		speed = (double) config.get("climbing_UpSpeed");
	}

	protected void execute()
	{
		isFin = !Robot.climbing.setMotors(speed);

		// The setpoint in the config is the difference in pot angle that we want
		onTarget = Robot.climbing.getAngle() >= ((double) config.get("climbing_Setpoint") + initAngle);
		// The variable climbing_Setpoint is called periodically because we want it to update while the
		// command is running
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
