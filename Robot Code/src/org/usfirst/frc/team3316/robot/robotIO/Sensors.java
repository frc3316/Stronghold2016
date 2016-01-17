/**
 * Le robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class Sensors
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	public AHRS ahrs;
	
	public Counter flywheelCounter;
	public DigitalInput hallEffect;

	public Sensors()
	{
		try
		{
			ahrs = new AHRS(SPI.Port.kMXP);
		}
		catch (RuntimeException ex)
		{
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
		
		hallEffect = new DigitalInput(0);
		
		flywheelCounter = new Counter(hallEffect);
		flywheelCounter.setDistancePerPulse(1.0 / 6.0); //6 bolts per round
	}
}
