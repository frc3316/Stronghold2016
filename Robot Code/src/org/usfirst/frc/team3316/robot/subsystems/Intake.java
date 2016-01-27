package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.robotIO.DBugSpeedController;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends DBugSubsystem
{
	// Actuators
	private DoubleSolenoid intakeSolendoid;
	private DBugSpeedController intakeMotor;

	// Sensors
	private DigitalInput intakeLS, intakeRS, intakeTS, intakeBS;
	private AnalogPotentiometer intakePot;

	public Intake()
	{
		// Actuators
		intakeSolendoid = Robot.actuators.intakeSolenoid;
		intakeMotor = Robot.actuators.intakeMotor;

		addSpeedController(intakeMotor);

		// Sensors
		intakeLS = Robot.sensors.intakeLS;
		intakeRS = Robot.sensors.intakeRS;
		intakeTS = Robot.sensors.intakeTS;
		intakeBS = Robot.sensors.intakeBS;
		intakePot = Robot.sensors.intakePot;
		
		SmartDashboard.putNumber("Max Current", 0.0);
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

	public boolean setMotor(double v)
	{
		return intakeMotor.setMotor(v);
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

	public boolean isIntakeOpen()
	{
		// TODO: Update when sensors are final
		/*
		 * return intakeBS.get() && intakePot .get() >= (double)
		 * config.get("INTAKE_POT_HIGH_TRESH");
		 */
		return intakeBS.get();
	}

	public boolean isIntakeClose()
	{
		// TODO: Update when sensors are final
		/*
		 * return intakeTS.get() && intakePot .get() <= (double)
		 * config.get("INTAKE_POT_LOW_TRESH");
		 */
		return intakeTS.get();
	}

}
