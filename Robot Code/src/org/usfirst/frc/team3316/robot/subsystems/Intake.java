package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;

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
	private DigitalInput intakeLS, intakeRS;

	public Intake()
	{
		// Actuators
		intakeSolendoid = Robot.actuators.intakeSolenoid;
		intakeMotor = Robot.actuators.intakeMotor;

		// Sensors
		intakeLS = Robot.sensors.intakeLS;
		intakeRS = Robot.sensors.intakeRS;
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
		if (intakeLS.get() && intakeRS.get())
		{
			return true;
		}

		return false;
	}

	public boolean isBallOut()
	{
		return !isBallIn();
	}

}
