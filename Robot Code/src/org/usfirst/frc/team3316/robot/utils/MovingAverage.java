package org.usfirst.frc.team3316.robot.utils;

import java.util.TimerTask;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team3316.robot.Robot;

public class MovingAverage
{
	private double [] lastValues;
	private int index = 0;
	
	private int updateRate;
	
	private TimerTask insertTask;
	
	DoubleSupplier supplier;
	
	/**
	 * Constructor
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
		
		this.updateRate = updateRate;
		
		this.supplier = supplier;
	}
	
	/**
	 * Schedules a timertask that updates the moving average in the timer object in robot
	 * Should be called only after timer was initialized 
	 */
	public void timerInit ()
	{
		if (insertTask != null)
		{
			return;
		}
		
		insertTask = new TimerTask() 
		{
			public void run() 
			{
				insert(supplier.getAsDouble());				
			}
		};
		
		Robot.timer.schedule(insertTask, 0, updateRate);
	}
	
	private void insert (double value)
	{
		lastValues[index] = value;
		index++;
		index %= lastValues.length;
	}
	
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
