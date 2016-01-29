/**
 * Al robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CounterBase;
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
	public Encoder chassisLeftEncoder;
	public Encoder chassisRightEncoder;

	// Intake
	public DigitalInput intakeLS, intakeRS, intakeTS, intakeBS;
	public AnalogPotentiometer intakePot;

	// Transport
	public Encoder transportEncoder;

	// Flywheel
	public Counter flywheelCounter;
	public DigitalInput hallEffect;

	public Sensors()
	{
		pdp = new PowerDistributionPanel();

		// Chassis
		try
		{
			navx = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex)
		{
			DriverStation.reportError(
					"Error instantiating navX MXP:  " + ex.getMessage(), true);
		}

		chassisLeftEncoder = new Encoder(
				(int) Robot.config.get("CHASSIS_LEFT_ENCODER_CHANNEL_A"),
				(int) Robot.config.get("CHASSIS_LEFT_ENCODER_CHANNEL_B"),
				(boolean) Robot.config.get("CHASSIS_LEFT_ENCODER_REVERSE"),
				EncodingType.k4X);
		chassisLeftEncoder.setDistancePerPulse(
				(double) config.get("CHASSIS_LEFT_GEAR_RATIO")
						* ((double) config.get("CHASSIS_LEFT_WHEEL_DIAMETER")
								* Math.PI));

		chassisRightEncoder = new Encoder(
				(int) Robot.config.get("CHASSIS_RIGHT_ENCODER_CHANNEL_A"),
				(int) Robot.config.get("CHASSIS_RIGHT_ENCODER_CHANNEL_B"),
				(boolean) Robot.config.get("CHASSIS_RIGHT_ENCODER_REVERSE"),
				EncodingType.k4X);
		chassisLeftEncoder.setDistancePerPulse(
				(double) config.get("CHASSIS_RIGHT_GEAR_RATIO")
						* ((double) config.get("CHASSIS_RIGHT_WHEEL_DIAMETER")
								* Math.PI));

		// Intake
		intakeLS = new DigitalInput((int) Robot.config.get("INTAKE_LS")); // LS
																			// -
																			// Left
																			// Switch
		intakeRS = new DigitalInput((int) Robot.config.get("INTAKE_RS")); // RS
																			// -
																			// Right
																			// Switch
		intakeTS = new DigitalInput((int) Robot.config.get("INTAKE_TS")); // PS
																			// -
																			// Top
																			// Switch
		intakeBS = new DigitalInput((int) Robot.config.get("INTAKE_BS")); // BS
																			// -
																			// Bottom
																			// Switch
		intakePot = new AnalogPotentiometer(
				(int) Robot.config.get("INTAKE_POT"),
				(double) Robot.config.get("INTAKE_POT_FULL_RANGE"),
				(double) Robot.config.get("INTAKE_POT_OFFSET"));

		// Transport
		transportEncoder = new Encoder((int) config.get("TRANSPORT_ENCODER_A"),
				(int) config.get("TRANSPORT_ENCODER_B"),
				(boolean) config.get("TRANSPORT_ENCODER_REVERSE_DIRECTION"),
				CounterBase.EncodingType.k4X);

		// Flywheel
		hallEffect = new DigitalInput(
				(int) Robot.config.get("FLYWHEEL_COUNTER"));

		flywheelCounter = new Counter(hallEffect);
		flywheelCounter.setDistancePerPulse(1.0 / 6.0); // 6 bolts per round

	}
}
