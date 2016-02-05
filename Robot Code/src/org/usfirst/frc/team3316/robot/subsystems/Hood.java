package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.hood.StopHood;
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
		setDefaultCommand(new StopHood());
	}

	/**
	 * Set the voltage for the motors of this subsystem. If the hood is past the
	 * allowed position (about to break itself) it allows movement only in one
	 * direction.
	 * 
	 * @par v - The voltage for the motors (between 1.0 to -1.0).
	 */
	public boolean setMotors(double v)
	{
		// Checks if the hood is able to move in a certain direction. We assume
		// forward is for moving up and negative for moving down.

		if (getAngle() < (double) config.get("hood_Pot_BottomThresh"))
		{
			v = Math.max(v, 0);
		}
		else if (getAngle() > (double) config.get("hood_Pot_TopThresh"))
		{
			v = Math.min(v, 0);
		}

		return super.setMotors(v);
	}

	/**
	 * @return The angle of the hood by the potentiometer.
	 */
	public double getAngle()
	{
		return hoodPot.get();
	}

}
