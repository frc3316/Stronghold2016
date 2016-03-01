package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.transport.TransportRollIn;

public class OpenIntakeTransport extends DBugCommand
{
	private DBugCommand openIntake, transportRollIn;

	private boolean openStarted;
	// This boolean is to prevent this command from ending in case that the open intake hasn't started yet and
	// this command's isFinished is invoked

	public OpenIntakeTransport()
	{
		openIntake = new OpenIntake();
		transportRollIn = new TransportRollIn();
	}

	protected void init()
	{
		openStarted = false;

		openIntake.start();
		transportRollIn.start();
	}

	protected void execute()
	{
		if (openIntake.isRunning())
		{
			openStarted = true;
		}
	}

	protected boolean isFinished()
	{
		return !openIntake.isRunning() && openStarted;
	}

	protected void fin()
	{
		openIntake.cancel();
		transportRollIn.cancel();
	}

	protected void interr()
	{
		fin();
	}
}
