package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class Drive extends Command 
{
	//TODO: Add commenting
	
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
    	Robot.chassis.setMotors(left, right);
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() 
    {
    	Robot.chassis.setMotors(0, 0);
    }

    protected void interrupted() 
    {
    	end();
    }
}
