package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class SetTurretAngle extends DBugCommand
{
	protected void init()
	{
		Robot.hood.setAngle((double) config.get("turret_Pot_Offset"));
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
}
