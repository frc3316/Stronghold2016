package org.usfirst.frc.team3316.robot.vision;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;

public class AlignShooter
{
	public static void align()
	{
		alignTurret();
		alignHood();
	}
	// hood_Angle_SetPoint, turret_Angle_SetPoint
	private static void alignTurret()
	{
		double towerAngle = VisionServer.Data.get("AA"); // AA = Azimuthal Angle
		double turretAngle = Robot.turret.getAngle();
		Robot.config.add("turret_Angle_SetPoint", turretAngle - towerAngle);
	}
	
	private static void alignHood()
	{
		double towerAngle = VisionServer.Data.get("PA"); // PA = Polar Angle
		double hoodAngle = Robot.turret.getAngle();
		Robot.config.add("turret_Angle_SetPoint", hoodAngle - towerAngle);
	}
}
