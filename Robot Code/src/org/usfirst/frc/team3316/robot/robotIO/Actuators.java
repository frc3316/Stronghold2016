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
	public DBugSpeedController chassisLeft1, chassisLeft2, chassisRight1, chassisRight2;
	public SpeedController chassisLeft1SC, chassisLeft2SC, chassisRight1SC, chassisRight2SC;

	public DoubleSolenoid chassisLongPistons, chassisShortPistonsLeft, chassisShortPistonsRight;

	// Intake
	public DoubleSolenoid intakeSolenoid;

	public DBugSpeedController intakeMotor;
	public SpeedController intakeSC;

	// Transport
	public DBugSpeedController transportMotor;
	public SpeedController transportSC;

	// Flywheel
	public DBugSpeedController flywheelMotor;
	public SpeedController flywheelSC;

	// Turret
	public DBugSpeedController turretMotor;
	public SpeedController turretSC;

	// Hood
	public DBugSpeedController hoodMotor;
	public SpeedController hoodSC;

	// Climbing
	public DoubleSolenoid climbingSolenoid;
	public DBugSpeedController climbingMotor1, climbingMotor2, climbingMotor3, climbingMotor4;
	public SpeedController climbingMotorSC1, climbingMotorSC2, climbingMotorSC3, climbingMotorSC4;

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
			// Speed controllers of robotA are all VictorSP's.
			// We know that in reality 2 of them will be TalonSRX's, in the meantime but we're ignoring it.
			chassisLeft1SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
			chassisLeft2SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
			chassisRight1SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
			chassisRight2SC = new VictorSP((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));

			intakeSC = new VictorSP((int) Robot.config.get("INTAKE_MOTOR"));

			transportSC = new VictorSP((int) Robot.config.get("TRANSPORT_MOTOR"));

			flywheelSC = new VictorSP((int) Robot.config.get("FLYWHEEL_MOTOR"));

			turretSC = new VictorSP((int) Robot.config.get("TURRET_MOTOR"));

			hoodSC = new VictorSP((int) Robot.config.get("HOOD_MOTOR"));

			climbingMotorSC1 = new VictorSP((int) Robot.config.get("CLIMBING_MOTOR_1"));
			climbingMotorSC2 = new VictorSP((int) Robot.config.get("CLIMBING_MOTOR_2"));
			climbingMotorSC3 = new VictorSP((int) Robot.config.get("CLIMBING_MOTOR_3"));
			climbingMotorSC4 = new VictorSP((int) Robot.config.get("CLIMBING_MOTOR_4"));
		}
		else
		{
			// Speed controllers of robotB are all Talons.
			// We know that in reality some of them will be TalonSRX's, in the meantime but we're ignoring it.
			chassisLeft1SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_1"));
			chassisLeft1SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_LEFT_2"));
			chassisRight1SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_1"));
			chassisRight2SC = new Talon((int) Robot.config.get("CHASSIS_MOTOR_RIGHT_2"));

			intakeSC = new Talon((int) Robot.config.get("INTAKE_MOTOR"));

			transportSC = new Talon((int) Robot.config.get("TRANSPORT_MOTOR"));

			flywheelSC = new Talon((int) Robot.config.get("FLYWHEEL_MOTOR"));

			turretSC = new Talon((int) Robot.config.get("TURRET_MOTOR"));

			hoodSC = new Talon((int) Robot.config.get("HOOD_MOTOR"));

			climbingMotorSC1 = new Talon((int) Robot.config.get("CLIMBING_MOTOR_1"));
			climbingMotorSC2 = new Talon((int) Robot.config.get("CLIMBING_MOTOR_2"));
			climbingMotorSC3 = new Talon((int) Robot.config.get("CLIMBING_MOTOR_3"));
			climbingMotorSC4 = new Talon((int) Robot.config.get("CLIMBING_MOTOR_4"));
		}

		chassisLeft1 = new DBugSpeedController(chassisLeft1SC, (boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL"));
		chassisLeft2 = new DBugSpeedController(chassisLeft2SC, (boolean) Robot.config.get("CHASSIS_MOTOR_LEFT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL"));

		chassisRight1 = new DBugSpeedController(chassisRight1SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL"));
		chassisRight2 = new DBugSpeedController(chassisRight2SC,
				(boolean) Robot.config.get("CHASSIS_MOTOR_RIGHT_REVERSE"),
				(int) config.get("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL"));

		chassisLongPistons = new DoubleSolenoid((int) Robot.config.get("CHASSIS_LONG_PISTONS_MODULE"),
				(int) Robot.config.get("CHASSIS_LONG_PISTONS_FORWARD"),
				(int) Robot.config.get("CHASSIS_LONG_PISTONS_REVERSE"));

		chassisShortPistonsLeft = new DoubleSolenoid((int) Robot.config.get("CHASSIS_SHORT_PISTONS_LEFT_MODULE"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_LEFT_FORWARD"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_LEFT_REVERSE"));

		chassisShortPistonsRight = new DoubleSolenoid((int) Robot.config.get("CHASSIS_SHORT_PISTONS_RIGHT_MODULE"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_RIGHT_FORWARD"),
				(int) Robot.config.get("CHASSIS_SHORT_PISTONS_RIGHT_REVERSE"));

		/*
		 * Intake
		 */
		intakeSolenoid = new DoubleSolenoid((int) Robot.config.get("INTAKE_SOLENOID_MODULE"),
				(int) Robot.config.get("INTAKE_SOLENOID_FORWARD_CHANNEL"), (int) Robot.config.get("INTAKE_SOLENOID_REVERSE_CHANNEL"));

		intakeMotor = new DBugSpeedController(intakeSC, (boolean) Robot.config.get("INTAKE_MOTOR_REVERSE"),
				(int) Robot.config.get("INTAKE_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("INTAKE_MOTOR_MAX_CURRENT"));

		/*
		 * Transport
		 */
		transportMotor = new DBugSpeedController(transportSC, (boolean) Robot.config.get("TRANSPORT_MOTOR_REVERSE"),
				(int) Robot.config.get("TRANSPORT_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("TRANSPORT_MOTOR_MAX_CURRENT"));

		/*
		 * Flywheel
		 */
		flywheelMotor = new DBugSpeedController(flywheelSC, (boolean) Robot.config.get("FLYWHEEL_MOTOR_REVERSE"),
				(int) Robot.config.get("FLYWHEEL_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("FLYWHEEL_MOTOR_MAX_CURRENT"));

		/*
		 * Turret
		 */
		turretMotor = new DBugSpeedController(turretSC, (boolean) Robot.config.get("TURRET_MOTOR_REVERSE"),
				(int) Robot.config.get("TURRET_MOTOR_PDP_CHANNEL"),
				(double) Robot.config.get("TURRET_MOTOR_MAX_CURRENT"));

		/*
		 * Hood
		 */
		hoodMotor = new DBugSpeedController(hoodSC, (boolean) Robot.config.get("HOOD_MOTOR_REVERSE"),
				(int) Robot.config.get("HOOD_MOTOR_PDP_CHANNEL"), (double) Robot.config.get("HOOD_MOTOR_MAX_CURRENT"));

		/*
		 * Climbing
		 */
		climbingMotor1 = new DBugSpeedController(climbingMotorSC1,
				(boolean) Robot.config.get("CLIMBING_MOTOR_1_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_1_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_1_MAX_CURRENT"));

		climbingMotor2 = new DBugSpeedController(climbingMotorSC2,
				(boolean) Robot.config.get("CLIMBING_MOTOR_2_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_2_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_2_MAX_CURRENT"));

		climbingMotor3 = new DBugSpeedController(climbingMotorSC3,
				(boolean) Robot.config.get("CLIMBING_MOTOR_3_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_3_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_3_MAX_CURRENT"));

		climbingMotor4 = new DBugSpeedController(climbingMotorSC4,
				(boolean) Robot.config.get("CLIMBING_MOTOR_4_REVERSE"),
				(int) Robot.config.get("CLIMBING_MOTOR_4_PDP_CHANNEL"),
				(double) Robot.config.get("CLIMBING_MOTOR_4_MAX_CURRENT"));

		climbingSolenoid = new DoubleSolenoid((int) Robot.config.get("CLIMBING_SOLENOID_FORWARD"),
				(int) Robot.config.get("CLIMBING_SOLENOID_REVERSE"));
	}
}
