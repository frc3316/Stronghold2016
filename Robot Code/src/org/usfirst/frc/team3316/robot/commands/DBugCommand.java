package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class DBugCommand extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;

    protected final void initialize() 
    {
    	logger.fine(this.getName() + " initialize");
    	init();
    }
    
    protected abstract void init(); 

    protected abstract void execute();

    protected abstract boolean isFinished();

    protected final void end() 
    {
    	logger.fine(this.getName() + " end");
    	fin();
    }
    
    protected abstract void fin();

    protected final void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    	interr();
    }
    
    protected abstract void interr();
}
