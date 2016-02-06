/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.intake.RollIn;
import org.usfirst.frc.team3316.robot.commands.intake.RollOut;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.ClimbingSequence;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class Joysticks {
	/*
	 * Defines a button in a gamepad POV for an array of angles
	 */
	private class POVButton extends Button {
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

	/* private class ToggleButton extends Button {
		Command m_command1, m_command2;
		Joystick m_joystick;
		int m_port;

		public ToggleButton(Command command1, Command command2, Joystick joystick, int port)
		{
			m_command1 = command1;
			m_command2 = command2;
			m_joystick = joystick;
			m_port = port;
		} 

		public boolean get()
		{
			return m_joystick.getRawButton(m_port);
		}
	} */

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
		//JoystickButton emergencyClimbButton = new JoystickButton(joystickOperator, 4);
		JoystickButton climbButton = new JoystickButton(joystickRight, 1);
		
		climbButton.whileHeld(new DBugToggleCommand(new RollIn(), new RollOut()));

		//climbButton.whenPressed(new ClimbingSequence());
		// emergencyClimbButton.whenPressed();
	}
}
