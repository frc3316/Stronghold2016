package org.usfirst.frc.team3316.robot.commands.chassis.auton;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.subsystems.Chassis;
import org.usfirst.frc.team3316.robot.vision.AlignShooter;

public class DriveDistanceCamera extends DBugCommand
{
	private double dist, measuredDist, v;

	public DriveDistanceCamera(double dist)
	{
		requires(Robot.chassis);
		
		this.dist = dist;
	}

	protected void init()
	{
		v = (double) config.get("chassis_DriveDistanceCamera_Speed");
	}

	protected void execute()
	{
		if (AlignShooter.isObjectDetected())
		{
			measuredDist = AlignShooter.getDistanceFromTower();

			Robot.chassis.setMotors(v, v);
		}
	}

	protected boolean isFinished()
	{
		return !AlignShooter.isObjectDetected() || measuredDist < dist;
	}

	protected void fin()
	{
		Robot.chassis.setMotors(0, 0);
	}

	protected void interr()
	{
		fin();
	}
}
