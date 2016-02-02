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
				for(String key : VisionServer.Data.keySet()) {
					System.out.print("Key: " + key + "; Val: " + VisionServer.Data.get(key) + ",");
				}
				System.out.println();
			}
			else
			{
				System.out.println("Is Null :O");
			}
			Thread.sleep(1000);
		}
	}
}
