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
import org.usfirst.frc.team3316.robot.commands.flywheel.BangbangFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.JoystickFlywheel;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import org.usfirst.frc.team3316.robot.vision.VisionServer;

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
			put("Chassis Yaw", Robot.chassis.getYaw());
			put("Chassis Pitch", Robot.chassis.getPitch());
			put("isOnDefense", Robot.chassis.isOnDefense());

			put("Hood Angle", Robot.hood.getAngle());
			put("Turret Angle", Robot.turret.getAngle());
			
			put("Flywheel speed", Robot.flywheel.getRate());
			put("Flywheel HE", Robot.sensors.flywheelHE.get());
			put("Flywheel SC voltage", Robot.actuators.flywheelMotor.getVoltage());
			put("Flywheel Current", Robot.actuators.flywheelMotor.getCurrent());
			
			put("Joystick Y", Robot.joysticks.joystickOperator.getY());
			
			try
			{
				put("DistanceFromCamera", VisionServer.Data.get("DistanceFromCamera"));
			}
			catch (Exception e)
			{
				//e.printStackTrace();
				put("DistanceFromCamera", "null");
			}
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
		initLiveWindow();
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
				logger.info("Added to SDB " + key + " of type " + type + " and allows for its modification");
			}
			else
			{
				logger.info("Added to SDB " + key + " of type " + type + " BUT DOES NOT ALLOW for its modification");
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
		SmartDashboard.putData(new UpdateVariablesInConfig()); // NEVER REMOVE THIS COMMAND
		
		/*
		 * For testing
		 */
		
		// Flywheel
		putConfigVariableInSDB("flywheel_Bangbang_Setpoint");
		putConfigVariableInSDB("flywheel_Bangbang_OnVoltage");
		putConfigVariableInSDB("flywheel_Bangbang_OffVoltage");
		
		SmartDashboard.putData(new BangbangFlywheel());
		SmartDashboard.putData(new JoystickFlywheel());
		
		// Hood
		putConfigVariableInSDB("hood_PID_KP");
		putConfigVariableInSDB("hood_PID_KI");
		putConfigVariableInSDB("hood_PID_KD");

		putConfigVariableInSDB("hood_Bangbang_OnVoltage");
		putConfigVariableInSDB("hood_Bangbang_OffVoltage");
		
		putConfigVariableInSDB("hood_Angle_SetPoint");

		putConfigVariableInSDB("hood_Pot_BottomThresh");
		putConfigVariableInSDB("hood_Pot_TopThresh");
		
		SmartDashboard.putData(new HoodBangbang());
		SmartDashboard.putData(new HoodPID());
		SmartDashboard.putData(new HoodJoysticks());
		SmartDashboard.putData("Hood ZeroAngle", new org.usfirst.frc.team3316.robot.commands.hood.ZeroAngle());
		
		// Turret
		SmartDashboard.putData("Turret ZeroAngle", new org.usfirst.frc.team3316.robot.commands.turret.ZeroAngle());

		logger.info("Finished initSDB()");
	}

	/**
	 * This method puts in the live window of the test mode all of the robot's
	 * actuators and sensors. It is disgusting.
	 */
	public void initLiveWindow()
	{
		/*
		 * Actuators
		 */
	}
}