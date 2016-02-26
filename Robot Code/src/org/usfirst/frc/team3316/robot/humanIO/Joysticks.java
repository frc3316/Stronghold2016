/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.climbing.PullUp;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollIn;
import org.usfirst.frc.team3316.robot.commands.intake.IntakeRollOut;
import org.usfirst.frc.team3316.robot.commands.intake.ToggleIntake;
import org.usfirst.frc.team3316.robot.commands.chassis.CloseLongPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.CloseShortPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.OpenLongPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.OpenShortPistons;
import org.usfirst.frc.team3316.robot.commands.chassis.ToggleOmni;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.ClimbingSequence;
import org.usfirst.frc.team3316.robot.sequences.CollectBall;
import org.usfirst.frc.team3316.robot.sequences.EjectBall;
import org.usfirst.frc.team3316.robot.sequences.TriggerShootingSequence;
import org.usfirst.frc.team3316.robot.sequences.WarmUpShooterSequence;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Joysticks
{
	/*
	 * Defines a button in a gamepad POV for an array of angles
	 */
	private class POVButton extends Button
	{
		Joystick m_joystick;
		int m_deg;

		public POVButton(Joystick joystick, int deg)
		{
			m_joystick = joystick;
			m_deg = deg;
		}

		public boolean get()
		{
			if (m_joystick.getPOV() == m_deg)
			{
				return true;
			}
			return false;
		}
	}

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	public Joystick joystickLeft, joystickRight, joystickOperator;

	/**
	 * Initializes the joysticks.
	 */
	public Joysticks()
	{
		joystickLeft = new Joystick((int) Robot.config.get("JOYSTICK_LEFT"));
		joystickRight = new Joystick((int) Robot.config.get("JOYSTICK_RIGHT"));
		joystickOperator = new Joystick((int) Robot.config.get("JOYSTICK_OPERATOR"));
	}

	/**
	 * Initializes the joystick buttons. This is done separately because they
	 * usually require the subsystems to be already instantiated.
	 */
	public void initButtons()
	{
		// Chassis
		DBugJoystickButton toggleOmniBtn = new DBugJoystickButton(joystickRight, "button_Toggle_Omni");
		toggleOmniBtn.whenPressed(new ToggleOmni());
		
		// Intake
		DBugJoystickButton collectBallBtn = new DBugJoystickButton(joystickOperator, "button_Collect_Ball");
		collectBallBtn.whenPressed(new CollectBall());
		DBugJoystickButton ejectBallBtn = new DBugJoystickButton(joystickOperator, "button_Eject_Ball");
		ejectBallBtn.whenPressed(new EjectBall());
		
		DBugJoystickButton rollInBtn = new DBugJoystickButton(joystickOperator, "button_Roll_In");
		rollInBtn.whileHeld(new IntakeRollIn());
		DBugJoystickButton rollOutBtn = new DBugJoystickButton(joystickOperator, "button_Roll_Out");
		rollOutBtn.whileHeld(new IntakeRollOut());
		
		DBugJoystickButton toggleIntakeBtn = new DBugJoystickButton(joystickOperator, "button_Intake_Toggle");
		toggleIntakeBtn.whenPressed(new ToggleIntake());
		
		// Shooter
		DBugJoystickButton prepareToShootBtn = new DBugJoystickButton(joystickOperator, "button_Warm_Up_Shooter");
		prepareToShootBtn.whileHeld(new WarmUpShooterSequence());
		
		DBugJoystickButton shootingTriggerBtn = new DBugJoystickButton(joystickOperator, "button_Shooting_Trigger");
		shootingTriggerBtn.whenPressed(new TriggerShootingSequence());
	}
}
