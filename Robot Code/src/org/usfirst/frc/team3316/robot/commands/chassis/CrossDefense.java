package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CrossDefense extends DBugCommand
{
	private double currentSpeed;
	private PIDController pid;
	private boolean lastIsOnDefense, isFinished, reverse;

	public CrossDefense(boolean reverse)
	{
		requires(Robot.chassis);

		this.reverse = reverse;
	}

	protected void init()
	{
		currentSpeed = (double) config.get("chassis_CrossDefense_Voltage");
	}

	protected void execute()
	{
		Robot.chassis.setMotors(currentSpeed, currentSpeed);

		currentSpeed -= currentSpeed > (double) config
				.get("chassis_CrossDefense_MinSpeed")
						? (double) config.get("chassis_CrossDefense_DownV") : 0.0;
	}

	protected boolean isFinished()
	{
		isFinished = (lastIsOnDefense && !Robot.chassis.isOnDefense());
		lastIsOnDefense = Robot.chassis.isOnDefense();

		return isFinished;
	}

	protected void fin()
	{
		SmartDashboard.putBoolean("finished", true);
		Robot.chassis.setMotors(0, 0);
	}

	protected void interr()
	{
		fin();
	}

}
