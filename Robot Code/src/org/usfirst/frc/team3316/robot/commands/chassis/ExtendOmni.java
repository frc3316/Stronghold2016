package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Timer;

public class ExtendOmni extends DBugCommand
{
	private double timeout;
	private double cancelTimeout;

	private double timeoutStartTime;
	private boolean isTimeoutStarted;

	private boolean leftSet, rightSet; // booleans for whether we've set already
										// the pistons to open

	public ExtendOmni()
	{
		requires(Robot.chassis); // We don't want the driver to stall the motors
	}

	protected void init()
	{
		//If we've already extended the module, the hall effects will not be active and this command will run forever.
		if (Robot.chassis.areLongPistonsExtended() && Robot.chassis.areShortPistonsExtended())
		{
			this.cancel();
			return;
		}
		
		Robot.chassis.openLongPistons();

		leftSet = false;
		rightSet = false;

		timeout = (double) config.get("chassis_ExtendOmni_Timeout");
		cancelTimeout = (double) config.get("chassis_ExtendOmni_CancelTimeout");

		timeoutStartTime = 0;
		isTimeoutStarted = false;
	}

	protected void execute()
	{
		if (Robot.chassis.isHELeftBackOn && Robot.chassis.isHELeftFrontOn
				&& !leftSet)
		{
			Robot.chassis.openShortPistonsLeft();
			leftSet = true;
			Robot.chassis.isHELeftFrontOn = false;
			Robot.chassis.isHELeftBackOn = false;
		}

		if (Robot.chassis.isHERightBackOn && Robot.chassis.isHERightFrontOn
				&& !rightSet)
		{
			
			Robot.chassis.openShortPistonsRight();
			rightSet = true;
			Robot.chassis.isHERightFrontOn = false;
			Robot.chassis.isHERightBackOn = false;
		}

		if (leftSet && rightSet && !isTimeoutStarted)
		{
			/*
			 * We can't use here the isTimedOut method because it measures the
			 * time since the command was initialized, rather than from when the
			 * timeout was started
			 */
			timeoutStartTime = Timer.getFPGATimestamp();
			isTimeoutStarted = true;
		}
	}

	protected boolean isFinished()
	{
		if (timeSinceInitialized() > cancelTimeout)
		{
			// Too much time has passed and the short pistons didn't open.
			// Something is wrong and we're aborting.
			Robot.chassis.closeAllPistons();
			return true;
		}

		// This is a timer
		return (Timer.getFPGATimestamp() >= timeoutStartTime + timeout) && isTimeoutStarted;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
		// If the command didn't finish for some reason we're aborting as well
		Robot.chassis.closeAllPistons();
	}
}
