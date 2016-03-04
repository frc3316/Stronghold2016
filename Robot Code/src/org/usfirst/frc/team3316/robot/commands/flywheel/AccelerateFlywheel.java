package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Utils;

/**
 * Command for accelerating the flywheel a bit more cleanly, to prevent excess
 * current draws
 */
public class AccelerateFlywheel extends DBugCommand
{
	double[][] accelTable;
	double timeToAccel;
	
	boolean finish = false;

	public AccelerateFlywheel()
	{
		requires(Robot.flywheel);
	}

	protected void init()
	{
		timeToAccel = (double) config
				.get("flywheel_AccelerateFlywheel_Timeout");

		accelTable = new double[][] { { 0, timeToAccel }, { 0, 0.5 } };

		setTimeout(timeToAccel);
		
		finish = false;
		
		if (Robot.flywheel.getRate() != 0)
		{
			finish = true; //We can't cancel the command because it may be a part of a command sequence
			return;
		}
	}

	protected void execute()
	{
		isFin = !Robot.flywheel.setMotors(
				Utils.valueInterpolation(timeSinceInitialized(), accelTable));
	}

	protected boolean isFinished()
	{
		return isTimedOut() || finish;
	}

	protected void fin()
	{
		Robot.flywheel.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
