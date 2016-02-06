package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class CrossBangbang extends DBugCommand
{
	
	public CrossBangbang() {
		requires(Robot.chassis);
	}

	private double onVoltage, offVoltage;
	protected void init()
	{
		offVoltage = (double) config.get("chassis_Cross_Bangbang_offVoltage");
		onVoltage = (double) config.get("chassis_Cross_Bangbang_onVoltage");
	}

	protected void execute()
	{
		if (Robot.chassis.isOnDefense())  {
			Robot.chassis.setMotors(onVoltage, onVoltage);
		}
		else {
			Robot.chassis.setMotors(offVoltage, offVoltage);
		}
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
		Robot.chassis.setMotors(0, 0);
	}

	protected void interr()
	{
		fin();
	}

}
