package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.commands.DBugCommand;

public class ToggleCommand extends DBugCommand
{
	private static DBugCommand cmd1, cmd2;
	private static int toggle;

	public ToggleCommand(DBugCommand cmd1, DBugCommand cmd2)
	{
		toggle = 2;

		this.cmd1 = cmd1;
		this.cmd2 = cmd2;
	}

	protected void init()
	{
		if (toggle == 2)
		{
			toggle = 1;
			cmd1.start();
		}
		else
		{
			toggle = 2;
			cmd2.start();
		}
	}

	protected void execute()
	{}

	protected boolean isFinished()
	{
		return (!cmd1.isRunning() && !cmd2.isRunning());
	}

	protected void fin()
	{
		cmd1.cancel();
		cmd2.cancel();
	}

	protected void interr()
	{
		fin();
	}
}
