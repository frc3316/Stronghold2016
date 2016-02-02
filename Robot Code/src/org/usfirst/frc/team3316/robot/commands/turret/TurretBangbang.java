package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.Robot;

public class TurretBangbang extends DBugCommand {
	
	private double setPoint, onVoltage, offVoltage;
	
	public TurretBangbang() {
		requires(Robot.turret);
	}

	protected void init() {}

	protected void execute() {
		onVoltage = (double) config.get("turret_Bangbang_OnVoltage");
		offVoltage = (double) config.get("turret_Bangbang_OffVoltage");
		setPoint = (double) config.get("turret_Bangbang_SetPoint");
		
		if (Robot.turret.getAngle() <= setPoint) {
			Robot.turret.setMotors(onVoltage);
		}
		else {
			Robot.turret.setMotors(offVoltage);
		}
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
