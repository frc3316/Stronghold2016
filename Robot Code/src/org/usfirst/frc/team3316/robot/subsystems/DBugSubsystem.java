package org.usfirst.frc.team3316.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class for bundling stuff that we want in every subsystem.
 */
public abstract class DBugSubsystem extends Subsystem
{
	static DBugLogger logger = Robot.logger;
	static Config config = Robot.config;

	private List<DBugSpeedController> controllers = new ArrayList<>();

	public abstract void initDefaultCommand();

	/**
	 * Add the D-Bug Speed Controllers of this subsystem to a list.
	 * @param sc The D-Bug Speed Controller.
	 */
	protected void addSpeedController(DBugSpeedController sc)
	{
		controllers.add(sc);
	}

	/**
	 * This method sets the voltage for all the D-Bug Speed Controllers you've added.
	 * 
	 * @param v
	 *            The voltage (velocity) to set for all the D-Bug Speed Controllers
	 *            you've added.
	 * @return A boolean of the process success - true if it succeeded or false if
	 *         it failed.
	 */
	protected boolean setMotors(double v)
	{
		for (DBugSpeedController d : controllers)
		{
			if (!d.setMotor(v))
				return false;
		}

		return true;
	}
}
