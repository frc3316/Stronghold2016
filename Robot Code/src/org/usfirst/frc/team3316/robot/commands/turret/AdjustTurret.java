package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class AdjustTurret extends DBugCommand
{
	
	protected void init()
	{}

	protected void execute()
	{
		Robot.turret.setTurretAdjusting(true);
	}

	protected boolean isFinished()
	{
		return Robot.turret.toAdjustTurret();
	}

	protected void fin()
	{}

	protected void interr()
	{}

}
