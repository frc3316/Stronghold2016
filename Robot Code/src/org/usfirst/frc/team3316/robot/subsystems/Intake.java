package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake extends DBugSubsystemCC
{

	// Actuators
	private DoubleSolenoid intakeSolendoid;
	private DBugSpeedController intakeMotor;

	// Sensors
	private DigitalInput intakeLeftSwitch, intakeRightSwitch;

	public Intake()
	{
		// Actuators
		Robot.actuators.IntakeActuators();

		intakeSolendoid = Robot.actuators.intakeSolenoid;
		intakeMotor = Robot.actuators.intakeMotor;

		addSpeedController(intakeMotor);

		// Sensors
		Robot.sensors.IntakeSensors();

		intakeLeftSwitch = Robot.sensors.intakeLeftSwitch;
		intakeRightSwitch = Robot.sensors.intakeRightSwitch;
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
		return intakeSolendoid.get().equals(Value.kForward);
	}

	public boolean isIntakeClose()
	{
		return intakeSolendoid.get().equals(Value.kReverse);
	}
	
	public boolean isReadyToIntake()
	{
		// When the ball is out and the intake is open, returns true.
		return isBallOut() && isIntakeOpen();
	}
	
	public boolean isReadyToTransfer()
	{
		// When the ball is in and the intake is close, returns true.
		return isBallIn() && isIntakeClose();
	}

	public double getCurrent()
	{
		return intakeMotor.getCurrent();
	}

}
