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

	public boolean setMotors(double v)
	{
		// If the turret is able to move.
		if (getAngle() > (double) config.get("turret_Low_Thresh")
				&& getAngle() < (double) config.get("turret_High_Thresh")) {
			return super.setMotors(v);
		}
		
		super.setMotors(0);
		return false;
	}

	public double getAngle()
	{
		return turretPot.get();
	}

}
