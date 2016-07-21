package org.usfirst.frc.team3316.robot.vision;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlignShooter
{
	private static DBugLogger logger;
	private static Config config;
	
	private static double [][] hoodTable;

	static
	{
		logger = Robot.logger;
		config = Robot.config;
		
		hoodTable = (double[][]) config.get("alignShooter_HoodTable");
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
		SmartDashboard.putNumber("DFC", distance); // TO REMOVE after testings
		
		return (Utils.valueInterpolation(distance, hoodTable));
	}
	
	public static double interpolateHoodAngle (double distance)
	{
		return (Utils.valueInterpolation(distance, hoodTable));
	}

	public static double getDistanceFromTower()
	{
		double distance = VisionServer.Data.get("DFC"); // DFC = Distance From
		// Camera
		return distance;
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
