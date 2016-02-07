package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Class for bundling stuff that we want in every command group.
 */
public abstract class DBugCommandGroup extends CommandGroup 
{
	protected static DBugLogger logger = Robot.logger;
	protected static Config config = Robot.config;
	
	//TODO: Think if we want to add isFin here too.
	
	protected final void initialize() 
    {
    	logger.fine(this.getName() + " initialize");
    	init();
    }
    
    /**
     * Same as Command.initialize()
     */
    protected void init() {}
    
    protected final void end() 
    {
    	logger.fine(this.getName() + " end");
    	fin();
    }
    
    /**
     * Same as Command.end()
     */
    protected void fin() {}

    protected final void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    	interr();
    }
    
    /**
     * Same as Command.interrupted()
     */
    protected void interr() {}
}
