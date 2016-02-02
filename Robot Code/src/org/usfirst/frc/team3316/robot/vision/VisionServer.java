package org.usfirst.frc.team3316.robot.vision;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;


class VisionServer implements Runnable
{
	public static Map<String, Double> Data;
	public void run()
	{
		ServerSocket server = null;
		try
		{
			// A socket listening on port 8080 for connection.
			// Known leak.
			server = new ServerSocket(8080);

			System.out.println("wait for connection on port 8080");
			// The socket to the python side.
			VisionSocketReader worker = new VisionSocketReader();
			new Thread(worker).start();

			while (true)
			{
				Socket client = server.accept();
				worker.change(client);
				System.out.println("got connection on port 8080");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally {
			try
			{
				server.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
