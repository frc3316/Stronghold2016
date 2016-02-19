/**
 * Al robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.vision.VisionServer;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SPI;

public class Sensors
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	public PowerDistributionPanel pdp;

	// Chassis
	public AHRS navx;

	public Encoder chassisLeftEncoder, chassisRightEncoder;

	public DigitalInput chassisHELeftFront, chassisHELeftBack, chassisHERightFront, chassisHERightBack;

	// Intake
	public DigitalInput intakeLeftSwitch, intakeRightSwitch;
	public AnalogPotentiometer intakePot;

	// Transport
	public Counter transportCounter;

	// Flywheel
	public Counter flywheelCounter;
	public DigitalInput flywheelHE;

	// Turret
	public AnalogPotentiometer turretPot;

	// Hood
	public AnalogPotentiometer hoodPot;

	// Climbing
	public AnalogPotentiometer climbingPot;
	public DigitalInput climbingSwitch;

	public Sensors() {}
	
	/*
	 * General
	 */
	public void GeneralSensors()
	{
		pdp = new PowerDistributionPanel();
	}

	/*
	 * Vision
	 */
	public void VisionSensors()
	{
		VisionServer visionServer = new VisionServer();
		Thread visionThread = new Thread(visionServer);
		visionThread.start();
	}

	/*
	 * Chassis
	 */
	public void ChassisSensors()
	{
		try
		{
			navx = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex)
		{
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
		chassisLeftEncoder = new Encoder((int) Robot.config.get("CHASSIS_LEFT_ENCODER_CHANNEL_A"),
				(int) Robot.config.get("CHASSIS_LEFT_ENCODER_CHANNEL_B"),
				(boolean) Robot.config.get("CHASSIS_LEFT_ENCODER_REVERSE"), EncodingType.k4X);

		chassisLeftEncoder.setDistancePerPulse((double) config.get("CHASSIS_LEFT_ENCODER_DISTANCE_PER_PULSE"));

		chassisRightEncoder = new Encoder((int) Robot.config.get("CHASSIS_RIGHT_ENCODER_CHANNEL_A"),
				(int) Robot.config.get("CHASSIS_RIGHT_ENCODER_CHANNEL_B"),
				(boolean) Robot.config.get("CHASSIS_RIGHT_ENCODER_REVERSE"), EncodingType.k4X);
		chassisRightEncoder.setDistancePerPulse((double) config.get("CHASSIS_RIGHT_ENCODER_DISTANCE_PER_PULSE"));

		chassisHELeftFront = new DigitalInput((int) config.get("CHASSIS_HALL_EFFECT_LEFT_FRONT"));
		chassisHELeftBack = new DigitalInput((int) config.get("CHASSIS_HALL_EFFECT_LEFT_BACK"));
		chassisHERightFront = new DigitalInput((int) config.get("CHASSIS_HALL_EFFECT_RIGHT_FRONT"));
		chassisHERightBack = new DigitalInput((int) config.get("CHASSIS_HALL_EFFECT_RIGHT_BACK"));
	}

	/*
	 * Intake
	 */
	public void IntakeSensors()
	{
		intakeLeftSwitch = new DigitalInput((int) config.get("INTAKE_LEFT_SWITCH"));
		intakeRightSwitch = new DigitalInput((int) config.get("INTAKE_RIGHT_SWITCH"));

		intakePot = new AnalogPotentiometer((int) config.get("INTAKE_POT"),
				(double) config.get("INTAKE_POT_FULL_RANGE"), (double) config.get("INTAKE_POT_OFFSET"));
	}

	/*
	 * Transport
	 */
	public void TransportSensors()
	{
		transportCounter = new Counter((int) config.get("TRANSPORT_ENCODER"));

		// transportEncoder.setDistancePerPulse(???);
		// TODO: check the distance per pulse of the transport encoder
	}

	/*
	 * Flywheel
	 */
	public void FlywheelSensors()
	{
		flywheelHE = new DigitalInput((int) config.get("FLYWHEEL_HALL_EFFECT_COUNTER"));

		flywheelCounter = new Counter(flywheelHE);
		flywheelCounter.setDistancePerPulse(1.0 / 6.0); // 6 bolts per round
	}

	/*
	 * Turret
	 */
	public void TurretSensors()
	{
		turretPot = new AnalogPotentiometer((int) Robot.config.get("TURRET_POT"),
				(double) Robot.config.get("TURRET_POT_FULL_RANGE"), 0);
	}

	/*
	 * Hood
	 */
	public void HoodSensors()
	{
		hoodPot = new AnalogPotentiometer((int) Robot.config.get("HOOD_POT"),
				(double) Robot.config.get("HOOD_POT_FULL_RANGE"), 0);
	}

	/*
	 * Climbing
	 */
	public void ClimbingSensors()
	{
		climbingPot = new AnalogPotentiometer((int) config.get("CLIMBING_POT"),
				(double) config.get("CLIMBING_POT_FULL_RANGE"), (double) config.get("CLIMBING_POT_OFFSET"));
		climbingSwitch = new DigitalInput((int) config.get("CLIMBING_SWITCH"));
	}
}
