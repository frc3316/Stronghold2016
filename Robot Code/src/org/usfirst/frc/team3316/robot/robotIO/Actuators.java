/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.*;

public class Actuators
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// Chassis
	public DBugSpeedController leftChassis1, leftChassis2, rightChassis1,
			rightChassis2;

	// Intake
	public DoubleSolenoid intakeSolenoid;
	public DBugSpeedController intakeMotor;

	// Transport
	public DBugSpeedController transportMotor1, transportMotor2;

	// Flywheel
	public DBugSpeedController flywheelMotor1, flywheelMotor2;

	public Actuators()
	{
		// Chassis
		leftChassis1 = new DBugSpeedController(
				new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1")),
				(boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"));
		leftChassis2 = new DBugSpeedController(
				new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2")),
				(boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"));

		rightChassis1 = new DBugSpeedController(
				new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1")),
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"));
		rightChassis2 = new DBugSpeedController(
				new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2")),
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"));

		// Intake
		intakeSolenoid = new DoubleSolenoid(
				(int) Robot.config.get("INTAKE_SOLENOID_FORWARD_CHANNEL"),
				(int) Robot.config.get("INTAKE_SOLENOID_REVERSE_CHANNEL"));

		intakeMotor = new DBugSpeedController(
				new Talon((int) Robot.config.get("INTAKE_MOTOR")),
				(boolean) Robot.config.get("INTAKE_MOTOR_REVERSE"),
				(int) Robot.config.get("INTAKE_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("INTAKE_MOTOR_MAX_CURRENT"));

		// Transport
		transportMotor1 = new DBugSpeedController(
				new Talon((int) Robot.config.get("TRANSPORT_MOTOR_1")),
				(boolean) Robot.config.get("TRANSPORT_MOTOR_1_REVERSE"),
				(int) Robot.config.get("TRANSPORT_MOTOR_1_PDP_CHANNEL"),
				(double) Robot.config.get("TRANSPORT_MOTOR_1_MAX_CURRENT"));
		transportMotor2 = new DBugSpeedController(
				new Talon((int) Robot.config.get("TRANSPORT_MOTOR_2")),
				(boolean) Robot.config.get("TRANSPORT_MOTOR_2_REVERSE"),
				(int) Robot.config.get("TRANSPORT_MOTOR_2_PDP_CHANNEL"),
				(double) Robot.config.get("TRANSPORT_MOTOR_2_MAX_CURRENT"));

		// Flywheel
		flywheelMotor1 = new DBugSpeedController(
				new Talon((int) Robot.config.get("FLYWHEEL_MOTOR_1")),
				(boolean) Robot.config.get("FLYWHEEL_MOTOR_1_REVERSE"),
				(int) Robot.config.get("FLYWHEEL_MOTOR_1_PDP_CHANNEL"),
				(double) Robot.config.get("FLYWHEEL_MOTOR_1_MAX_CURRENT"));
		flywheelMotor2 = new DBugSpeedController(
				new Talon((int) Robot.config.get("FLYWHEEL_MOTOR_2")),
				(boolean) Robot.config.get("FLYWHEEL_MOTOR_2_REVERSE"),
				(int) Robot.config.get("FLYWHEEL_MOTOR_2_PDP_CHANNEL"),
				(double) Robot.config.get("FLYWHEEL_MOTOR_2_MAX_CURRENT"));
	}
}
