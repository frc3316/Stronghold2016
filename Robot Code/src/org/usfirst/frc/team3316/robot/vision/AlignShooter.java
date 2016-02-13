package org.usfirst.frc.team3316.robot.vision;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.Utils;

public class AlignShooter
{
	// hood_Angle_SetPoint, turret_Angle_SetPoint
	public static double alignTurret()
	{
		double towerAngle = VisionServer.Data.get("AA"); // AA = Azimuthal Angle
		double turretAngle = Robot.turret.getAngle();
		return (turretAngle - towerAngle);
	}

	public static double alignHood()
	{
		double distance = VisionServer.Data.get("DFC"); // DFC = Distance From
														// Camera
		double table[][] = new double[][] { { 0 }, { 0 } };
		return (Utils.valueInterpolation(distance, table));

	}
}
