package org.usfirst.frc.team3316.robot.commands.chassis;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ExtendOmni extends DBugCommand
{
	/**
	 * Tasks for opening the short pistons of each side. We want to pass this as a parameter to a timer
	 * object (to create a delay)
	 * 
	 * @author D-Bug
	 *
	 */
	private class LiftLeftSide extends TimerTask
	{
		public void run()
		{
			leftSet = Robot.chassis.openShortPistonsLeft();
		}
	}
	
	private class LiftRightSide extends TimerTask
	{
		public void run()
		{
			rightSet = Robot.chassis.openShortPistonsRight();
		}
	}

	private int liftTimeout; // The delay from when the hall effects are triggered to when we open the
								// pistons

	private boolean leftSet, rightSet; // booleans for whether we've set already
										// the pistons to open
	
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
		
		liftTimeout = (int) config.get("chassis_ExtendOmni_LiftTimeout");

		timer = new java.util.Timer();
		
		timer.schedule(new LiftLeftSide(), liftTimeout);
		timer.schedule(new LiftRightSide(), liftTimeout);
	}

	protected void execute()
	{}

	protected boolean isFinished()
	{
		return leftSet && rightSet;
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
