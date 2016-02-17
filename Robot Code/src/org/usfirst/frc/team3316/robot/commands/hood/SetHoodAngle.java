package org.usfirst.frc.team3316.robot.commands.hood;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class SetHoodAngle extends DBugCommand
{
	public SetHoodAngle ()
	{
		setRunWhenDisabled(true);
	}
	
	protected void init()
	{
		Robot.hood.setAngle((double) config.get("hood_SetHoodAngle_Angle"));
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
