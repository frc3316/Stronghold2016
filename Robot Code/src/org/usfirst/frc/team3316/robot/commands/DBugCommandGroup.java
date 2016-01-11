package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public abstract class DBugCommandGroup extends CommandGroup 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	protected final void initialize() 
    {
    	logger.fine(this.getName() + " initialize");
    	init();
    }
    
    protected void init() {}
    
    protected final void end() 
    {
    	logger.fine(this.getName() + " end");
    	fin();
    }
    
    protected void fin() {}

    protected final void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    	interr();
    }
    
    protected void interr() {}
}
