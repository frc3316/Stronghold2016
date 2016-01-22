/**
 * Al robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

public class Sensors {
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// Chassis
	public AHRS navx;

	// Intake
	public DigitalInput intakeLS, intakeRS, intakeTS, intakeBS;
	public AnalogPotentiometer intakePot;

	// Transport
	public Encoder transportEncoder;

	// Flywheel
	public Counter flywheelCounter;
	public DigitalInput flywheelHE;

	public Sensors() {
		try {
			// Chassis
			try {
				navx = new AHRS(SPI.Port.kMXP);
			} catch (RuntimeException ex) {
				DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
			}

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
			intakePot = new AnalogPotentiometer((int) Robot.config.get("INTAKE_POT"),
					(double) Robot.config.get("INTAKE_POT_FULL_RANGE"), (double) Robot.config.get("INTAKE_POT_OFFSET"));

			// Transport
			transportEncoder = new Encoder((int) config.get("TRANSPORT_ENCODER_A"),
					(int) config.get("TRANSPORT_ENCODER_B"),
					(boolean) config.get("TRANSPORT_ENCODER_REVERSE_DIRECTION"), CounterBase.EncodingType.k4X);

			// Flywheel
			flywheelHE = new DigitalInput((int) Robot.config.get("FLYWHEEL_HALL_EFFECT")); // HE
																							// -
																							// Hall
																							// Effect
			flywheelCounter = new Counter(flywheelHE);
			flywheelCounter.setDistancePerPulse(1.0 / 6.0); // 6 bolts per round

		} catch (ConfigException e) {
			logger.severe(e);
		}
	}
}
