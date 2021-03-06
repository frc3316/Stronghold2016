package org.usfirst.frc.team3316.robot.commands.flywheel;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;

/**
 * Command that lets a driver set a voltage to the flywheel using the operator joystick.
 * @author D-Bug
 *
 */
public class JoystickFlywheel extends DBugCommand
{

	public JoystickFlywheel()
	{
		requires(Robot.flywheel);
	}

	protected void init()
	{
	}

	protected void execute()
	{
		double v = -Robot.joysticks.joystickOperator.getRawAxis(1);

		isFin = !Robot.flywheel.setMotors(v);
	}

	protected boolean isFinished()
	{
		return isFin;
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
