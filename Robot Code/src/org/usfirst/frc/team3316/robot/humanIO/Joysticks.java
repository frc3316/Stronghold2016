/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.commands.intake.ToggleIntake;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Joysticks {
	/*
	 * Defines a button in a gamepad POV for an array of angles
	 */
	private class POVButton extends Button {
		Joystick m_joystick;
		int m_deg;

		public POVButton(Joystick joystick, int deg) {
			m_joystick = joystick;
			m_deg = deg;
		}

		public boolean get() {
			if (m_joystick.getPOV() == m_deg) {
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
	public Joysticks() {
		joystickLeft = new Joystick((int) Robot.config.get("JOYSTICK_LEFT"));
		joystickRight = new Joystick((int) Robot.config.get("JOYSTICK_RIGHT"));
		joystickOperator = new Joystick((int) Robot.config.get("JOYSTICK_OPERATOR"));
	}

	/**
	 * Initializes the joystick buttons. This is done separately because they
	 * usually require the subsystems to be already instantiated.
	 */
	public void initButtons() {
		// TODO: Remember! Add all the variables to config and SDB.
		// For setting new buttons use DBugJoystickButton, for setting their
		// config variables use the format: joysticks_CommandName.
		
		DBugJoystickButton rollInBtn = new DBugJoystickButton(joystickOperator, "joysticks_RollIn");
		DBugJoystickButton rollOutBtn = new DBugJoystickButton(joystickOperator, "joysticks_RollOut");
		DBugJoystickButton toggleIntakeBtn = new DBugJoystickButton(joystickOperator, "joysticks_IntakeToggle");

		rollInBtn.whileHeld(new RollIn());
		rollOutBtn.whileHeld(new RollOut());
		toggleIntakeBtn.whenPressed(new ToggleIntake());
	}
}
