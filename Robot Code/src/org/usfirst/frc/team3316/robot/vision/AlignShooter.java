package org.usfirst.frc.team3316.robot.vision;

import org.omg.CORBA.portable.ValueInputStream;
import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.utils.Utils;

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
		double distance = VisionServer.Data.get("DFC"); // DFC = Distance From Camera
		// double hoodAngle = Robot.turret.getAngle();
		double table [][] = null;
		Robot.config.add("turret_Angle_SetPoint", Utils.valueInterpolation(distance, table));
	}
}
