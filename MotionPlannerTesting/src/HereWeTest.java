import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HereWeTest
{
	public static void main(String[] args)
	{
		MotionPlanner.setStuff(4.2, -6.7, 4, 0.01);
		MotionPlanner.PlannedMotion motion = MotionPlanner.planMotion(3);
		
		try
		{
			String content = motion.toString();

			File file = new File("C:/Users/Idan/Desktop/FileOfTest.txt");

			// if file doesnt exists, then create it
			if (!file.exists())
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
