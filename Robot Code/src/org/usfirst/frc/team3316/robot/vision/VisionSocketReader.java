package org.usfirst.frc.team3316.robot.vision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class VisionSocketReader implements Runnable
{
	private BufferedReader buffer;

	public void change(Socket s) throws IOException
	{
		s.setSoTimeout(3000);
		this.buffer = new BufferedReader(
				new InputStreamReader(s.getInputStream()));
	}

	private Map<String, Double> parseLine(String s)
	{
		// Input e.g.: {"Var1":33.16,"Var2":22.12}
		Map<String, Double> data = new HashMap<String, Double>();

		String vars[] = s.split(",");
		for (String var : vars)
		{
			String parts[] = var.split(":", 2);

			String key = parts[0].substring(parts[0].indexOf('\'') + 1,
					parts[0].lastIndexOf('\'') - 1);

			double value = Double
					.parseDouble(parts[1].substring(parts[1].indexOf('\'') + 1,
							parts[1].lastIndexOf('\'') - 1));

			data.put(key, value);
		}

		return data;
	}

	public void run()
	{
		while (true)
		{ // Run forever
			if (buffer != null)
			{
				String nextLine = null;
				// System.out.println("BEFORE");
				try
				{
					nextLine = buffer.readLine();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				// System.out.println("AFTER");

				if (nextLine != null)
				{
					VisionServer.Data = parseLine(nextLine);
				}
			}
			else
			{
				System.out.println("NO.");
			}

		}
	}
}
