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
		speed = 0.5 * Robot.joysticks.joystickOperator.getRawAxis(4); //We want less power
		
		isFin = !Robot.turret.setMotors(speed);
	}

	protected boolean isFinished() {
		return isFin;
	}

	protected void fin() {
		Robot.turret.setMotors(0);
	}

	protected void interr() {
		fin();
	}

}
