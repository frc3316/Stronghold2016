/**
 * Le robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

public class Sensors
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	public AHRS navX;

	public Sensors()
	{
		try
		{
			navX = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex)
		{
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
	}
}
