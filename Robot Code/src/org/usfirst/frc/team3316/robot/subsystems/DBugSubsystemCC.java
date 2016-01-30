package org.usfirst.frc.team3316.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

/**
 * DBugSubsystemCC - D-Bug Subsystem Current Control.
 * This class should be extended by subsystems which use the setMotors method, for all of its D-Bug Speed Controller. 
 * @author D-Bug
 *
 */
public abstract class DBugSubsystemCC extends DBugSubsystem
{
	private List<DBugSpeedController> controllers = new ArrayList<>();

	public abstract void initDefaultCommand();

	/**
	 * Add the D-Bug Speed Controllers of this subsystem to a list.
	 * 
	 * @param sc
	 *            The D-Bug Speed Controller.
	 */
	protected void addSpeedController(DBugSpeedController sc)
	{
		controllers.add(sc);
	}
	
	/**
	 * This method sets the voltage for all the D-Bug Speed Controllers you've
	 * added.
	 * 
	 * @param v
	 *            The voltage (velocity) to set for all the D-Bug Speed
	 *            Controllers you've added.
	 * @return A boolean of the process success - true if it succeeded or false
	 *         if it failed.
	 */
	public boolean setMotors(double v)
	{
		boolean ok = true;
		
		for (DBugSpeedController d : controllers)
		{
			if (!d.setMotor(v))
			{
				ok = false;
				break;
			}
		}

		if (!ok)
		{
			for (DBugSpeedController d : controllers)
			{
				d.setMotor(0);
			}

			logger.severe(this.getName() + " is burning to the ground");
			
			return false;
		}

		return true;
	}
}
