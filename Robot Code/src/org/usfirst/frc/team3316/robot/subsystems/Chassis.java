package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.*;

public class Chassis extends DBugSubsystem {

	private Talon leftMotor, rightMotor;
	private Encoder leftEncoder, rightEncoder;

	public Chassis() {
		// Actuators
		leftMotor = Robot.actuators.leftChassisTalon;
		rightMotor = Robot.actuators.rightChassisTalon;

		// Sensors
		leftEncoder = Robot.sensors.leftChassisEncoder;
		rightEncoder = Robot.sensors.rightChassisEncoder;
	}

	public void initDefaultCommand() {
	}

	/*
	 * SET Methods
	 */
	public void setMotors(double v) {
		rightMotor.set(v);
		leftMotor.set(-v);
	}
	
	/*
	 * GET Methods
	 */
	public double getDistance() {
		try {
			double leftDist = leftEncoder.getRaw() * (double)Robot.config.get("CHASSIS_WHEEL_DIAMETER");
			double rightDist = rightEncoder.getRaw() * (double)Robot.config.get("CHASSIS_WHEEL_DIAMETER");
			
			return (leftDist + rightDist) / 2;
			
		} catch (ConfigException e) {
			logger.severe(e);
		}
		
		return 0;
	}
}
