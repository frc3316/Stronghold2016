package org.usfirst.frc.team3316.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class for bundling stuff that we want in every subsystem.
 */
public abstract class DBugSubsystem extends Subsystem
{
	static DBugLogger logger = Robot.logger;
	static Config config = Robot.config;
	static PowerDistributionPanel pdp = Robot.sensors.pdp;

	private class SpeedControllerData
	{
		SpeedController sc;
		boolean reverse; // Negative factor of velocity
		int pdpChannel; // The channel in the PDP of the speed controller
		double maxCurrent; // The high threshold for current control

		public SpeedControllerData(SpeedController sc, boolean reverse,
				int pdpChannel, double maxCurrent)
		{
			this.sc = sc;
			this.reverse = reverse;
			this.pdpChannel = pdpChannel;
			this.maxCurrent = maxCurrent;
		}

		/*
		 * GET Methods
		 */

		public SpeedController getSpeedController()
		{
			return sc;
		}

		public boolean getReverse()
		{
			return reverse;
		}

		public int getPdpChannel()
		{
			return pdpChannel;
		}

		public double getMaxCurrent()
		{
			return maxCurrent;
		}
	}

	private List<SpeedControllerData> controllers = new ArrayList<>();

	public abstract void initDefaultCommand();

	protected void addSpeedController(SpeedController sc, boolean reverse,
			int pdpChannel, double maxCurrent)
	{
		controllers.add(
				new SpeedControllerData(sc, reverse, pdpChannel, maxCurrent));
	}

	protected boolean setMotors(double v)
	{
		for (SpeedControllerData d : controllers)
		{
			SpeedController sc = d.getSpeedController();
			if (pdp.getCurrent((int) d.getPdpChannel()) < d.getMaxCurrent())
			{
				sc.set(v * (d.getReverse() ? -1 : 1));
				return false;
			}
			else {
				sc.set(0);
				return true;
			}
		}

		return false;
	}
}
