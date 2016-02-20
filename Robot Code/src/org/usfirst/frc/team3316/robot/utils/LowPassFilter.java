package org.usfirst.frc.team3316.robot.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.DoubleSupplier;

public class LowPassFilter
{
	private static Timer timer;
	
	static
	{
		timer = new Timer();
	}
	
	private double value;
	private double maxChange;
	
	private DoubleSupplier supplier;
	
	private TimerTask filterTask;
	
	public LowPassFilter (double maxChange, long period, DoubleSupplier supplier)
	{
		this.maxChange = maxChange;
		this.supplier = supplier;
		
		value = supplier.getAsDouble();
		
		filterTask = new TimerTask()
		{
			public void run()
			{
				updateValue();
			}
		};
		
		timer.schedule(filterTask, 0, period);
	}
	
	private void updateValue ()
	{
		double currentValue = supplier.getAsDouble();
		double delta = Math.abs(value - currentValue);
		
		if (delta <= maxChange)
		{
			value = currentValue;
		}
	}
	
	/**
	 * Returns the current value of the lowPass
	 * @return
	 */
	public double get ()
	{
		return value;
	}
}
