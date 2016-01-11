package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public abstract class DBugSubsystem extends Subsystem 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
    public abstract void initDefaultCommand();
}

