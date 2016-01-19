package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class Drive extends Command 
{
	protected static Config config = Robot.config;
	protected static DBugLogger logger = Robot.logger;
	
	double left = 0, right = 0;
	
    public Drive () 
    {
       requires(Robot.chassis);
    }

    protected void initialize() {}

    /**
     * Subclass needs to give left and right a value at the end of the set method
     */
    protected abstract void set();
    
    protected void execute()
    {
    	set();
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() 
    {
    	Robot.chassis.set(0, 0);
    }

    protected void interrupted() 
    {
    	end();
    }
}
