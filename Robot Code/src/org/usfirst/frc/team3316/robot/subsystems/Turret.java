package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.turret.TurretJoysticks;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Turret extends DBugSubsystemCC
{
	private DBugSpeedController turretMotor;
	private AnalogPotentiometer turretPot;
	private double potOffset;

	public Turret()
	{
		// Actuators
		Robot.actuators.TurretActuators();
		
		turretMotor = Robot.actuators.turretMotor;
		addSpeedController(turretMotor);

		// Sensors
		Robot.sensors.TurretSensors();
		
		turretPot = Robot.sensors.turretPot;
		potOffset = (double) config.get("turret_Pot_Offset");
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new TurretJoysticks());
	}

	/**
	 * Set the voltage for the motors of this subsystem. If the turret is past
	 * the allowed position (about to break itself) it allows movement only in
	 * one direction.
	 * 
	 * @par v - The voltage for the motors (between 1.0 to -1.0).
	 */
	public boolean setMotors(double v)
	{
		// If the turret is able to move. We assume positive is for CW movement
		// and negative is for CCW movement.

		if (getAngle() > (double) config.get("turret_Pot_RightThresh"))
		{
			logger.severe("Turret trying to move more right than right thresh. Aborting.");
			v = Math.min(v, 0);
		}
		else if (getAngle() < (double) config.get("turret_Pot_LeftThresh"))
		{
			logger.severe("Hood trying to move more left than left thresh. Aborting.");
			v = Math.max(v, 0);
		}

		return super.setMotors(v);
	}

	/**
	 * @return The angle of the turret by the potentiometer.
	 */
	public double getAngle()
	{
		return turretPot.get() + potOffset;
	}
	
	public void setAngle(double angle) {
		potOffset = (angle - turretPot.get());
		logger.fine("The offset of the turret is set to be " + potOffset + ". UPDATE THIS VALUE IN THE CONFIG.");
	}

}
