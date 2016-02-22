package org.usfirst.frc.team3316.robot.commands.intake;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.transport.TransportRollIn;

public class OpenIntakeTransport extends DBugCommand
{
	private DBugCommand openIntake, transportRollIn;
	
	public OpenIntakeTransport() {
		openIntake = new OpenIntake();
		transportRollIn = new TransportRollIn();
	}
	
	protected void init()
	{
		openIntake.start();
		transportRollIn.start();
	}
	
	protected void execute()
	{}
	
	protected boolean isFinished() {
		return !openIntake.isRunning();
	}
	
	protected void fin() {
		openIntake.cancel();
		transportRollIn.cancel();
	}
	
	protected void interr()
	{
		fin();
	}
}
