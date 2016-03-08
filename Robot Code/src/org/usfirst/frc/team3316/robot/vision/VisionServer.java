package org.usfirst.frc.team3316.robot.vision;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

public class VisionServer implements Runnable
{
	public static Map<String, Double> Data;
	private static DBugLogger logger;
	
	public static boolean isConnected = false;

	static
	{
		Data = new HashMap<String, Double>();
		logger = Robot.logger;
	}

	private Map<String, Double> parseLine(String s)
	{
		// Input e.g.: {'Var1':'33.16','Var2':'22.12'}
		Map<String, Double> data = new HashMap<String, Double>();

//		logger.finest(s);
		
		String vars[] = s.split(",");
		
		for (String var : vars)
		{
//			logger.info(var);
			
			String parts[] = var.split(":", 0);

			String key = parts[0].substring(parts[0].indexOf('\'') + 1,
					parts[0].lastIndexOf('\''));

//			logger.finest("Key: " + key);
			
			String valueString = parts[1].substring(parts[1].indexOf('\'') + 1,
					parts[1].lastIndexOf('\''));
			
			if (valueString.contains("}"))
			{
				valueString = valueString.substring(0, valueString.indexOf('}') - 2);
			}
			
			double value = Double.parseDouble(valueString);
			
//			logger.finest("Value: " + value);

			data.put(key, value);
		}

//		logger.fine("data: " + data.toString());
		
		return data;
	}
	
	private long lastTime = 0;

	public void run()
	{
		DatagramSocket serverSocket = null;
		try
		{
			serverSocket = new DatagramSocket(8000);
		}
		catch (SocketException e)
		{
			System.err.println("Error with creating the UDP Socket.");
		}

		byte[] receiveData = new byte[70];

		while (true)
		{
			if (lastTime == 0)
			{
				lastTime = System.currentTimeMillis();
			}
			
//			logger.finest("Time difference from last while: " + (System.currentTimeMillis() - lastTime));
			lastTime = System.currentTimeMillis();
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			try
			{
				serverSocket.setSoTimeout(100);
				serverSocket.receive(receivePacket);

				logger.finest("Received packet");
				
				isConnected = true;
				
				String sentence = new String(receivePacket.getData());
//				logger.finest("Packet data length: " + receivePacket.getLength());
				VisionServer.Data = parseLine(sentence);
				
//				logger.finest("Parsed line");
			}
			catch (Exception e)
			{
				logger.severe("Vision server couldn't receive a packet");
				isConnected = false;
//				logger.severe(e);
			}
		}
	}
}
