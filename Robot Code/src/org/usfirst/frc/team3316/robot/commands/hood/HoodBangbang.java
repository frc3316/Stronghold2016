package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.Robot;

public class HoodBangbang extends DBugCommand {
	
	private double setPoint, onVoltage, offVoltage;
	
	public HoodBangbang() {
		requires(Robot.hood);
	}

	protected void init() {}

	protected void execute() {
		onVoltage = (double) config.get("hood_Bangbang_OnVoltage");
		offVoltage = (double) config.get("hood_Bangbang_OffVoltage");
		setPoint = (double) config.get("hood_Angle_SetPoint");
		
		if (Robot.hood.getAngle() <= setPoint) {
			isFin = !Robot.hood.setMotors(onVoltage);
		}
		else {
			isFin = !Robot.hood.setMotors(offVoltage);
		}
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
