/**
 * Le robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.SerialPort;

public class Sensors 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	public IMUAdvanced navx;
	SerialPort serial_port;
	
	public Sensors ()
	{
		serial_port = new SerialPort(57600, SerialPort.Port.kMXP);
		navx = new IMUAdvanced(serial_port);
	}
}
