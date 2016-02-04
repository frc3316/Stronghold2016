/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.motion.MeasureKinematics;
import org.usfirst.frc.team3316.robot.commands.chassis.autonomous.DriveDistanceVES;
import org.usfirst.frc.team3316.robot.commands.chassis.autonomous.DriveDistanceVS;
import org.usfirst.frc.team3316.robot.commands.flywheel.BangbangFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.JoystickFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.PIDFlywheel;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.commands.intake.StopRoll;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB
{
	/*
	 * Runnable that periodically updates values from the robot into the
	 * SmartDashboard This is the place where all of the robot data should be
	 * displayed from
	 */
	private class UpdateSDBTask extends TimerTask
	{
		public UpdateSDBTask()
		{
			logger.info("Created UpdateSDBTask");
		}

		public void run()
		{
			/*
			 * Insert put methods here
			 */
			
			put("Left Speed", Robot.chassis.getLeftSpeed());
			put("Right Speed", Robot.chassis.getRightSpeed());
			
			put("Joystick Left", Robot.joysticks.joystickLeft.getY());
			put("Joystick Right", Robot.joysticks.joystickRight.getY());
		}

		private void put(String name, double d)
		{
			SmartDashboard.putNumber(name, d);
		}

		private void put(String name, int i)
		{
			SmartDashboard.putNumber(name, i);
		}

		private void put(String name, boolean b)
		{
			SmartDashboard.putBoolean(name, b);
		}

		private void put(String name, String s)
		{
			SmartDashboard.putString(name, s);
		}
	}

	DBugLogger logger = Robot.logger;
	Config config = Robot.config;

	private UpdateSDBTask updateSDBTask;

	private Hashtable<String, Class<?>> variablesInSDB;

	public SDB()
	{
		variablesInSDB = new Hashtable<String, Class<?>>();

		initSDB();
	}

	public void timerInit()
	{
		updateSDBTask = new UpdateSDBTask();
		Robot.timer.schedule(updateSDBTask, 0, 10);
	}

	/**
	 * Adds a certain key in the config to the SmartDashboard
	 * 
	 * @param key
	 *            the key required
	 * @return whether the value was put in the SmartDashboard
	 */
	public boolean putConfigVariableInSDB(String key)
	{
		Object value = config.get(key);
		if (value != null)
		{
			Class<?> type = value.getClass();

			boolean constant = Character.isUpperCase(key.codePointAt(0))
					&& Character.isUpperCase(key.codePointAt(key.length() - 1));

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
				logger.info("Added to SDB " + key + " of type " + type
						+ "and allows for its modification");
			}
			else
			{
				logger.info("Added to SDB " + key + " of type " + type
						+ "BUT DOES NOT ALLOW for its modification");
			}

			return true;
		}

		return false;
	}

	public Set<Entry<String, Class<?>>> getVariablesInSDB()
	{
		return variablesInSDB.entrySet();
	}

	private void initSDB()
	{
		SmartDashboard.putData(new UpdateVariablesInConfig()); // NEVER REMOVE
																// THIS COMMAND
		//PID stuff
		putConfigVariableInSDB("chassis_PIDRight_KP");
		putConfigVariableInSDB("chassis_PIDRight_KI");
		putConfigVariableInSDB("chassis_PIDRight_KD");
		putConfigVariableInSDB("chassis_PIDRight_KF");

		putConfigVariableInSDB("chassis_PIDLeft_KP");
		putConfigVariableInSDB("chassis_PIDLeft_KI");
		putConfigVariableInSDB("chassis_PIDLeft_KD");
		putConfigVariableInSDB("chassis_PIDLeft_KF");
		
		putConfigVariableInSDB("chassis_DriveDistance_KV");
		putConfigVariableInSDB("chassis_DriveDistance_KA");
		
		//Motion Planner stuff
		putConfigVariableInSDB("motionPlanner_MaxAccel");
		putConfigVariableInSDB("motionPlanner_MaxDecel");
		putConfigVariableInSDB("motionPlanner_MaxVelocity");
		
		//Drive distance stuff
		SmartDashboard.putData(new DriveDistanceVS(2));
		SmartDashboard.putData(new DriveDistanceVES(2));
		
		SmartDashboard.putData("Measure 0.5 sec", new MeasureKinematics(0.5));
		SmartDashboard.putData("Measure 1 sec", new MeasureKinematics(1));
		SmartDashboard.putData("Measure 1.5 sec", new MeasureKinematics(1.5));
		SmartDashboard.putData("Measure 2 sec", new MeasureKinematics(2));
		
		logger.info("Finished initSDB()");
	}
}