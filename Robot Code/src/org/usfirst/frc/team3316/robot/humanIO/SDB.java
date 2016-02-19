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
import org.usfirst.frc.team3316.robot.commands.chassis.ExtendOmni;
import org.usfirst.frc.team3316.robot.commands.chassis.OpenLongPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.OpenShortPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.RetractOmni;
import org.usfirst.frc.team3316.robot.commands.chassis.WaitForDefense;
import org.usfirst.frc.team3316.robot.commands.hood.HoodBangbang;
import org.usfirst.frc.team3316.robot.commands.hood.HoodJoysticks;
import org.usfirst.frc.team3316.robot.commands.hood.HoodPID;
import org.usfirst.frc.team3316.robot.commands.hood.SetHoodAngle;
import org.usfirst.frc.team3316.robot.commands.turret.SetTurretAngle;
import org.usfirst.frc.team3316.robot.commands.turret.TurretJoysticks;
import org.usfirst.frc.team3316.robot.commands.flywheel.BangbangFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.FlywheelPID;
import org.usfirst.frc.team3316.robot.commands.flywheel.JoystickFlywheel;
import org.usfirst.frc.team3316.robot.commands.flywheel.WarmShooter;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import org.usfirst.frc.team3316.robot.vision.VisionServer;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
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
			put("Hood Angle", Robot.hood.getAngle());
			put("Hood current", Robot.actuators.hoodMotor.getCurrent());
			
			put("Turret Angle", Robot.turret.getAngle());
			put("Turret current", Robot.actuators.turretMotor.getCurrent());
			
			put("Flywheel speed", Robot.flywheel.getRate());
			put("Flywheel HE", Robot.sensors.flywheelHE.get());
			put("Flywheel Current", Robot.actuators.flywheelMotor.getCurrent());
			put("Flywheel Motor Power Sum", Robot.flywheel.getPowerSum());
			
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
		
		SmartDashboard.putData(new StartCompressor());
		SmartDashboard.putData(new StopCompressor());
		
		putConfigVariableInSDB("hood_SetHoodAngle_Angle");
		putConfigVariableInSDB("turret_SetTurretAngle_Angle");
		
		SmartDashboard.putData(new SetHoodAngle());
		SmartDashboard.putData(new SetTurretAngle());
		
		/*
		 * For testing
		 */
		
		// Flywheel
		putConfigVariableInSDB("flywheel_Bangbang_Setpoint");
		putConfigVariableInSDB("flywheel_Bangbang_OnVoltage");
		putConfigVariableInSDB("flywheel_Bangbang_OffVoltage");
		
		SmartDashboard.putData(new BangbangFlywheel());
		SmartDashboard.putData(new JoystickFlywheel());

		putConfigVariableInSDB("flywheel_PID_KP");
		putConfigVariableInSDB("flywheel_PID_KI");
		putConfigVariableInSDB("flywheel_PID_KD");
		putConfigVariableInSDB("flywheel_PID_KF");
		
		putConfigVariableInSDB("flywheel_PID_Setpoint");
		
		SmartDashboard.putData(new FlywheelPID());
		
		putConfigVariableInSDB("flywheel_AccelerateFlywheel_Timeout");
		SmartDashboard.putData(new WarmShooter());
		
		// Hood
		putConfigVariableInSDB("hood_PID_KP");
		putConfigVariableInSDB("hood_PID_KI");
		putConfigVariableInSDB("hood_PID_KD");

		putConfigVariableInSDB("hood_Bangbang_OnVoltage");
		putConfigVariableInSDB("hood_Bangbang_OffVoltage");
		
		putConfigVariableInSDB("hood_Angle_SetPoint");
		
		SmartDashboard.putData(new HoodBangbang());
		SmartDashboard.putData(new HoodPID());
		SmartDashboard.putData(new HoodJoysticks());
		
		// Turret
		SmartDashboard.putData(new TurretJoysticks());
		
		putConfigVariableInSDB("turret_Pot_LeftThresh");
		putConfigVariableInSDB("turret_Pot_RightThresh");
		
		putConfigVariableInSDB("hood_Pot_BottomThresh");
		putConfigVariableInSDB("hood_Pot_TopThresh");

		/*
		 * Remove these after finishing testing on prototype
		 */
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
		// General
		LiveWindow.addActuator("General", "compressor", Robot.actuators.compressor);
		// Chassis
		LiveWindow.addActuator("Chassis", "chassisLeft1SC", (LiveWindowSendable) Robot.actuators.chassisLeft1SC);
		LiveWindow.addActuator("Chassis", "chassisLeft2SC", (LiveWindowSendable) Robot.actuators.chassisLeft2SC);
		LiveWindow.addActuator("Chassis", "chassisRight1SC", (LiveWindowSendable) Robot.actuators.chassisRight1SC);
		LiveWindow.addActuator("Chassis", "chassisRight2SC", (LiveWindowSendable) Robot.actuators.chassisRight2SC);
		LiveWindow.addActuator("Chassis", "chassisLongPistons", (LiveWindowSendable) Robot.actuators.chassisLongPistons);
		LiveWindow.addActuator("Chassis", "chassisShortPistonsLeft", (LiveWindowSendable) Robot.actuators.chassisShortPistonsLeft);
		LiveWindow.addActuator("Chassis", "chassisShortPistonsRight", (LiveWindowSendable) Robot.actuators.chassisShortPistonsRight);
		// Intake
		LiveWindow.addActuator("Intake", "intakeSolenoid", (LiveWindowSendable) Robot.actuators.intakeSolenoid);
		LiveWindow.addActuator("Intake", "intakeSC", (LiveWindowSendable) Robot.actuators.intakeSC);
		// Transport
		LiveWindow.addActuator("Transport", "transportSC", (LiveWindowSendable) Robot.actuators.transportSC);
		// Flywheel
		LiveWindow.addActuator("Flywheel", "flywheelSC", (LiveWindowSendable) Robot.actuators.flywheelSC);
		// Turret
		LiveWindow.addActuator("Turret", "turretSC", (LiveWindowSendable) Robot.actuators.turretSC);
		// Hood
		LiveWindow.addActuator("Hood", "hoodSC", (LiveWindowSendable) Robot.actuators.hoodSC);
		// Climbing
		LiveWindow.addActuator("Climbing", "climbingSolenoid", (LiveWindowSendable) Robot.actuators.climbingSolenoid);
		LiveWindow.addActuator("Climbing", "climbingMotorSC1", (LiveWindowSendable) Robot.actuators.climbingMotorSC1);
		LiveWindow.addActuator("Climbing", "climbingMotorSC2", (LiveWindowSendable) Robot.actuators.climbingMotorSC2);
		LiveWindow.addActuator("Climbing", "climbingMotorSC3", (LiveWindowSendable) Robot.actuators.climbingMotorSC3);
		LiveWindow.addActuator("Climbing", "climbingMotorSC4", (LiveWindowSendable) Robot.actuators.climbingMotorSC4);
		// Spare
		LiveWindow.addActuator("Spare", "spareMotorSC", (LiveWindowSendable) Robot.actuators.spareMotorSC);

		/*
		 * Sensors
		 */
		// General
		LiveWindow.addSensor("General", "pdp", (LiveWindowSendable) Robot.sensors.pdp);
		// Chassis
		LiveWindow.addSensor("Chassis", "navx", (LiveWindowSendable) Robot.sensors.navx);
		LiveWindow.addSensor("Chassis", "chassisLeftEncoder", (LiveWindowSendable) Robot.sensors.chassisLeftEncoder);
		LiveWindow.addSensor("Chassis", "chassisRighttEncoder", (LiveWindowSendable) Robot.sensors.chassisRightEncoder);
		LiveWindow.addSensor("Chassis", "chassisHELeftFront", (LiveWindowSendable) Robot.sensors.chassisHELeftFront);
		LiveWindow.addSensor("Chassis", "chassisHELeftBack", (LiveWindowSendable) Robot.sensors.chassisHELeftBack);
		LiveWindow.addSensor("Chassis", "chassisHERightFront", (LiveWindowSendable) Robot.sensors.chassisHERightFront);
		LiveWindow.addSensor("Chassis", "chassisHERightBack", (LiveWindowSendable) Robot.sensors.chassisHERightBack);
		// Intake
		LiveWindow.addSensor("Intake", "intakeLeftSwitch", (LiveWindowSendable) Robot.sensors.intakeLeftSwitch);
		LiveWindow.addSensor("Intake", "intakeRightSwitch", (LiveWindowSendable) Robot.sensors.intakeRightSwitch);
		LiveWindow.addSensor("Intake", "intakePot", (LiveWindowSendable) Robot.sensors.intakePot);
		// Transport
		LiveWindow.addSensor("Transport", "transportEncoder", (LiveWindowSendable) Robot.sensors.transportCounter);
		// Flywheel
		LiveWindow.addSensor("Flywheel", "flywheelCounter", (LiveWindowSendable) Robot.sensors.flywheelCounter);
		LiveWindow.addSensor("Flywheel", "hallEffect", (LiveWindowSendable) Robot.sensors.flywheelHE);
		// Turret
		LiveWindow.addSensor("Turret", "turretPot", (LiveWindowSendable) Robot.sensors.turretPot);
		// Hood
		LiveWindow.addSensor("Hood", "hoodPot", (LiveWindowSendable) Robot.sensors.hoodPot);
		// Climbing
		LiveWindow.addSensor("Climbing", "climbingPot", (LiveWindowSendable) Robot.sensors.climbingPot);
		LiveWindow.addSensor("Climbing", "climbingSwitch", (LiveWindowSendable) Robot.sensors.climbingSwitch);
		
		logger.info("Finished initLiveWindow()");
	}
}