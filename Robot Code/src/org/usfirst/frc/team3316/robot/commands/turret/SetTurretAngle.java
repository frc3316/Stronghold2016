package org.usfirst.frc.team3316.robot.commands.turret;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class SetTurretAngle extends DBugCommand
{
	public SetTurretAngle ()
	{
		setRunWhenDisabled(true);
	}
	
	protected void init()
	{
//		Robot.turret.setAngle((double) config.get("turret_SetTurretAngle_A?ngle"));
		
		logger.info("Current turret angle: " + Robot.turret.getAngle()); 
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
