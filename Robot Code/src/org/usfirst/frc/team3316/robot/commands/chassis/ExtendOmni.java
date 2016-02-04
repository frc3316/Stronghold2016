package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Timer;

public class ExtendOmni extends DBugCommand
{
	private double timeout;
	private double cancelTimeout;

	private double timeoutStartTime;

	private boolean leftSet, rightSet; // booleans for whether we've set already
										// the pistons to open

	public ExtendOmni()
	{
		requires(Robot.chassis); // We don't want the driver to stall the motors
	}

	protected void init()
	{
		Robot.chassis.openLongPistons();

		leftSet = false;
		rightSet = false;

		timeout = (double) config.get("chassis_ExtendOmni_Timeout");
		cancelTimeout = (double) config.get("chassis_ExtendOmni_CancelTimeout");

		timeoutStartTime = Double.MAX_VALUE;
	}

	protected void execute()
	{
		if (Robot.chassis.getHELeftBack() && Robot.chassis.getHELeftFront()
				&& !leftSet)
		{
			Robot.chassis.openShortPistonsLeft();
			leftSet = true;
		}

		if (Robot.chassis.getHERightBack() && Robot.chassis.getHERightFront()
				&& !rightSet)
		{
			Robot.chassis.openShortPistonsRight();
			rightSet = true;
		}

		if (leftSet && rightSet && timeoutStartTime == Double.MAX_VALUE)
		{
			/*
			 * We can't use here the isTimedOut method because it measures the
			 * time since the command was initialized, rather than from when the
			 * timeout was started
			 */
			timeoutStartTime = Timer.getFPGATimestamp();

		}
	}

	protected boolean isFinished()
	{
		if (timeSinceInitialized() > cancelTimeout)
		{
			// Too much time has passed and the short pistons didn't open.
			// Something is wrong and we're aborting.

			Robot.chassis.closeLongPistons();
			Robot.chassis.closeShortPistonsLeft();
			Robot.chassis.closeShortPistonsRight();

			return true;
		}

		// This is a timer
		return Timer.getFPGATimestamp() >= timeoutStartTime + timeout;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
}
