package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Hood extends DBugSubsystemCC
{
	private DBugSpeedController hoodMotor;
	private AnalogPotentiometer hoodPot;

	public Hood()
	{
		hoodMotor = Robot.actuators.hoodMotor;
		addSpeedController(hoodMotor);

		hoodPot = Robot.sensors.hoodPot;
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
		// If the hood is able to move.
		if (getAngle() > (double) config.get("hood_Pot_LowThresh")
				&& getAngle() < (double) config.get("hood_Pot_HighThresh")) {
			return super.setMotors(v);
		}
		
		super.setMotors(0);
		return false;
	}

	/**
	 * @return The angle of the hood by the potentiometer.
	 */
	public double getAngle()
	{
		return hoodPot.get();
	}

}
