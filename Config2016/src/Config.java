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
			addToConstants("CURRENT_CONTROL_COUNTER", 10);
			
			{
				/*
				 * Chassis
				 */
				addToConstants("CHASSIS_MOTOR_LEFT_1", 5);
				addToConstants("CHASSIS_MOTOR_LEFT_2", 6);
				addToConstants("CHASSIS_MOTOR_RIGHT_1", 1);
				addToConstants("CHASSIS_MOTOR_RIGHT_2", 2);

				addToConstants("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL", 1); //Change this after prototype
				addToConstants("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL", 1); //Change this after prototype
				
				addToConstants("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL", 1); //Change this after prototype
				addToConstants("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL", 1); //Change this after prototype
				
				addToConstants("CHASSIS_MOTOR_LEFT_REVERSE", false);
				addToConstants("CHASSIS_MOTOR_RIGHT_REVERSE", true);

				/*
				 * Intake
				 */
				addToConstants("INTAKE_SOLENOID_FORWARD_CHANNEL", 6); //changed for prototype
				addToConstants("INTAKE_SOLENOID_REVERSE_CHANNEL", 7); //changed for prototype
				addToConstants("INTAKE_MOTOR", 3); //changed for prototype

				addToConstants("INTAKE_LEFT_SWITCH", 7);
				addToConstants("INTAKE_RIGHT_SWITCH", 1);

				addToConstants("INTAKE_MOTOR_REVERSE", false);
				addToConstants("INTAKE_MOTOR_PDP_CHANNEL", 3); //changed for prototype 
				addToConstants("INTAKE_MOTOR_MAX_CURRENT", 666.6); // TODO: Check the stall current

				addToConstants("INTAKE_POT", 6);
				addToConstants("INTAKE_POT_FULL_RANGE", 270.0);
				addToConstants("INTAKE_POT_OFFSET", 0.0);

				/*
				 * Transport
				 */
				addToConstants("TRANSPORT_MOTOR", 8);

				addToConstants("TRANSPORT_MOTOR_REVERSE", false);
				addToConstants("TRANSPORT_MOTOR_PDP_CHANNEL", 5);
				addToConstants("TRANSPORT_MOTOR_MAX_CURRENT", 1000.0); // TODO:
																			// Check
																			// what
																			// this
																			// value
																			// should
																			// be.

				addToConstants("TRANSPORT_ENCODER_A", 2);
				addToConstants("TRANSPORT_ENCODER_B", 3);
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
				addToConstants("FLYWHEEL_MOTOR_2", 11);
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
		
		/*
		 * Intake
		 */
		{
			/*
			 * Constants
			 */
			{
			}

			/*
			 * Variables
			 */
			{
				addToVariables("intake_Pot_LowThresh", 1.0);
				addToVariables("intake_Pot_HightThresh", 130.5);				
			}
			
			/*
			 * Roll In
			 */
			{
				addToVariables("intake_RollIn_Speed", 0.5);
			}
			
			/*
			 * Roll Out
			 */
			{
				addToVariables("intake_RollOut_Speed", -0.5);
			}
		}
	}
}
