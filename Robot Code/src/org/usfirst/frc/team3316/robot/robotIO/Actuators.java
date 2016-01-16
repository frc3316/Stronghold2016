/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class Actuators 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	public Talon flywheel;
	
	public Actuators ()
	{
		flywheel = new Talon(0);
	}
}
