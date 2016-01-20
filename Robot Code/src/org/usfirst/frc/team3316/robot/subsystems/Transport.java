package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.SpeedController;

public class Transport extends DBugSubsystem
{

	private SpeedController transportMotor;
	
	public Transport() {
		transportMotor = Robot.actuators.transportMotor;
	}
	
	public void initDefaultCommand()
	{}
	
	public void setMotor(double v) {
		transportMotor.set(v);
	}

}
