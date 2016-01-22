/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

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
		public POVButton (Joystick joystick, int deg)
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
	
	public Joysticks ()
	{
		try
		{
			joystickLeft = new Joystick((int) Robot.config.get("JOYSTICK_LEFT"));
			joystickRight = new Joystick((int) Robot.config.get("JOYSTICK_RIGHT"));
			joystickOperator = new Joystick((int) Robot.config.get("JOYSTICK_OPERATOR"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	public void initButtons ()
	{
	
	}
}
