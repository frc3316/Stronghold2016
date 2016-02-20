package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.transport.TransportRollOut;

public class CloseIntakeTransport extends DBugCommand
{
	private DBugCommand closeIntake, transportRollOut;
	
	public CloseIntakeTransport() {
		closeIntake = new CloseIntake();
		transportRollOut = new TransportRollOut();
	}
	
	protected void init()
	{
		closeIntake.start();
		transportRollOut.start();
	}
	
	protected void execute()
	{}
	
	protected boolean isFinished() {
		return !closeIntake.isRunning();
	}
	
	protected void fin() {
		closeIntake.cancel();
		transportRollOut.cancel();
	}
	
	protected void interr()
	{
		fin();
	}
}
