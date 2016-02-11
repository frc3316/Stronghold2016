package org.usfirst.frc.team3316.robot.chassis.motion;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class SetConstantVoltage extends DBugCommand
{
	double voltage;
	
	public SetConstantVoltage ()
	{
		requires(Robot.chassis);
	}
	
	protected void init()
	{
		voltage = (double) config.get("chassis_SetConstantVoltage_Voltage");
	}

	protected void execute()
	{
		Robot.chassis.setMotors(voltage, voltage);
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
