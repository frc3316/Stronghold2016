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

	// General
	public Compressor compressor;

	// Chassis
	public DBugSpeedController leftChassis1, leftChassis2, rightChassis1, rightChassis2;
	private SpeedController leftChassis1SC, leftChassis2SC, rightChassis1SC, rightChassis2SC;

	public DoubleSolenoid longPistons, shortPistonsLeft, shortPistonsRight;

	// Intake
	public DoubleSolenoid intakeSolenoid;
	public DBugSpeedController intakeMotor;

	// Transport
	public DBugSpeedController transportMotor;

	// Flywheel
	public DBugSpeedController flywheelMotor;

	// Turret
	public DBugSpeedController turretMotor;

	// Hood
	public DBugSpeedController hoodMotor;

	// Climbing
	public DoubleSolenoid climbingSolenoid;
	public DBugSpeedController climbingMotor1, climbingMotor2, climbingMotor3, climbingMotor4;

	public Actuators()
	{
		/*
		 * General
		 */
		compressor = new Compressor();

		/*
		 * Chassis
		 */
		if (config.robotA)
		{
			// Speed controllers of robotA are all Victors (VictorSP).
			leftChassis1SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
			leftChassis2SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
			rightChassis1SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
			rightChassis2SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));
		}
		else
		{
			// Speed controllers of robotB are all Talons.
			leftChassis1SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
			leftChassis1SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
			rightChassis1SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
			rightChassis2SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));
		}

		leftChassis1 = new DBugSpeedController(leftChassis1SC, (boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL"));
		leftChassis2 = new DBugSpeedController(leftChassis2SC, (boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL"));

		rightChassis1 = new DBugSpeedController(rightChassis1SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL"));
		rightChassis2 = new DBugSpeedController(rightChassis2SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL"));

		longPistons = new DoubleSolenoid((int) Robot.config.get("CHASSIS_LONG_PISTONS_MODULE"),
				(int) Robot.config.get("CHASSIS_LONG_PISTONS_FORWARD"),
				(int) Robot.config.get("CHASSIS_LONG_PISTONS_REVERSE"));

		shortPistonsLeft = new DoubleSolenoid((int) Robot.config.get("CHASSIS_SHORT_PISTONS_LEFT_MODULE"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_LEFT_FORWARD"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_LEFT_REVERSE"));

		shortPistonsRight = new DoubleSolenoid((int) Robot.config.get("CHASSIS_SHORT_PISTONS_RIGHT_MODULE"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_RIGHT_FORWARD"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_RIGHT_REVERSE"));

		/*
		 * Intake
		 */
		intakeSolenoid = new DoubleSolenoid((int) Robot.config.get("INTAKE_SOLENOID_MODULE"),
				(int) Robot.config.get("INTAKE_SOLENOID_FORWARD"), (int) Robot.config.get("INTAKE_SOLENOID_REVERSE"));

		intakeMotor = new DBugSpeedController(new VictorSP((int) Robot.config.get("INTAKE_MOTOR")),
				(boolean) Robot.config.get("INTAKE_MOTOR_REVERSE"), (int) Robot.config.get("INTAKE_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("INTAKE_MOTOR_MAX_CURRENT"));

		/*
		 * Transport
		 */
		transportMotor = new DBugSpeedController(new Talon((int) Robot.config.get("TRANSPORT_MOTOR")),
				(boolean) Robot.config.get("TRANSPORT_MOTOR_REVERSE"),
				(int) Robot.config.get("TRANSPORT_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("TRANSPORT_MOTOR_MAX_CURRENT"));

		/*
		 * Flywheel
		 */
		flywheelMotor = new DBugSpeedController(new Talon((int) Robot.config.get("FLYWHEEL_MOTOR")),
				(boolean) Robot.config.get("FLYWHEEL_MOTOR_REVERSE"),
				(int) Robot.config.get("FLYWHEEL_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("FLYWHEEL_MOTOR_MAX_CURRENT"));

		/*
		 * Turret
		 */
		turretMotor = new DBugSpeedController(new Talon((int) Robot.config.get("TURRET_MOTOR")),
				(boolean) Robot.config.get("TURRET_MOTOR_REVERSE"), (int) Robot.config.get("TURRET_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("TURRET_MOTOR_MAX_CURRENT"));

		/*
		 * Hood
		 */
		hoodMotor = new DBugSpeedController(new Talon((int) Robot.config.get("HOOD_MOTOR")),
				(boolean) Robot.config.get("HOOD_MOTOR_REVERSE"), (int) Robot.config.get("HOOD_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("HOOD_MOTOR_MAX_CURRENT"));

		/*
		 * Climbing
		 */
		climbingMotor1 = new DBugSpeedController(new Talon((int) Robot.config.get("CLIMBING_MOTOR_1")),
				(boolean) Robot.config.get("CLIMBING_MOTOR_1_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_1_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_1_MAX_CURRENT"));

		climbingMotor2 = new DBugSpeedController(new Talon((int) Robot.config.get("CLIMBING_MOTOR_2")),
				(boolean) Robot.config.get("CLIMBING_MOTOR_2_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_2_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_2_MAX_CURRENT"));

		climbingMotor3 = new DBugSpeedController(new Talon((int) Robot.config.get("CLIMBING_MOTOR_3")),
				(boolean) Robot.config.get("CLIMBING_MOTOR_3_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_3_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_3_MAX_CURRENT"));

		climbingMotor4 = new DBugSpeedController(new Talon((int) Robot.config.get("CLIMBING_MOTOR_4")),
				(boolean) Robot.config.get("CLIMBING_MOTOR_4_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_4_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_4_MAX_CURRENT"));

		climbingSolenoid = new DoubleSolenoid((int) Robot.config.get("CLIMBING_SOLENOID_FORWARD_CHANNEL"),
				(int) Robot.config.get("CLIMBING_SOLENOID_REVERSE_CHANNEL"));
	}
}
