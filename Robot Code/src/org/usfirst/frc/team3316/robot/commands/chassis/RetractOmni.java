package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class RetractOmni extends DBugCommand
{
	private double timeout;

	public RetractOmni()
	{
		requires(Robot.chassis); // We don't want the driver to stall the motors
	}

	protected void init()
	{
		// Closing requires no coordination, so yolo
		Robot.chassis.closeLongPistons();
		Robot.chassis.closeShortPistonsLeft();
		Robot.chassis.closeShortPistonsRight();

		timeout = (double) config.get("chassis_RetractOmni_Timeout");
		setTimeout(timeout); // We want a timeout so we're sure that only the
								// traction wheels are touching when the command
								// ends
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return isTimedOut();
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
}
