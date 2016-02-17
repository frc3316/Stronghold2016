package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Command for releasing the climbing off the rung. Sets the climbing motors
 * until we reach the target angle of the pot.
 * 
 * @author D-Bug
 *
 */
public class ReleaseDown extends DBugCommand
{
	private double speed, initAngle;
	private boolean onTarget;

	public ReleaseDown()
	{
		requires(Robot.chassis);
		requires(Robot.climbing);
		requires(Robot.flywheel);
		requires(Robot.intake);
		requires(Robot.transport);
		initAngle = Robot.climbing.getWinchAngle();
	}

	protected void init()
	{
		Robot.actuators.compressor.stop();
		
		speed = (double) config.get("climbing_DownSpeed");
	}

	protected void execute()
	{
		isFin = !Robot.climbing.setMotors(speed);

		// The setpoint in the config is the difference in pot angle that we
		// want
		onTarget = Robot.climbing.getWinchAngle() <= initAngle;
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
