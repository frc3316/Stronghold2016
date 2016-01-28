package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;

public class DBugSpeedController
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	PowerDistributionPanel pdp = Robot.sensors.pdp;

	//TODO: Fix access levels. Either all public or all private (with getters and setters).
	
	private SpeedController sc;
	public boolean reverse; // Negative factor of velocity
	private boolean isSetLimit;
	public int pdpChannel; // The channel in the PDP of the speed controller
	public double maxCurrent; // The high threshold for current control
	private int counter = 0;

	/**
	 * This method is using for adding a new speed controller to this subsystem.
	 * This method offers a better way to control all of your speed controllers.
	 * 
	 * @param sc
	 *            The speed controller to add.
	 * @param reverse
	 *            Set true if you want to reverse the voltage of the motor,
	 *            otherwise set false.
	 * @param pdpChannel
	 *            The channel of the speed controller on the PDP.
	 * @param maxCurrent
	 *            The highest current that the speed controller can get.
	 */
	public DBugSpeedController(SpeedController sc, boolean reverse,
			int pdpChannel, double maxCurrent)
	{
		this.sc = sc;
		this.reverse = reverse;
		this.isSetLimit = true;
		this.pdpChannel = pdpChannel;
		this.maxCurrent = maxCurrent;

		sc.setInverted(reverse);
	}

	/**
	 * This method is using for adding a new D-Bug Speed Controller to this
	 * subsystem. This method offers a better way to control all of your D-Bug
	 * Speed Controllers.
	 * 
	 * @param sc
	 *            The D-Bug Speed Controller
	 * @param reverse
	 *            Set true if you want to reverse the voltage of the motor,
	 *            otherwise set false.
	 */
	public DBugSpeedController(SpeedController sc, boolean reverse)
	{
		this.sc = sc;
		this.reverse = reverse;
		isSetLimit = false;

		sc.setInverted(reverse);
	}

	/**
	 * This method sets the voltage for this D-Bug Speed Controller.
	 * 
	 * @param v
	 *            The voltage (velocity) to set for this D-Bug Speed Controller.
	 * @return A boolean of the process success - true if it succeeded or false
	 *         if it failed.
	 */
	public boolean setMotor(double v)
	{
		if (!isSetLimit || Robot.sensors.pdp.getCurrent(pdpChannel) < maxCurrent)
		{
			sc.set(v);
		}
		//TODO: Counter isn't necessary if max current is the real stall current
		//TODO: Measure the stall current
		//TODO: Logic of load current should be moved to the command
		else
		{
			counter++;
		}

		if (counter >= (int) config.get("CURRENT_CONTROL_COUNTER"))
		{
			sc.set(0);

			logger.severe(
					"Current overflow at D-Bug Speed Controller on PDP channel "
							+ pdpChannel + ".");
			return false;
		}

		return true;
	}
	
	//TODO: Add get current method
	//TODO: Add get voltage method
}
