package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class for bundling stuff that we want in every command.
 */
public abstract class DBugCommand extends Command 
{
	protected static DBugLogger logger = Robot.logger;
	protected static Config config = Robot.config;
	
	protected boolean isFin = false; 
	//TODO: Eliminate the isFinished method by changing it here: return isFin. 

	public DBugCommand ()
	{
		logger.info(this.getName() + " instantiated");
	}
	
    protected final void initialize() 
    {
    	logger.fine(this.getName() + " initialize");
    	init();
    }
    
    /**
     * Same as Command.initialize()
     */
    protected abstract void init(); 

    protected abstract void execute();

    protected abstract boolean isFinished();

    protected final void end() 
    {
    	logger.fine(this.getName() + " end");
    	fin();
    }
    
    /**
     * Same as Command.end()
     */
    protected abstract void fin();

    protected final void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    	interr();
    }
    
    /**
     * Same as Command.interrupted()
     */
    protected abstract void interr();
}
