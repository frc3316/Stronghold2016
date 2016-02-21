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

	static
	{
		Data = new HashMap<String, Double>();
		logger = Robot.logger;
	}

	private Map<String, Double> parseLine(String s)
	{
		// Input e.g.: {'Var1':'33.16','Var2':'22.12'}
		Map<String, Double> data = new HashMap<String, Double>();

		String vars[] = s.split(",");
		for (String var : vars)
		{
			String parts[] = var.split(":", 4);

			String key = parts[0].substring(parts[0].indexOf('\'') + 1, parts[0].lastIndexOf('\''));

			double value = Double
					.parseDouble(parts[1].substring(parts[1].indexOf('\'') + 1, parts[1].lastIndexOf('\'')));

			logger.finest("Parsing vision data. Key: " + key + ", Value: " + value);
			
			data.put(key, value);
		}

		return data;
	}

	public void run()
	{
		DatagramSocket serverSocket = null;
		try
		{
			serverSocket = new DatagramSocket(8080);
		}
		catch (SocketException e)
		{
			System.err.println("Error with creating the UDP Socket.");
		}

		byte[] receiveData = new byte[1024];

		while (true)
		{
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try
			{
				logger.finest("Vision server is trying to receive a packet");
				serverSocket.setSoTimeout(10000);
				serverSocket.receive(receivePacket);
				logger.finest("Vision server received a packet");
				
				String sentence = new String(receivePacket.getData());
				VisionServer.Data = parseLine(sentence);
			}
			catch (Exception e)
			{
				logger.severe("Vision server couldn't receive a packet");
				logger.severe(e);
			}
		}
	}
}
