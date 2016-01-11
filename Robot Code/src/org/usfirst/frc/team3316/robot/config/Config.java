package org.usfirst.frc.team3316.robot.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

public class Config 
{
	DBugLogger logger = Robot.logger;
	
	public boolean robotA; //true if it's robot A, false if it's robot B
	
	/**
	 * Exception that is raised when a certain key is not found in config
	 */
	public class ConfigException extends Exception
	{
		private static final long serialVersionUID = -658181374612523772L;
		
		public ConfigException (String key)
		{
			super(key);
		}
	}
	
	private static Hashtable <String, Object> variables;
	private static Hashtable <String, Object> constants;
	
	public Config ()
	{
		determineRobotA();
		deserializationInit();	
	}
	
	/*
	 * Reads the file on the roborio that says whether it is robot A or B
	 * Default is Robot B
	 */
	private void determineRobotA ()
	{
		String line;
		BufferedReader in;
		
		try 
		{
			in = new BufferedReader(new FileReader("/home/lvuser/RobotName/robot.txt"));
			line = in.readLine();
			
			if (line.equals("Robot A"))
			{
				robotA = true;
				logger.info(" This is Robot A");
			}
			else
			{
				robotA = false;
				logger.info(" This is Robot B");
			}
		} 
		catch (FileNotFoundException e) 
		{
			logger.severe(e);
			System.exit(1);
		} 
		catch (IOException e) 
		{
			logger.severe(e);
		}
	}
	
	public void add (String key, Object value)
	{
		addToVariables(key, value);
	}
	
	/**
	 * Returns the value attached to a requested key
	 * @param key the key to look for
	 * @return returns the corresponding value
	 * @throws ConfigException if the key does not exist
	 */
	public Object get (String key) throws ConfigException
	{
		if (constants.containsKey(key))
		{
			return constants.get(key);
		}
		else if (variables.containsKey(key))
		{
			return variables.get(key);
		}
		else
		{
			throw new ConfigException(key);
		}
	}
	
	private void addToVariables (String key, Object value)
	{
		if (variables.containsKey(key))
		{
			variables.replace(key, value);
			logger.info("replaced " + key + " in variables hashtable");
		}
		else
		{
			variables.put(key, value);
			logger.info("added " + key + " in variables hashtable");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void deserializationInit () 
	{
		String configPath;
		
		if (robotA)
		{
			configPath = "/home/lvuser/config/configFileA.ser";
		}
		else
		{
			configPath = "/home/lvuser/config/configFileB.ser";
		}
		
		try 
		{
			FileInputStream in = new FileInputStream(configPath);
			ObjectInputStream input = new ObjectInputStream(in);
			
			constants = (Hashtable <String, Object>) input.readObject();
			variables = (Hashtable <String, Object>) input.readObject();
			
			Set <Entry <String, Object> > set;
			
			set = constants.entrySet();
			logger.info(" Logging Constants");
			for (Entry <String, Object> entry : set)
			{
				logger.info(" Key = " + entry.getKey() + " Value = " + entry.getValue());
			}
			
			set = variables.entrySet();
			logger.info(" Logging Variables");
			for (Entry <String, Object> entry : set)
			{
				logger.info(" Key = " + entry.getKey() + " Value = " + entry.getValue());
			}
			
			input.close();
		} 
		catch (Exception e) 
		{
			logger.severe(e);
		}
	}
}
