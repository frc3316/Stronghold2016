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
	public Talon leftChassis1, leftChassis2, rightChassis1, rightChassis2;

	public Actuators() {
		try {
			// Chassis
			leftChassis1 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
			leftChassis2 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
			
			rightChassis1 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
			rightChassis2 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));
			
		} catch (ConfigException e) {
			logger.severe(e);
		}
	}
}
