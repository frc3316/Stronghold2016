package org.usfirst.frc.team3316.robot.flywheel.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class JoystickFlywheel extends DBugCommand
{	
	public JoystickFlywheel ()
	{
		requires(Robot.flywheel);
	}
	
	protected void init()
	{
		
	}
	
	protected void execute()
	{
		double v = Robot.joysticks.joystickOperator.getRawAxis(1);
		
		Robot.flywheel.setMotors(v);
	}
	
	protected boolean isFinished()
	{
		return false;
	}

	protected void fin()
	{
		Robot.flywheel.setMotors(0);
	}
	
	protected void interr()
	{	
		fin();
	}
}
