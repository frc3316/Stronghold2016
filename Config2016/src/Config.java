import java.util.Hashtable;

public class Config
{
	public Hashtable<String, Object> variablesB;
	public Hashtable<String, Object> constantsB;

	public Hashtable<String, Object> variablesA;
	public Hashtable<String, Object> constantsA;

	public Config()
	{
		variablesB = new Hashtable<String, Object>();
		constantsB = new Hashtable<String, Object>();

		variablesA = new Hashtable<String, Object>();
		constantsA = new Hashtable<String, Object>();

		initConfig();
	}

	private void addToConstantsA(String key, Object value)
	{
		if (constantsA.containsKey(key))
		{
			constantsA.replace(key, value);
		}
		else
		{
			constantsA.put(key, value);
		}
	}

	private void addToVariablesA(String key, Object value)
	{
		if (variablesA.containsKey(key))
		{
			variablesA.replace(key, value);
		}
		else
		{
			variablesA.put(key, value);
		}
	}

	private void addToConstantsB(String key, Object value)
	{
		if (constantsB.containsKey(key))
		{
			constantsB.replace(key, value);
		}
		else
		{
			constantsB.put(key, value);
		}
	}

	private void addToVariablesB(String key, Object value)
	{
		if (variablesB.containsKey(key))
		{
			variablesB.replace(key, value);
		}
		else
		{
			variablesB.put(key, value);
		}
	}

	private void addToConstants(String key, Object value)
	{
		addToConstantsA(key, value);
		addToConstantsB(key, value);
	}

	private void addToVariables(String key, Object value)
	{
		addToVariablesA(key, value);
		addToVariablesB(key, value);
	}

	/*
	 * NOTE: constants and variables that are common to both robot A and robot B
	 * should be added with addToConstants() or addToVariables()
	 * 
	 * Specify the which table to add the constant or variable to only if there
	 * is a difference between the two robots
	 */
	private void initConfig()
	{
		/*
		 * Human IO
		 */
		{
			/*
			 * Constants
			 */
			{
				addToConstants("JOYSTICK_LEFT", 0);
				addToConstants("JOYSTICK_RIGHT", 1);
				addToConstants("JOYSTICK_OPERATOR", 2);
			}
		}

		/*
		 * RobotIO
		 */
		{
			/*
			 * Constants
			 */
			{
				/*
				 * Chassis
				 */
				addToConstants("CHASSIS_MOTOR_LEFT_1", 5);
				addToConstants("CHASSIS_MOTOR_LEFT_2", 6);
				addToConstants("CHASSIS_MOTOR_RIGHT_1", 1);
				addToConstants("CHASSIS_MOTOR_RIGHT_2", 2);

				addToConstants("CHASSIS_MOTOR_LEFT_REVERSE", false);
				addToConstants("CHASSIS_MOTOR_RIGHT_REVERSE", true);

				addToConstants("CHASSIS_LEFT_ENCODER_CHANNEL_A", 4);
				addToConstants("CHASSIS_LEFT_ENCODER_CHANNEL_B", 5);
				
				addToConstants("CHASSIS_RIGHT_ENCODER_CHANNEL_A", 2);
				addToConstants("CHASSIS_RIGHT_ENCODER_CHANNEL_B", 3);
				
				
				addToConstants("CHASSIS_LEFT_ENCODER_REVERSE", true);
				addToConstants("CHASSIS_RIGHT_ENCODER_REVERSE", false);
				
				//For some reason the encoders give 4 times less the correct speed
				addToConstants("CHASSIS_LEFT_ENCODER_DISTANCE_PER_PULSE", (4 * (6*Math.PI) / 32) * 0.0254);
				addToConstants("CHASSIS_RIGHT_ENCODER_DISTANCE_PER_PULSE", (4 * (6*Math.PI) / 32) * 0.0254);
				
				
				/*
				 * Intake
				 */
				addToConstants("INTAKE_SOLENOID_FORWARD_CHANNEL", 0);
				addToConstants("INTAKE_SOLENOID_REVERSE_CHANNEL", 1);

				addToConstants("INTAKE_MOTOR", 3);

				addToConstants("INTAKE_MOTOR_REVERSE", false);
				addToConstants("INTAKE_MOTOR_PDP_CHANNEL", 2);
				addToConstants("INTAKE_MOTOR_MAX_CURRENT", 5.5); // TODO:
																	// Check
																	// what this
																	// value
																	// should
																	// be.

				addToConstants("INTAKE_LS", 7);
				addToConstants("INTAKE_RS", 1);
				addToConstants("INTAKE_TS", 12);
				addToConstants("INTAKE_BS", 6);
				addToConstants("INTAKE_POT", 6);
				addToConstants("INTAKE_POT_FULL_RANGE", 270.0);
				addToConstants("INTAKE_POT_OFFSET", 0.0);
				addToConstants("INTAKE_POT_LOW_TRESH", 1.0);
				addToConstants("INTAKE_POT_HIGH_TRESH", 130.5);

				/*
				 * Transport
				 */
				addToConstants("TRANSPORT_MOTOR_1", 7);
				addToConstants("TRANSPORT_MOTOR_2", 13);

				addToConstants("TRANSPORT_MOTOR_1_REVERSE", false);
				addToConstants("TRANSPORT_MOTOR_2_REVERSE", true);
				addToConstants("TRANSPORT_MOTOR_1_PDP_CHANNEL", 5);
				addToConstants("TRANSPORT_MOTOR_2_PDP_CHANNEL", 6);
				addToConstants("TRANSPORT_MOTOR_1_MAX_CURRENT", 1000.0); // TODO:
																			// Check
																			// what
																			// this
																			// value
																			// should
																			// be.
				addToConstants("TRANSPORT_MOTOR_2_MAX_CURRENT", 1000.0); // TODO:
																			// Check
																			// what
																			// this
																			// value
																			// should
																			// be.

				addToConstants("TRANSPORT_ENCODER_A", 10);
				addToConstants("TRANSPORT_ENCODER_B", 11);
				addToConstants("TRANSPORT_ENCODER_REVERSE_DIRECTION", false);

				/*
				 * Flywheel
				 */
				addToConstants("FLYWHEEL_MOTOR_1", 4);
				addToConstants("FLYWHEEL_MOTOR_1_REVERSE", false);
				addToConstants("FLYWHEEL_MOTOR_1_PDP_CHANNEL", 3);
				addToConstants("FLYWHEEL_MOTOR_1_MAX_CURRENT", 1000.0); // TODO:
																		// Check
																		// what
																		// this
																		// value
																		// should
																		// be.
				addToConstants("FLYWHEEL_MOTOR_2", 14);
				addToConstants("FLYWHEEL_MOTOR_2_REVERSE", true);
				addToConstants("FLYWHEEL_MOTOR_2_PDP_CHANNEL", 5);
				addToConstants("FLYWHEEL_MOTOR_2_MAX_CURRENT", 1000.0);// TODO:
																		// Check
																		// what
																		// this
																		// value
																		// should
																		// be.
			}
		}
		
		

		/*
		 * Chassis
		 */
		{
			/*
			 * Constants
			 */
			{
				addToConstants("CHASSIS_DEFENSE_ANGLE_TIMEOUT", 500.0);
				addToConstants("CHASSIS_DEFENSE_ANGLE_RANGE", 4.0);
				addToConstants("CHASSIS_ANGLE_MOVING_AVG_SIZE", 10);
			}

			/*
			 * Variables
			 */
			{
				addToVariables("chassis_TankDrive_DeadBand", 0.05);

				addToVariables("chassis_TankDrive_InvertX", false);
				addToVariables("chassis_TankDrive_InvertY", true);

				// TODO: Check what the following variables should be, and
				// change them.
				addToVariables("motionPlanner_MaxAccel", 1.2);
				addToVariables("motionPlanner_MaxDecel", -2.8);
				addToVariables("motionPlanner_MaxVelocity", 5.0);
				addToVariables("motionPlanner_TimeStep", 0.02);
			}
			
			/*
			 * Drive Distance
			 */
			{
				// PID
				addToVariables("chassis_PIDRight_KP", 0.25);
				addToVariables("chassis_PIDRight_KI", 0.0);
				addToVariables("chassis_PIDRight_KD", 0.05);
				addToVariables("chassis_PIDRight_KF", 0.1);
				
				addToVariables("chassis_PIDLeft_KP", 0.25);
				addToVariables("chassis_PIDLeft_KI", 0.0);
				addToVariables("chassis_PIDLeft_KD", 0.05);
				addToVariables("chassis_PIDLeft_KF", 0.1);
				
				// Velocity Error Setpoint
				addToVariables("chassis_DriveDistance_KV", 0.0);
				addToVariables("chassis_DriveDistance_KA", 0.0);
			}
		}

		/*
		 * Flywheel
		 */
		{
			/*
			 * Constants
			 */
			{
				addToConstants("FLYWHEEL_MOTOR_HIGH_THRESH", 1000.0); // TOOD:
																		// Measure
																		// this
																		// and
																		// make
																		// it
																		// useful.
				addToConstants("FLYWHEEL_COUNTER", 0);
			}
			/*
			 * Variables
			 */
			{

				// Bangbang - TO REMOVE AFTER TESTINGS
				addToVariables("flywheel_Bangbang_Setpoint", 0.0);
				addToVariables("flywheel_Bangbang_OnVoltage", 0.0);
				addToVariables("flywheel_Bangbang_OffVoltage", 0.0);

				// PID - TO REMOVE AFTER TESTINGS
				addToVariables("flywheel_PID_Setpoint", 0.0);
				addToVariables("flywheel_PID_KP", 0.0);
				addToVariables("flywheel_PID_KI", 0.0);
				addToVariables("flywheel_PID_KD", 0.0);
			}
		}
	}
}
