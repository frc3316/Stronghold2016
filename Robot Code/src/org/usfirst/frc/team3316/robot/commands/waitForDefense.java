package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import com.kauailabs.navx.frc.AHRS;

public class waitForDefense extends DBugCommand {
	private int counter;
	private AHRS navX;

	public waitForDefense() {
		navX = Robot.sensors.navX;
	}

	protected void init() {
		counter = 0;
	}

	protected void execute() {
		try {
			if (navX.getAngle() <= (double) Robot.config.get("DEFENSE_ANGLE_RANGE")) {
				counter++;
			} else {
				counter = 0;
			}
		} catch (ConfigException e) {
			logger.severe(e);
		}
	}

	protected boolean isFinished() {
		try {
			if (counter >= (int)Math.round((double)Robot.config.get("DEFENSE_ANGLE_TIMEOUT") / 20)) // isTimedOut
			{
				return true;
			}
		} catch (ConfigException e) {
			logger.severe(e);
		}

		return false;
	}

	protected void fin() {
	}

	protected void interr() {
	}

}
