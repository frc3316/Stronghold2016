package org.usfirst.frc.team3316.robot.vision;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.Utils;

public class AlignShooter
{
	private static DBugLogger logger;
	
	static
	{
		logger = Robot.logger;
	}
	
	
	public static double getTowerAngle()
	{
		double towerAngle = VisionServer.Data.get("AA"); // AA = Azimuthal Angle
		logger.finest("Someone asked for turret angle. Tower angle: " + towerAngle);
		return (towerAngle);
	}

	/**
	 * This returns the DFC
	 */
	public static double getHoodAngle()
	{
		double distance = VisionServer.Data.get("DFC"); // DFC = Distance From
														// Camera
//		double table[][] = new double[][] { { 0 }, { 0 } };
//		return (Utils.valueInterpolation(distance, table));
		
		return distance;
	}
	public static boolean isObjectDetected()
	{
		return VisionServer.Data.get("IOD") == 1.0;
	}
}
