package org.usfirst.frc.team3316.robot.vision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class VisionSocketReader implements Runnable
{
	BufferedReader in;

	public synchronized void change(Socket s) throws IOException
	{
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}

	private Map<String, Double> parseLine(String s)
	{
		// Input e.g.: {"Var1":33.16,"Var2":22.12}
		Map<String, Double> data = new HashMap<String, Double>();
		
		String vars[] = s.split(",");
		for (String var : vars)
		{
			String parts[] = var.split(":");
			String key = parts[0].substring(parts[0].indexOf('"') + 1,parts[0].lastIndexOf('"') - 1);
			double value = Double.parseDouble(parts[1]);
			data.put(key, value);
		}
		
		return data;
	}

	public void run()
	{
		while (true)
		{ // Run forever <3
			if (in != null)
			{
				String nextLine = null;
				try
				{
					nextLine = in.readLine();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				if (nextLine != null)
				{
					VisionServer.Data = parseLine(nextLine);
				}
				
			}
		}
	}
}
