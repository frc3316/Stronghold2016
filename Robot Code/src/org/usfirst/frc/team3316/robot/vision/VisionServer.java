package org.usfirst.frc.team3316.robot.vision;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class VisionServer implements Runnable {
	public static Map<String, Double> Data;
	
	public VisionServer() {
		
	}

	private Map<String, Double> parseLine(String s) {
		// Input e.g.: {"Var1":33.16,"Var2":22.12}
		Map<String, Double> data = new HashMap<String, Double>();

		String vars[] = s.split(",");
		for (String var : vars) {
			String parts[] = var.split(":", 2);

			String key = parts[0].substring(parts[0].indexOf('\'') + 1, parts[0].lastIndexOf('\'') - 1);

			double value = Double
					.parseDouble(parts[1].substring(parts[1].indexOf('\'') + 1, parts[1].lastIndexOf('\'') - 1));

			data.put(key, value);
		}

		return data;
	}

	public void run() {
		
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(8080);
		} catch (SocketException e) {
			System.err.println("Error with creating the UDP Socket.");
		}

		byte[] receiveData = new byte[1024];

		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				System.err.println("Error receiving data.");
			}
			
			String sentence = new String( receivePacket.getData() );
			VisionServer.Data = parseLine(sentence);
		}
		
	}
}
