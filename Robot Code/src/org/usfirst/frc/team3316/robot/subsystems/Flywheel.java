package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class Flywheel extends DBugSubsystem
{
	private Talon talon;
	private Counter counter;

	double rate = 0;
	private static Timer timer;

	static
	{
		timer = new Timer();
	}

	/**
	 * Class for filtering out noise from the FPGA rate calculation. Essentially
	 * this is a low pass filter.
	 * 
	 * @author D-Bug
	 *
	 */
	private class RateTask extends TimerTask
	{
		public void run()
		{
			double delta = counter.getRate() - rate;

			if (delta < 10) // random constant, couldv'e been anything (when
							// there is noise the delta is in the scale of 10^5
							// - 10^6).
			{
				rate = counter.getRate();
			}
		}
	}

	public Flywheel()
	{
		talon = Robot.actuators.flywheel;
		counter = Robot.sensors.flywheelCounter;

		timer.schedule(new RateTask(), 0, 10);
	}

	/**
	 * Sets the flywheel a certain % voltage.
	 * 
	 * @param v
	 *            The % voltage to set. Positive is for shooting.
	 * @return Whether setting was successful. Will return false if motor
	 *         reaches stall current as measured by the PDP.
	 */
	public boolean setMotors(double v)
	{
		double talonCurrent = pdp.getCurrent(2);

		if (Math.abs(talonCurrent) >= (double) Robot.config
				.get("FLYWHEEL_MOTOR_HIGH_THRESH"))
		{
			talon.set(0);
			return false; //Current going to motor is too high - abort
		}

		talon.set(-v);
		return true;
	}

	/**
	 * Returns the turning rate of the flywheel.
	 * 
	 * @return The speed in rounds per second.
	 */
	public double getRate()
	{
		return rate;
	}

	public void initDefaultCommand()
	{
	}
}
