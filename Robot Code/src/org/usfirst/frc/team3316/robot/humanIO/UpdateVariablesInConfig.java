package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Updates all the variables that are in the SmartDashboard
 */
public class UpdateVariablesInConfig extends Command 
{
	Config config = Robot.config;
	
    public UpdateVariablesInConfig() 
    {
    	setRunWhenDisabled(true);
    }

    protected void initialize()
    {
    	/*
    	 * Iterates over all of the entries in the map and replaces their value in the config from the SDB
    	 */
    	Set <Entry <String, Class <?> > > variables = Robot.sdb.getVariablesInSDB();
    	
    	for (Map.Entry<String, Class <?> > entry : variables)
    	{
    		if (entry.getValue() == Double.class)
    		{
    			config.add(entry.getKey(), (double) SmartDashboard.getNumber(entry.getKey()));
    		}
    		else if (entry.getValue() == Integer.class)
    		{
    			config.add(entry.getKey(), (int) SmartDashboard.getNumber(entry.getKey()));
    		}
    		else if (entry.getValue() == Boolean.class)
    		{
    			config.add(entry.getKey(), (boolean) SmartDashboard.getBoolean(entry.getKey()));
    		}
    	}
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() {}
    
    protected void interrupted() {}
}
