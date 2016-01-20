/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.*;

public class Actuators {
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// Chassis
	public Talon leftChassis1, leftChassis2, rightChassis1, rightChassis2;
	
	//Intake
	public DoubleSolenoid intakeSolenoid;
	public Talon intakeMotor;

	public Actuators() {
		try {
			// Chassis
			leftChassis1 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
			leftChassis2 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
			
			rightChassis1 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
			rightChassis2 = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));
			
			//Intake
			intakeSolenoid = new DoubleSolenoid((int) Robot.config.get("INTAKE_SOLENOID_FORWARD_CHANNEL"), (int) Robot.config.get("INTAKE_SOLENOID_REVERSE_CHANNEL"));
			intakeMotor = new Talon((int) Robot.config.get("INTAKE_MOTOR"));
			
		} catch (ConfigException e) {
			logger.severe(e);
		}
	}
}
