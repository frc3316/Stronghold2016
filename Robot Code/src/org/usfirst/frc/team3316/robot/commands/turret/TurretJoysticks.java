package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class TurretJoysticks extends DBugCommand {

	private double speed;
	
	public TurretJoysticks() {
		requires(Robot.turret);
	}
	
	protected void init() {}

	protected void execute() {
		speed = Robot.joysticks.joystickOperator.getX();
		
		Robot.turret.setMotors(speed);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void fin() {
		Robot.turret.setMotors(0);
	}

	protected void interr() {
		fin();
	}

}
