package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;

public class AutonTriggerShootingSequence extends DBugCommand
{
	private DBugCommandGroup triggerShootingSequence;
	
	public AutonTriggerShootingSequence ()
	{
		triggerShootingSequence = new TriggerShootingSequence();
	}

	protected void init()
	{
		setTimeout(3.0);
	}

	protected void execute()
	{
		triggerShootingSequence.start();
	}

	protected boolean isFinished()
	{
		return isTimedOut();
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
}
