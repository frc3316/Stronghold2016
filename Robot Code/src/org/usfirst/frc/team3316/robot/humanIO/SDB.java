/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.hood.HoodBangbang;
import org.usfirst.frc.team3316.robot.commands.hood.HoodJoysticks;
import org.usfirst.frc.team3316.robot.commands.hood.HoodPID;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.commands.intake.StopRoll;
import org.usfirst.frc.team3316.robot.commands.turret.TurretBangbang;
import org.usfirst.frc.team3316.robot.commands.turret.TurretJoysticks;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;
import org.usfirst.frc.team3316.robot.config.Config;
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
			
			put("Hood Angle", Robot.hood.getAngle());
			put("Turret Angle", Robot.turret.getAngle());
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
		Object value = config.get(key);
			if(value != null) {
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

		return false;
	}
	
	public Set <Entry <String, Class <?> > > getVariablesInSDB ()
	{
		return variablesInSDB.entrySet();
	}
	
	private void initSDB ()
	{
		SmartDashboard.putData(new UpdateVariablesInConfig()); //NEVER REMOVE THIS COMMAND

		/*
		 * Remove these after finishing testing on prototype
		 */
		
		putConfigVariableInSDB("hood_Pot_LowThresh");
		putConfigVariableInSDB("hood_Pot_HighThresh");	
		
		putConfigVariableInSDB("turret_Pot_LowThresh");
		putConfigVariableInSDB("turret_Pot_HighThresh");	
		
		// Hood
		SmartDashboard.putData(new HoodBangbang());
		SmartDashboard.putData(new HoodJoysticks());
		SmartDashboard.putData(new HoodPID());

		// Turret
		SmartDashboard.putData(new TurretBangbang());
		SmartDashboard.putData(new TurretJoysticks());
		SmartDashboard.putData(new TurretPID());
		
		logger.info("Finished initSDB()");
	}
}