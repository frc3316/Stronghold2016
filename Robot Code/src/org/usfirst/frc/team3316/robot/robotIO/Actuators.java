/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.*;

public class Actuators {
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// Chassis
	public Talon leftChassisTalon, rightChassisTalon;

	public Actuators() {
		try {
			// Chassis
			leftChassisTalon = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT"));
			
			rightChassisTalon = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT"));
			
		} catch (ConfigException e) {
			logger.severe(e);
		}
	}
}
