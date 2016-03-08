
package org.usfirst.frc.team3316.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks;
import org.usfirst.frc.team3316.robot.humanIO.SDB;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.robotIO.Actuators;
import org.usfirst.frc.team3316.robot.robotIO.Sensors;
import org.usfirst.frc.team3316.robot.sequences.AutonomousSequence;
import org.usfirst.frc.team3316.robot.sequences.AutonomousShootingSequence;
import org.usfirst.frc.team3316.robot.subsystems.Chassis;
import org.usfirst.frc.team3316.robot.subsystems.Climbing;
import org.usfirst.frc.team3316.robot.subsystems.Flywheel;
import org.usfirst.frc.team3316.robot.subsystems.Hood;
import org.usfirst.frc.team3316.robot.subsystems.Intake;
import org.usfirst.frc.team3316.robot.subsystems.Transport;
import org.usfirst.frc.team3316.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	public static Config config;
	public static DBugLogger logger;
	public static Timer timer;

	/*
	 * Human IO
	 */
	public static Joysticks joysticks;
	public static SDB sdb;
	/*
	 * Robot IO
	 */
	public static Actuators actuators;
	public static Sensors sensors;
	/*
	 * Subsystems
	 */
	public static Chassis chassis;
	public static Intake intake;
	public static Transport transport;
	public static Flywheel flywheel;
	public static Turret turret;
	public static Hood hood;
	public static Climbing climbing;

	Command autonomousCommand;
	SendableChooser autonChooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit()
	{
		/*
		 * Above all else
		 */
		try
		{
		logger = new DBugLogger();
		timer = new Timer();
		config = new Config();

		/*
		 * Human IO (that does not require subsystems)
		 */
		joysticks = new Joysticks();

		/*
		 * Robot IO
		 */
		actuators = new Actuators();
		sensors = new Sensors();

		Robot.actuators.GeneralActuators();
		Robot.sensors.GeneralSensors();
		Robot.sensors.VisionSensors();

		/*
		 * Subsystems
		 */
		chassis = new Chassis();
		intake = new Intake();
		transport = new Transport();
		flywheel = new Flywheel();
		turret = new Turret();
		hood = new Hood();
		climbing = new Climbing();

		/*
		 * Human IO (that requires subsystems)
		 */
		joysticks.initButtons();

		sdb = new SDB();

		/*
		 * Timer
		 */
		sdb.timerInit();

		/*
		 * La verite (turns out that apostrophes makes errors)
		 */
		logger.info(returnTheTruth());
		
		/*
		 * Choosers
		 */
		autonChooser = new SendableChooser();
		autonChooser.addDefault("Empty Auton", new DBugCommand()
		{
			protected boolean isFinished()
			{
				return false;
			}
			
			protected void interr()
			{
			}
			
			protected void init()
			{
			}
			
			protected void fin()
			{
			}
			
			protected void execute()
			{
			}
		});
		autonChooser.addObject("Cross and reach", new AutonomousSequence());
		autonChooser.addObject("Cross, shoot and reach", new AutonomousShootingSequence());
		SmartDashboard.putData("Auton Chooser", autonChooser);
		}
		catch (Exception e)
		{
			logger.severe(e);
		}
	}

	public void disabledInit()
	{

	}

	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	public void autonomousInit()
	{
		if ((autonChooser.getSelected()) != null)
			((Command) autonChooser.getSelected()).start();
	}

	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}

	public void teleopInit()
	{
		if ((autonChooser.getSelected()) != null)
			((Command) autonChooser.getSelected()).cancel();
	}

	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}

	public void testIniwt()
	{
	}

	public void testPeriodic()
	{
		LiveWindow.run();
	}

	private String returnTheTruth()
	{
		return "Vita is the Melech!!";
	}
}
