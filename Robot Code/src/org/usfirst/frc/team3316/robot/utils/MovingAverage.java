package org.usfirst.frc.team3316.robot.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.DoubleSupplier;

public class MovingAverage
{
	private static Timer timer;
	
	private double [] lastValues;
	private int index = 0;
	
	private TimerTask insertTask;
	
	static
	{
		timer = new Timer();
	}
	
	/**
	 * Constructs a new moving average and starts it
	 * @param size the number of values to keep track of
	 * @param updateRate the rate that a value is taken from func
	 * @param supplier a function that returns a double
	 */
	public MovingAverage (int size, int updateRate, DoubleSupplier supplier)
	{
		lastValues = new double [size];
		for (int i = 0; i < size; i++)
		{
			lastValues[i] = 0;
		}
		
		insertTask = new TimerTask() 
		{
			public void run() 
			{
				insert(supplier.getAsDouble());	
			}
		};
		
		timer.schedule(insertTask, 0, updateRate);
	}
	
	private void insert (double value)
	{
		lastValues[index] = value;
		index++;
		index %= lastValues.length;
	}
	
	/**
	 * Returns the average of all the current saved samples
	 */
	public double get ()
	{
		double sum = 0;
		
		for (int i = 0; i < lastValues.length; i++)
		{
			sum += lastValues[i];
		}
		
		return (sum /= lastValues.length);
	}
}
