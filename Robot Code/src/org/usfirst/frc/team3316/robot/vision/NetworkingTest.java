package org.usfirst.frc.team3316.robot.vision;

public class NetworkingTest
{

	public static void main(String[] args) throws InterruptedException
	{
		VisionServer server = new VisionServer();
		new Thread(server).start();

		while (true)
		{
			if (VisionServer.Data != null)
			{
				System.out.println(VisionServer.Data);
			}
			else
			{
				//System.out.println("Is Null :O");
			}
			Thread.sleep(100);
		}
	}
}
