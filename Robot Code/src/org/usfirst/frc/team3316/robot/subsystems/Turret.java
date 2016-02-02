package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Turret extends DBugSubsystemCC
{
	private DBugSpeedController turretMotor;
	private AnalogPotentiometer turretPot;

	public Turret()
	{
		turretMotor = Robot.actuators.turretMotor;
		addSpeedController(turretMotor);

		turretPot = Robot.sensors.turretPot;
	}

	public void initDefaultCommand()
	{
	}

	/**
	 * Set the voltage for the motors of this subsystem.
	 * @par v - The voltage for the motors (between 1.0 to -1.0).
	 */
	public boolean setMotors(double v)
	{
		// If the turret is able to move.
		if (getAngle() > (double) config.get("turret_Pot_LowThresh")
				&& getAngle() < (double) config.get("turret_Pot_HighThresh")) {
			return super.setMotors(v);
		}
		
		super.setMotors(0);
		return false;
	}

	/**
	 * @return The angle of the turret by the potentiometer.
	 */
	public double getAngle()
	{
		return turretPot.get();
	}

}
