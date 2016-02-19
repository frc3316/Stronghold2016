package org.usfirst.frc.team3316.robot.commands.chassis;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.Timer;

public class ExtendOmni extends DBugCommand
{
	/**
	 * Task that updates the hall effects values faster than the update rate of the command. We're simply
	 * afraid that we will miss the hall effects' readings.
	 * 
	 * @author D-Bug
	 *
	 */
	private class HETask extends TimerTask
	{
		public void run()
		{
			if (Robot.chassis.getHELeftBack())
			{
				leftBack = true;
			}

			if (Robot.chassis.getHELeftFront())
			{
				leftFront = true;
			}

			if (Robot.chassis.getHERightBack())
			{
				rightBack = true;
			}

			if (Robot.chassis.getHERightFront())
			{
				rightFront = true;
			}
		}
	}

	/**
	 * Task for opening the short pistons of the left side. We want to pass this as a parameter to a timer
	 * object (to create a delay)
	 * 
	 * @author D-Bug
	 *
	 */
	private class LiftLeftSide extends TimerTask
	{
		public void run()
		{
			Robot.chassis.openShortPistonsLeft();
		}
	}

	/**
	 * Task for opening the short pistons of the right side. We want to pass this as a parameter to a timer
	 * object (to create a delay)
	 * 
	 * @author D-Bug
	 *
	 */
	private class LiftRightSide extends TimerTask
	{
		public void run()
		{
			Robot.chassis.openShortPistonsRight();
		}
	}

	private double timeout;
	private double cancelTimeout;
	private long liftTimeout; // The delay from when the hall effects are triggered to when we open the
								// pistons

	private double timeoutStartTime;
	private boolean isTimeoutStarted;

	private boolean leftSet, rightSet; // booleans for whether we've set already
										// the pistons to open
	private boolean leftFront, leftBack, rightFront, rightBack; // Booleans to mark if the according hall
																// effects have been triggered
	
	private java.util.Timer timer;
	
	public ExtendOmni()
	{
		requires(Robot.chassis); // We don't want the driver to stall the motors
	}

	protected void init()
	{
		// If we've already extended the module, the hall effects will not be active and this command will run
		// forever.
		if (Robot.chassis.areLongPistonsExtended() && Robot.chassis.areShortPistonsExtended())
		{
			this.cancel();
			return;
		}

		Robot.chassis.openLongPistons();

		leftSet = false;
		rightSet = false;

		leftFront = false;
		leftBack = false;
		rightFront = false;
		rightBack = false;

		timeout = (double) config.get("chassis_ExtendOmni_Timeout");
		cancelTimeout = (double) config.get("chassis_ExtendOmni_CancelTimeout");
		liftTimeout = (long) config.get("chassis_ExtendOmni_LiftTimeout");

		timeoutStartTime = 0;
		isTimeoutStarted = false;

		timer = new java.util.Timer();
		
		timer.schedule(new HETask(), 0, (long) config.get("chassis_ExtendOmni_HETaskPeriod"));
	}

	protected void execute()
	{
		if (!leftSet && leftFront && leftBack)
		{
			timer.schedule(new LiftLeftSide(), liftTimeout);
			leftSet = true;
		}

		if (!rightSet && rightFront && rightBack)
		{

			Robot.chassis.openShortPistonsRight();
			rightSet = true;
		}

		if (leftSet && rightSet && !isTimeoutStarted)
		{
			/*
			 * We can't use here the isTimedOut method because it measures the time since the command was
			 * initialized, rather than from when the timeout was started
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
		timer.cancel();
	}

	protected void interr()
	{
		fin();
		// If the command didn't finish for some reason we're aborting as well
		Robot.chassis.closeAllPistons();
	}
}
