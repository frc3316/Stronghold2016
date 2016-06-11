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
import org.usfirst.frc.team3316.robot.commands.hood.HoodPIDNoCamera;
import org.usfirst.frc.team3316.robot.commands.turret.TurretPID;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.CameraServer;
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
//			put("Flywheel speed", Math.abs(Robot.flywheel.getRate()));
//
//			put("Turret angle", Robot.turret.getAngle());
//			put("Hood angle", Robot.hood.getAngle());

			// Vision
			try
			{
//				put("Object detected", AlignShooter.isObjectDetected());
//				put("Turret vision angle", AlignShooter.getTowerAngle());
//				put("Vision distance", VisionServer.Data.get("DFC"));
//				put("Hood angle setpoint", AlignShooter.getHoodAngle());
//
//				put("Ready to shoot",
//						Robot.flywheel.isOnTarget() && Robot.hood.isOnTarget() && Robot.turret.isOnTarget()
//								&& Robot.intake.isReadyToTransfer() && AlignShooter.isObjectDetected());
			}
			catch (Exception e)
			{
				// logger.severe(e);
			}

			// For drivers
//			put("Ready to transfer", Robot.intake.isReadyToTransfer());
//			put("Flywheel on target", Robot.flywheel.isOnTarget());
//			put("Turret on target", Robot.turret.isOnTarget());
//			put("Hood on target", Robot.hood.isOnTarget());
//			put("Intake open", Robot.intake.isIntakeClose());
//
//			put("Is ball in", Robot.intake.isBallIn());
			put("On defense", Robot.chassis.isOnDefense());
			put("ON OMNI", Robot.chassis.areShortPistonsExtended());
//			put("Operator POV", Robot.joysticks.joystickOperator.getPOV());
//			put("Vision Connected", VisionServer.isConnected);
//			
//			// THE FOLLOWING INDICATORS ARE FOR THE ROBOT SHOWCASE. TODO: REMOVE THESE AFTER THE EVENT
//			// General
//			put("Compressor current", Robot.actuators.compressor.getCompressorCurrent());
//			put("PDP voltage", Robot.sensors.pdp.getVoltage()); // The battery voltage
			// Chassis
			put("Chassis left front motor voltage (between -1 and 1)", Robot.actuators.chassisLeft1SC.get()); // TODO: is that front?
			put("Chassis left back motor voltage (between -1 and 1)", Robot.actuators.chassisLeft2SC.get()); // TODO: is that back?
			put("Chassis right front motor voltage (between -1 and 1)", Robot.actuators.chassisRight1SC.get()); // TODO: is that front?
			put("Chassis right back motor voltage (between -1 and 1)", Robot.actuators.chassisRight2SC.get()); // TODO: is that back?
			put("Chassis long pistons extended", Robot.chassis.areLongPistonsExtended());
			put("Chassis pistons left extended", Robot.chassis.areShortPistonsLeftExtended());
			put("Chassis pistons right extended", Robot.chassis.areShortPistonsRightExtended());
//			put("Chassis pitch angle (measured by NavX)", Robot.chassis.getPitch());
			put("Chassis roll angle (measured by NavX)", Robot.chassis.getRoll());
			put("Chassis left encoder distance)", Robot.sensors.chassisLeftEncoder.getDistance());
			put("Chassis right encoder distance", Robot.sensors.chassisRightEncoder.getDistance()); // TODO: Add an encoder :)
			put("On defense", Robot.chassis.isOnDefense());
//			// Intake
//			put("Intake pistons open", Robot.intake.isIntakeOpen());
//			put("Intake motor voltage (between -1 and 1)", Robot.actuators.intakeSC.get());
//			put("Intake ball in (switch pushed)", Robot.intake.isBallIn());
//			// Transport
//			put("Transport voltage (between -1 and 1)", Robot.actuators.transportSC.get());
//			// Flywheel
//			put("Flywheel hall effect value", Robot.sensors.flywheelHE.get());
//			put("Flywheel bolts counter", Robot.sensors.flywheelCounter.get());
//			put("Flywheel motor voltage (between -1 and 1)", Robot.actuators.flywheelSC.get());
//			put("Flywheel speed (in RPS)", Robot.sensors.flywheelCounter.getRate());
//			// Turret
//			put("Turret motor voltage (between -1 and 1)", Robot.actuators.turretSC.get());
//			put("Turret potentiometer value", Robot.sensors.turretPot.get());
//			// Hood
////			put("Hood motor voltage (between -1 and 1)", Robot.actuators.hoodSC.get()); // The hood is temporarily disabled
//			// Climbing
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

	private CameraServer server;

	public SDB()
	{
		variablesInSDB = new Hashtable<String, Class<?>>();
		
		initLiveWindow();
		initSDB();
		// initDriverCamera();
	}

	/*
	 * private void initDriverCamera() { try { server =
	 * CameraServer.getInstance(); server.setQuality(50);
	 * server.startAutomaticCapture("cam0"); } catch (Exception e) {
	 * logger.severe(e); } }
	 */

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
		SmartDashboard.putData(new HoodPIDNoCamera());
		SmartDashboard.putData(new TurretPID());
		
		SmartDashboard.putData(new StartCompressor());
		SmartDashboard.putData(new StopCompressor());

		// Climbing
//		SmartDashboard.putData(new PullUp());
//		SmartDashboard.putData(new ReleaseDown());
//		putConfigVariableInSDB("climbing_UpSpeed");
//		putConfigVariableInSDB("climbing_DownSpeed");
//		SmartDashboard.putData(new JoystickWinchControl());
//		
//		SmartDashboard.putData(new SetHoodAngle());
//		SmartDashboard.putData(new SetTurretAngle());
		
		putConfigVariableInSDB("hood_PID_KP");
		putConfigVariableInSDB("hood_PID_KI");
		putConfigVariableInSDB("hood_PID_KD");
		
		logger.info("Finished initSDB()");
	}

	/**
	 * This method puts in the live window of the test mode all of the robot's
	 * actuators and sensors. It is disgusting.
	 */
	public void initLiveWindow()
	{
//		/*
//		 * Actuators
//		 */
//		// General
//		LiveWindow.addActuator("General", "compressor", Robot.actuators.compressor);
//		// Chassis
//		LiveWindow.addActuator("Chassis", "chassisLeft1SC", (LiveWindowSendable) Robot.actuators.chassisLeft1SC);
//		LiveWindow.addActuator("Chassis", "chassisLeft2SC", (LiveWindowSendable) Robot.actuators.chassisLeft2SC);
//		LiveWindow.addActuator("Chassis", "chassisRight1SC", (LiveWindowSendable) Robot.actuators.chassisRight1SC);
//		LiveWindow.addActuator("Chassis", "chassisRight2SC", (LiveWindowSendable) Robot.actuators.chassisRight2SC);
//		LiveWindow.addActuator("Chassis", "chassisLongPistons",
//				(LiveWindowSendable) Robot.actuators.chassisLongPistons);
//		LiveWindow.addActuator("Chassis", "chassisShortPistonsLeft",
//				(LiveWindowSendable) Robot.actuators.chassisShortPistonsLeft);
//		LiveWindow.addActuator("Chassis", "chassisShortPistonsRight",
//				(LiveWindowSendable) Robot.actuators.chassisShortPistonsRight);
//		// Intake
//		LiveWindow.addActuator("Intake", "intakeSolenoid", (LiveWindowSendable) Robot.actuators.intakeSolenoid);
//		LiveWindow.addActuator("Intake", "intakeSC", (LiveWindowSendable) Robot.actuators.intakeSC);
//		// Transport
//		LiveWindow.addActuator("Transport", "transportSC", (LiveWindowSendable) Robot.actuators.transportSC);
//		// Flywheel
//		LiveWindow.addActuator("Flywheel", "flywheelSC", (LiveWindowSendable) Robot.actuators.flywheelSC);
//		// Turret
//		LiveWindow.addActuator("Turret", "turretSC", (LiveWindowSendable) Robot.actuators.turretSC);
//		// Hood
//		LiveWindow.addActuator("Hood", "hoodSC", (LiveWindowSendable) Robot.actuators.hoodSC);
//		// Climbing
//		LiveWindow.addActuator("Climbing", "climbingSolenoid", (LiveWindowSendable) Robot.actuators.climbingSolenoid);
//		LiveWindow.addActuator("Climbing", "climbingMotorSC1", (LiveWindowSendable) Robot.actuators.climbingMotorSC1);
//		LiveWindow.addActuator("Climbing", "climbingMotorSC2", (LiveWindowSendable) Robot.actuators.climbingMotorSC2);
//		LiveWindow.addActuator("Climbing", "climbingMotorSC3", (LiveWindowSendable) Robot.actuators.climbingMotorSC3);
//		LiveWindow.addActuator("Climbing", "climbingMotorSC4", (LiveWindowSendable) Robot.actuators.climbingMotorSC4);
//		// Spare
//		LiveWindow.addActuator("Spare", "spareMotorSC", (LiveWindowSendable) Robot.actuators.spareMotorSC);
//
//		/*
//		 * Sensors
//		 */
//		// General
//		LiveWindow.addSensor("General", "pdp", (LiveWindowSendable) Robot.sensors.pdp);
//		// Chassis
//		LiveWindow.addSensor("Chassis", "navx", (LiveWindowSendable) Robot.sensors.navx);
//		LiveWindow.addSensor("Chassis", "chassisLeftEncoder", (LiveWindowSendable) Robot.sensors.chassisLeftEncoder);
//		LiveWindow.addSensor("Chassis", "chassisRighttEncoder", (LiveWindowSendable) Robot.sensors.chassisRightEncoder);
//		// Intake
//		LiveWindow.addSensor("Intake", "intakeLeftSwitch", (LiveWindowSendable) Robot.sensors.intakeSwitch);
//		// Flywheel
//		LiveWindow.addSensor("Flywheel", "flywheelCounter", (LiveWindowSendable) Robot.sensors.flywheelCounter);
//		LiveWindow.addSensor("Flywheel", "hallEffect", (LiveWindowSendable) Robot.sensors.flywheelHE);
//		// Turret
//		LiveWindow.addSensor("Turret", "turretPot", (LiveWindowSendable) Robot.sensors.turretPot);
//		// Hood
//		LiveWindow.addSensor("Hood", "hoodPot", (LiveWindowSendable) Robot.sensors.hoodPot);
//		// Climbing
//		LiveWindow.addSensor("Climbing", "climbingPot", (LiveWindowSendable) Robot.sensors.climbingPot);
//		LiveWindow.addSensor("Climbing", "climbingSwitch", (LiveWindowSendable) Robot.sensors.climbingSwitch);
//
//		logger.info("Finished initLiveWindow()");
	}
}