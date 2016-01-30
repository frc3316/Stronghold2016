package test_package;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import test_package.MotionPlanner.Step;

public class HereWeTest
{
	public static void main(String[] args)
	{
		MotionPlanner.setStuff(6.566, -4.352, 4);
		MotionPlanner.PlannedMotion motion = MotionPlanner.planMotion(8);
		
		Step[] steps = motion.convertToStepArray(0.01);
		
		try
		{
			String content = "";
			content += "Number of steps: " + steps.length + "\n"; 
			
			for (Step step : steps)
			{
				content += step.toString();
			}

			File file = new File("C:/Users/D-Bug/Desktop/FileOfTest.txt");

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
