package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedController;

public class Intake extends DBugSubsystem
{
	// Actuators
	private DoubleSolenoid intakeSolendoid;
	private SpeedController intakeMotor;

	// Sensors
	private DigitalInput intakeLeftSwitch, intakeRightSwitch;
	private AnalogPotentiometer intakePot;

	public Intake()
	{
		// Actuators
		intakeSolendoid = Robot.actuators.intakeSolenoid;
		intakeMotor = Robot.actuators.intakeMotor;

		// Sensors
		intakeLeftSwitch = Robot.sensors.intakeLeftSwitch;
		intakeRightSwitch = Robot.sensors.intakeRightSwitch;

		intakePot = Robot.sensors.intakePot;
	}

	public void initDefaultCommand()
	{
	}

	public void openIntake()
	{
		intakeSolendoid.set(Value.kForward);
	}

	public void closeIntake()
	{
		intakeSolendoid.set(Value.kReverse);
	}

	public void setMotor(double v)
	{
		intakeMotor.set(v);
	}

	public boolean isBallIn()
	{
		if ((intakeLeftSwitch.get() && intakeRightSwitch.get()))
		{
			return true;
		}

		return false;
	}

	public boolean isBallOut()
	{
		return !isBallIn();
	}

	public boolean isIntakeOpen()
	{
		/*
		 * try { return intakePot .get() >= (double)
		 * config.get("INTAKE_POT_HIGH_TRESH"); } catch (ConfigException e) {
		 * logger.severe(e); } return false;
		 */

		// Changed for prototype reasons, need to switch it back to pot input
		return intakeSolendoid.get() == Value.kForward;
	}

	public boolean isIntakeClose()
	{
		/*
		 * try { return intakePot .get() <= (double)
		 * config.get("INTAKE_POT_LOW_TRESH"); } catch (ConfigException e) {
		 * logger.severe(e); } return false;
		 */

		// Changed for prototype reasons, need to switch it back to pot input
		return intakeSolendoid.get() == Value.kReverse;
	}

}
