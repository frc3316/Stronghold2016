/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.chassis.ExtendOmni;
import org.usfirst.frc.team3316.robot.commands.chassis.RetractOmni;
import org.usfirst.frc.team3316.robot.commands.chassis.TankDrive;
import org.usfirst.frc.team3316.robot.commands.intake.StopRoll;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallIn;
import org.usfirst.frc.team3316.robot.commands.intake.WaitForBallOut;
import org.usfirst.frc.team3316.robot.commands.chassis.ToggleOmni;
import org.usfirst.frc.team3316.robot.commands.chassis.WaitForDefense;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.CrossDefense;
import org.usfirst.frc.team3316.robot.commands.chassis.auton.Direction;
import org.usfirst.frc.team3316.robot.commands.hood.HoodBangbang;
import org.usfirst.frc.team3316.robot.commands.hood.HoodJoysticks;
import org.usfirst.frc.team3316.robot.commands.hood.HoodPID;
import org.usfirst.frc.team3316.robot.commands.hood.StopHood;
import org.usfirst.frc.team3316.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team3316.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.commands.transport.BangbangTransport;
import org.usfirst.frc.team3316.robot.commands.turret.StopTurret;
import org.usfirst.frc.team3316.robot.commands.turret.TurretBangbang;
import org.usfirst.frc.team3316.robot.commands.turret.TurretJoysticks;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;
import org.usfirst.frc.team3316.robot.commands.climbing.ReleaseArmPiston;
import org.usfirst.frc.team3316.robot.commands.climbing.ReleaseDown;
import org.usfirst.frc.team3316.robot.commands.climbing.StopWinch;
import org.usfirst.frc.team3316.robot.commands.climbing.WaitForRung;
import org.usfirst.frc.team3316.robot.commands.climbing.JoystickWinchControl;
import org.usfirst.frc.team3316.robot.commands.climbing.PullUp;
import org.usfirst.frc.team3316.robot.commands.climbing.lockArmPiston;
import org.usfirst.frc.team3316.robot.commands.flywheel.BangbangFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.JoystickFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.PIDFlywheel;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.CrossingBackSequence;
import org.usfirst.frc.team3316.robot.sequences.CrossingForwardSequence;

import org.usfirst.frc.team3316.robot.vision.VisionServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3316.robot.sequences.ClimbingSequence;

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
			put("Intake Current", Robot.intake.getCurrent());
			
			put("Chassis Yaw", Robot.chassis.getYaw());
			put("Chassis Pitch", Robot.chassis.getPitch());
			put("isOnDefense", Robot.chassis.isOnDefense());
			put("Hood Angle", Robot.hood.getAngle());
			put("Turret Angle", Robot.turret.getAngle());
			try
			{
				put("DistanceFromCamera", VisionServer.Data.get("DistanceFromCamera"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
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
		 * Remove these after finishing testing on prototype
		 */		
		putConfigVariableInSDB("chassis_CrossDefense_Voltage");
		putConfigVariableInSDB("chassis_Defense_Pitch_Thresh");
		putConfigVariableInSDB("chassis_Defense_Roll_Thresh");
		putConfigVariableInSDB("chassis_CrossDefense_BrakeV");
		putConfigVariableInSDB("chassis_CrossBrake_Timeout");
		putConfigVariableInSDB("chassis_Defense_Angle_Timeout");
		putConfigVariableInSDB("chassis_CrossDefense_MinSpeed");
		putConfigVariableInSDB("chassis_CrossDefense_DownV");
		
		/*
		 * For testing
		 */
		SmartDashboard.putData(new CrossingForwardSequence());
		SmartDashboard.putData(new CrossingBackSequence());
		SmartDashboard.putData(new lockArmPiston());
		SmartDashboard.putData(new ExtendOmni());
		SmartDashboard.putData(new RetractOmni());
		SmartDashboard.putData(new ToggleOmni());
		SmartDashboard.putData(new WaitForDefense());
		SmartDashboard.putData(new JoystickWinchControl());
		SmartDashboard.putData(new lockArmPiston());
		SmartDashboard.putData(new PullUp());
		SmartDashboard.putData(new ReleaseArmPiston());
		SmartDashboard.putData(new ReleaseDown());
		SmartDashboard.putData(new StopWinch());
		SmartDashboard.putData(new WaitForRung());
		SmartDashboard.putData(new BangbangFlywheel());
		SmartDashboard.putData(new JoystickFlywheel());
		SmartDashboard.putData(new PIDFlywheel());
		SmartDashboard.putData(new HoodBangbang());
		SmartDashboard.putData(new HoodJoysticks());
		SmartDashboard.putData(new HoodPID());
		SmartDashboard.putData(new StopHood());
		SmartDashboard.putData(new CloseIntake());
		SmartDashboard.putData(new OpenIntake());
		SmartDashboard.putData("Intake RollIn", new RollIn());
		SmartDashboard.putData("Intake RollOut", new RollOut());
		SmartDashboard.putData("Intake StopRoll", new StopRoll());
		SmartDashboard.putData(new WaitForBallIn());
		SmartDashboard.putData(new WaitForBallOut());
		SmartDashboard.putData(new BangbangTransport());
		SmartDashboard.putData("Transport RollIn", new org.usfirst.frc.team3316.robot.commands.transport.RollIn());
		SmartDashboard.putData("Transport StopRoll", new org.usfirst.frc.team3316.robot.commands.transport.StopRoll());
		SmartDashboard.putData(new StopTurret());
		SmartDashboard.putData(new TurretBangbang());
		SmartDashboard.putData(new TurretBangbang());
		SmartDashboard.putData(new TurretJoysticks());
		SmartDashboard.putData(new TurretPID());
		
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

		putConfigVariableInSDB("climbing_UpSpeed");
		putConfigVariableInSDB("climbing_DownSpeed");

		// Climbing
		SmartDashboard.putData(new ClimbingSequence());
		SmartDashboard.putData(new lockArmPiston());
		SmartDashboard.putData(new ReleaseArmPiston());
		SmartDashboard.putData(new PullUp());
		SmartDashboard.putData(new ReleaseDown());
		SmartDashboard.putData(new JoystickWinchControl());

		logger.info("Finished initSDB()");
	}
}