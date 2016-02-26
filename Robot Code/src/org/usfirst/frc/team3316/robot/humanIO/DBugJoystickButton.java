package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class is using for changing the button number, via the config, while the code is running.
 * @author D-Bug
 *
 */
public class DBugJoystickButton extends Button {
	  GenericHID m_joystick;
	  String m_configButtonNumber;

	  /**
	   * Create a joystick button for triggering commands
	   *$
	   * @param joystick The GenericHID object that has the button (e.g. Joystick,
	   *        KinectStick, etc)
	   * @param configButtonNumber Config variable of joystick button number.
	   */
	  public DBugJoystickButton(GenericHID joystick, String configButtonNumber) {
	    m_joystick = joystick;
	    m_configButtonNumber = configButtonNumber;
	  }

	  /**
	   * Gets the value of the joystick button
	   *$
	   * @return The value of the joystick button
	   */
	  public boolean get() {
	    return m_joystick.getRawButton((int) Robot.config.get(m_configButtonNumber));
	  }

}