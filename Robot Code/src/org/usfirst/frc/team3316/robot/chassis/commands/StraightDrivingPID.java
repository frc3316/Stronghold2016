package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.DBugCommand;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StraightDrivingPID extends DBugCommand {

	/*
	 * PID Inner Classes
	 */

	private class PIDSourceY implements PIDSource {

		public void setPIDSourceType(PIDSourceType pidSource) {
		}

		public PIDSourceType getPIDSourceType() {
			return null;
		}

		public double pidGet() {
			return Robot.chassis.getDistance();
		}

	}

	private class PIDOutputY implements PIDOutput {

		public void pidWrite(double output) {
			Robot.chassis.setMotors(output);
			SmartDashboard.putNumber("PID Y Chassis Output", output);
		}

	}

	PIDController pidControllerY;

	public StraightDrivingPID() {

		pidControllerY = new PIDController(0, // Kp
				0, // Ki
				0, // Kd
				new PIDSourceY(), // PIDSource
				new PIDOutputY(), // PIDOutput
				0.05); // Update rate in ms
		pidControllerY.setOutputRange(-1, 1);

		try {
			pidControllerY.setAbsoluteTolerance((double) Robot.config.get("PID TOLLERACE"));
		} catch (ConfigException e) {
			Robot.logger.severe(e);
		}

	}

	protected void init() {
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void fin() {
	}

	protected void interr() {
	}

}
