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
		return (towerAngle);
	}

	/**
	 * This returns the DFC
	 */
	public static double getHoodAngle()
	{
		double distance = VisionServer.Data.get("DFC"); // DFC = Distance From
														// Camera
		double table[][] = new double[][] { { 205.0, 255.0, 310.0, 357.0, 417.0, 475.0, 510.0, 560.0 }, 
											{ 38.7, 50.3, 53.1, 52.0, 52.0, 49.7, 45.6, 40.0 } };
		return (Utils.valueInterpolation(distance, table));
	}
	public static boolean isObjectDetected()
	{
		try
		{
			return VisionServer.Data.get("IOD") == 1.0;
		}
		catch (Exception e)
		{
//			logger.severe(e);
			return false;
		}
	}
}
