/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.intake.commands.RollIn;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB 
{
	/*
	 * Runnable that periodically updates values from the robot into the SmartDashboard
	 * This is the place where all of the robot data should be displayed from
	 */
	private class UpdateSDBTask extends TimerTask
	{
		public UpdateSDBTask ()
		{
			logger.info("Created UpdateSDBTask");
		}
		
		public void run ()
		{
			/*
			 * Insert put methods here
			 */
			//navX
			put("navX Yaw Axis", Robot.sensors.navx.getYaw());
			
			System.out.println("Yaw:" + Robot.sensors.navx.getYaw());
			
			put("navX Roll Axis", Robot.sensors.navx.getRoll());
			put("navX Pitch Axis", Robot.sensors.navx.getPitch());
			put("navX Pressure", Robot.sensors.navx.getPressure());
			put("navX Barometric Pressure", Robot.sensors.navx.getBarometricPressure());
			put("navX Angle", Robot.sensors.navx.getAngle());
			put("navX Z Displacement", Robot.sensors.navx.getDisplacementZ());
			put("navX Z Acceleration", Robot.sensors.navx.getRawAccelZ());
			
			//Chassis
			put("isOnDefense", Robot.chassis.isOnDefense());
		}
		
		private void put (String name, double d)
	    {
	    	SmartDashboard.putNumber(name, d);
	    }
	    
	    private void put (String name, int i)
	    {
	    	SmartDashboard.putInt(name, i);
	    }
	    
	    private void put (String name, boolean b)
	    {
	    	SmartDashboard.putBoolean(name, b);
	    }
	    
	    private void put (String name, String s)
	    {
	    	SmartDashboard.putString(name, s);
	    }
	}
	
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private UpdateSDBTask updateSDBTask;
	
	private Hashtable <String, Class <?> > variablesInSDB;
	
	public SDB ()
	{
		variablesInSDB = new Hashtable <String, Class <?> > ();
		
		initSDB();
	}
	
	public void timerInit ()
	{
		updateSDBTask = new UpdateSDBTask();
		Robot.timer.schedule(updateSDBTask, 0, 100);
	}
	
	/**
	 * Adds a certain key in the config to the SmartDashboard
	 * @param key the key required
	 * @return whether the value was put in the SmartDashboard
	 */
	public boolean putConfigVariableInSDB (String key)
	{
		try
		{
			Object value = config.get(key);
			Class <?> type = value.getClass();
			
			boolean constant = Character.isUpperCase(key.codePointAt(0));
			
			if (type == Double.class)
			{
				SmartDashboard.putNumber(key, (double) value);
			}
			else if (type == Integer.class)
			{
				SmartDashboard.putNumber(key, (int) value);
			}
			else if (type == Boolean.class)
			{
				SmartDashboard.putBoolean(key, (boolean) value);
			}
			
			if (!constant)
			{
				variablesInSDB.put(key, type);
				logger.info("Added to SDB " + key + " of type " + type + 
						"and allows for its modification");
			}
			else
			{
				logger.info("Added to SDB " + key + " of type " + type + 
						"BUT DOES NOT ALLOW for its modification");
			}
			
			return true;
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
		return false;
	}
	
	public Set <Entry <String, Class <?> > > getVariablesInSDB ()
	{
		return variablesInSDB.entrySet();
	}
	
	private void initSDB ()
	{
		SmartDashboard.putData(new UpdateVariablesInConfig()); //NEVER REMOVE THIS COMMAND
		
		SmartDashboard.putData(new RollIn());
		
		logger.info("Finished initSDB()");
	}
}