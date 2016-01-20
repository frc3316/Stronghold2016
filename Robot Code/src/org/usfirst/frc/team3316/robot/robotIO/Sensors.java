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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import javafx.scene.chart.LineChart.SortingPolicy;

public class Sensors
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	// Chassis
	public AHRS navx;

	// Intake
	public DigitalInput intakeLS, intakeRS;

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
			intakeLS = new DigitalInput((int) Robot.config.get("INTAKE_LS"));
			intakeLS = new DigitalInput((int) Robot.config.get("INTAKE_RS"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
