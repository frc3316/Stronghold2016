package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.climbing.StopWinch;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climbing extends DBugSubsystemCC {

	// Sensors
	private AnalogPotentiometer climbingPot;
	private DigitalInput climbingSwitch;

	// Actuators
	private DBugSpeedController climbingMotor1, climbingMotor2, climbingMotor3, climbingMotor4;

	private DoubleSolenoid climbingSolenoid;

	public Climbing()
	{
		// Sensors
		climbingPot = Robot.sensors.climbingPot;

		climbingSwitch = Robot.sensors.climbingSwitch;

		// Actuators
		climbingSolenoid = Robot.actuators.climbingSolenoid;

		climbingMotor1 = Robot.actuators.climbingMotor1;
		climbingMotor2 = Robot.actuators.climbingMotor2;
		climbingMotor3 = Robot.actuators.climbingMotor3;
		climbingMotor4 = Robot.actuators.climbingMotor4;

		addSpeedController(climbingMotor1);
		addSpeedController(climbingMotor2);
		addSpeedController(climbingMotor3);
		addSpeedController(climbingMotor4);
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new StopWinch());
	}

	public boolean setMotors(double v)
	{
		if (getAngle() < (double) config.get("climbing_Pot_LowThresh"))
		{
			logger.severe("Someone is trying to break climbing pot. Aborting");
			v = Math.max(v, 0);
		} else if (getAngle() > (double) config.get("climbing_Pot_HighThresh"))
		{
			logger.severe("Someone is trying to break climbing pot. Aborting");
			v = Math.min(v, 0);
		}

		return super.setMotors(v);
	}

	public double getAngle()
	{
		return climbingPot.get();
	}

	public void lockArmPiston()
	{
		climbingSolenoid.set(Value.kForward);
	}

	public void releaseArmPiston()
	{
		climbingSolenoid.set(Value.kReverse);
	}

	public boolean isOnRung()
	{
		return climbingSwitch.get();
	}

	public boolean isNotOnRung()
	{
		return !isOnRung();
	}
}
