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

public class Sensors
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// Chassis
	public AHRS navx;

	// Intake
	public DigitalInput intakeLeftSwitch, intakeRightSwitch;
	public AnalogPotentiometer intakePot;

	// Transport
	public Encoder transportEncoder;

	public Sensors()
	{
		try
		{
			// Chassis
			try
			{
				navx = new AHRS(SPI.Port.kMXP);
			}
			catch (RuntimeException ex)
			{
				DriverStation.reportError(
						"Error instantiating navX MXP:  " + ex.getMessage(),
						true);
			}

			// Intake
			intakeLeftSwitch = new DigitalInput(
					(int) Robot.config.get("INTAKE_LEFT_SWITCH"));
			intakeRightSwitch = new DigitalInput(
					(int) Robot.config.get("INTAKE_RIGHT_SWITCH"));

			intakePot = new AnalogPotentiometer(
					(int) Robot.config.get("INTAKE_POT"),
					(double) Robot.config.get("INTAKE_POT_FULL_RANGE"),
					(double) Robot.config.get("INTAKE_POT_OFFSET"));

			// Transport
			transportEncoder = new Encoder(
					(int) config.get("TRANSPORT_ENCODER_A"),
					(int) config.get("TRANSPORT_ENCODER_B"),
					(boolean) config.get("TRANSPORT_ENCODER_REVERSE_DIRECTION"),
					CounterBase.EncodingType.k4X);

		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
