package org.usfirst.frc.team3316.robot.commands.climbing;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoystickWinchControl extends DBugCommand
{
	public JoystickWinchControl ()
	{
		requires(Robot.climbing);
	}
	
	protected void init()
	{}

	protected void execute()
	{
		isFin = !Robot.climbing.setMotors(-Robot.joysticks.joystickOperator.getRawAxis(5)); // Coz flight
																							// simulators
		SmartDashboard.putNumber("Operator axis 5 value", -Robot.joysticks.joystickOperator.getRawAxis(5));
	}

	protected boolean isFinished()
	{
		return isFin;
	}

	protected void fin()
	{
		Robot.climbing.setMotors(0);
	}

	protected void interr()
	{
		fin();
	}
}
