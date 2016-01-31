package test_package;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import test_package.PlannedMotion.Step;

public class HereWeTest
{
	public static void main(String[] args)
	{
		MotionPlanner.setStuff(4.881, -7.43, 3.5);
		PlannedMotion motion = MotionPlanner.planMotion(-3.5);
		
		Step[] steps = motion.convertToStepArray(0.03);
		
		try
		{
			String content = "";
			content += "Number of steps: " + steps.length + "\n"; 
			
			for (Step step : steps)
			{
				System.out.println(MotionPlanner.distanceToBrake(step.velocity));
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
