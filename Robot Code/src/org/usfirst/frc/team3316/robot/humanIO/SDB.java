/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.ResetSensors;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossBangbang;
import org.usfirst.frc.team3316.robot.commands.chassis.CrossDefense;
import org.usfirst.frc.team3316.robot.commands.chassis.TankDrive;
import org.usfirst.frc.team3316.robot.commands.flywheel.BangbangFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.JoystickFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.PIDFlywheel;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.commands.intake.StopRoll;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.CrossingSequence;

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
			
			put("Intake Current", Robot.intake.getCurrent());
			
			put("Chassis Yaw", Robot.chassis.getYaw());
			put("Chassis Pitch", Robot.chassis.getPitch());
			put("isOnDefense", Robot.chassis.isOnDefense());
			put("Chassis_Motor1_Voltage", Robot.actuators.leftChassis1.getVoltage());
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
		Robot.timer.schedule(updateSDBTask, 0, 10);
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
		SmartDashboard.putData(new TankDrive());

		/*
		 * Remove these after finishing testing on prototype
		 */		
		putConfigVariableInSDB("chassis_CrossDefense_Pid_Kp");
		putConfigVariableInSDB("chassis_CrossDefense_Pid_Ki");
		putConfigVariableInSDB("chassis_CrossDefense_Pid_Kd");
		putConfigVariableInSDB("chassis_CrossDefense_Pid_Setpoint");
		putConfigVariableInSDB("chassis_CrossDefense_Voltage");
		putConfigVariableInSDB("chassis_Defense_Pitch_Thresh");
		putConfigVariableInSDB("chassis_Defense_Roll_Thresh");
		putConfigVariableInSDB("chassis_CrossDefense_Back_Voltage");
		putConfigVariableInSDB("chassis_CrossBack_Timeout");
		putConfigVariableInSDB("chassis_Defense_Angle_Timeout");
		putConfigVariableInSDB("chassis_CrossDefense_MinSpeed");
		putConfigVariableInSDB("chassis_CrossDefense_MaxSpeed");
		
		/*
		 * For testing
		 */
		SmartDashboard.putData(new CrossDefense(false));
		SmartDashboard.putData(new CrossBangbang());
		
		SmartDashboard.putData(new CrossingSequence());
		
		SmartDashboard.putData(new ResetSensors());
		
		logger.info("Finished initSDB()");
	}
}