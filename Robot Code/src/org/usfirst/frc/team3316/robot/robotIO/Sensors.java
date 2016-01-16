/**
 * Al robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

public class Sensors {
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	public AHRS navX;

	// Chassis
	public Encoder leftChassisEncoder, rightChassisEncoder;

	public Sensors() {
		// navX
		try {
			navX = new AHRS(SPI.Port.kMXP);

		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}

		// Chassis
		try {
			leftChassisEncoder = new Encoder((int) config.get("CHASSIS_ENCODER_LEFT_A"),
					(int) config.get("CHASSIS_ENCODER_LEFT_B"),
					(boolean) config.get("CHASSIS_ENCODER_LEFT_REVERSE_DIRECTION"), CounterBase.EncodingType.k4X);

			rightChassisEncoder = new Encoder((int) config.get("CHASSIS_ENCODER_RIGHT_A"),
					(int) config.get("CHASSIS_ENCODER_RIGHT_B"),
					(boolean) config.get("CHASSIS_ENCODER_RIGHT_REVERSE_DIRECTION"), CounterBase.EncodingType.k4X);

		} catch (ConfigException e) {
			logger.severe(e);
		}
	}
}
