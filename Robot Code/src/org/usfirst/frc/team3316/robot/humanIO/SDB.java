/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;

import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.StartCompressor;
import org.usfirst.frc.team3316.robot.commands.StopCompressor;
import org.usfirst.frc.team3316.robot.commands.chassis.CloseLongPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.CloseShortPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.OpenLongPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.OpenShortPistons;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;

import edu.wpi.first.wpilibj.CameraServer;
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
			// Vision
			put("Object detected", AlignShooter.isObjectDetected());
			put("Ready to shoot", Robot.flywheel.isOnTarget() && Robot.intake.isReadyToTransfer() && AlignShooter.isObjectDetected());
		
			// Chassis
			put("On defense", Robot.chassis.isOnDefense());
			put("On Omni", Robot.chassis.areShortPistonsExtended());
			put("Chassis left front motor voltage (between -1 and 1)", Robot.actuators.chassisLeft1SC.get()); // TODO: is that front?
			put("Chassis right front motor voltage (between -1 and 1)", Robot.actuators.chassisRight1SC.get()); // TODO: is that front?
			put("Chassis long pistons extended", Robot.chassis.areLongPistonsExtended());
			put("Chassis short pistons extended", Robot.chassis.areShortPistonsExtended());
			put("Chassis pitch angle (measured by NavX)", Robot.chassis.getPitch());
//			// Intake
			put("Is intake open", Robot.intake.isIntakeOpen());
			put("Is ball in", Robot.intake.isBallIn());
//			// Flywheel
			put("Flywheel hall effect value", Robot.sensors.flywheelHE.get());
			put("Flywheel counter value", Robot.sensors.flywheelCounter.get());
			put("Flywheel on target", Robot.flywheel.isOnTarget());
			put("Flywheel speed (in RPS)", Robot.sensors.flywheelCounter.getRate());
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
		SmartDashboard.putData(new UpdateVariablesInConfig()); // NEVER REMOVE
																// THIS COMMAND
		SmartDashboard.putData(new StartCompressor());
		SmartDashboard.putData(new StopCompressor());
		
		SmartDashboard.putData(new CloseShortPistons());
		SmartDashboard.putData(new OpenLongPistons());
		SmartDashboard.putData(new CloseLongPistons());
		SmartDashboard.putData(new OpenShortPistons());

		logger.info("Finished initSDB()");
	}

	/**
	 * This method puts in the live window of the test mode all of the robot's
	 * actuators and sensors. It is disgusting.
	 */
}