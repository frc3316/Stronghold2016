package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class HoodJoysticks extends DBugCommand {

	private double speed;
	
	public HoodJoysticks() {
		requires(Robot.hood);
	}
	
	protected void init() {}

	protected void execute() {
		// When push joystick forward, the speed needs to be positive.
		speed = -Robot.joysticks.joystickOperator.getY(); //We want less power
		isFin = !Robot.hood.setMotors(speed);
	}

	protected boolean isFinished() {
		return isFin;
	}

	protected void fin() {
		Robot.hood.setMotors(0);
	}

	protected void interr() {
		fin();
	}

}
