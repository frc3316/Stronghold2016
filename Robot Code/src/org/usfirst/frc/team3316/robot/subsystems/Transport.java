package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.SpeedController;

public class Transport extends DBugSubsystem
{

	private SpeedController transportMotor1, transportMotor2;
	double motor1Current, motor2Current;

	public Transport()
	{
		transportMotor1 = Robot.actuators.transportMotor;
		transportMotor2 = Robot.actuators.transportMotor;
	}

	public void initDefaultCommand()
	{}

	public boolean setMotors(double v)
	{
		motor1Current = pdp
				.getCurrent((int) config.get("TRANSPORT_MOTOR1_PDP_CHANNEL"));
		motor2Current = pdp
				.getCurrent((int) config.get("TRANSPORT_MOTOR2_PDP_CHANNEL"));

		if (motor1Current >= (double) config.get("MOTOR1_CURRENT_HIGH_THRESH")
				|| motor2Current >= (double) config
						.get("MOTOR2_CURRENT_HIGH_THRESH"))
		{
			transportMotor1.set(0);
			transportMotor2.set(0);
			
			return false;
		}

			transportMotor1.set(v);
			transportMotor1.set(v);
			
			return true;
	}

}
