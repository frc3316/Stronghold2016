package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.commands.DBugCommandGroup;

public class AutonTriggerShootingSequence extends DBugCommand
{
	private DBugCommandGroup triggerShootingSequence;
	private int triggerStarted;
	private int counter = 0;
	
	public AutonTriggerShootingSequence ()
	{
		triggerShootingSequence = new TriggerShootingSequence();
		triggerStarted = 0;
	}

	protected void init()
	{
		triggerStarted = 0;
		counter = 0;
		
	}

	protected void execute()
	{
		if (!triggerShootingSequence.isRunning())
		{
			triggerShootingSequence.start();
			counter++;
		}
		else
		{
			triggerStarted++;
		}
	}

	protected boolean isFinished()
	{
		return (triggerStarted > 2 && !triggerShootingSequence.isRunning()) || counter > 50;
	}

	protected void fin()
	{
	}

	protected void interr()
	{
	}
}
