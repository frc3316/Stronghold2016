package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.transport.TransportRollOut;

public class CloseIntakeTransport extends DBugCommand
{
	private DBugCommand closeIntake, transportRollOut;

	private boolean closeStarted;
	// This boolean is to prevent this command from ending in case that the close intake hasn't started yet
	// and this command's isFinished is invoked

	public CloseIntakeTransport()
	{
		closeIntake = new CloseIntake();
		transportRollOut = new TransportRollOut();
	}

	protected void init()
	{
		closeStarted = false;

		closeIntake.start();
		transportRollOut.start();
	}

	protected void execute()
	{
		if (closeIntake.isRunning())
		{
			closeStarted = true;
		}
	}

	protected boolean isFinished()
	{
		return !closeIntake.isRunning() && closeStarted;
	}

	protected void fin()
	{
		closeIntake.cancel();
		transportRollOut.cancel();
	}

	protected void interr()
	{
		fin();
	}
}
