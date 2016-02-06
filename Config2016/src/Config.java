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
				
				addToConstants("BUTTON_TOGGLE_OMNI", 2);
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

				addToConstants("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL", 1); // Change
																		// this
																		// after
																		// prototype
				addToConstants("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL", 1); // Change
																		// this
																		// after
																		// prototype

				addToConstants("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL", 1); // Change
																		// this
																		// after
																		// prototype
				
				addToConstants("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL", 1); // Change
																		// this
																		// after
																		// prototype

				addToConstants("CHASSIS_MOTOR_LEFT_REVERSE", false);
				addToConstants("CHASSIS_MOTOR_RIGHT_REVERSE", true);
				
				addToConstants("CHASSIS_LONG_PISTONS_MODULE", 1);
				addToConstants("CHASSIS_LONG_PISTONS_FORWARD", 0);
				addToConstants("CHASSIS_LONG_PISTONS_REVERSE", 1);
				
				addToConstants("CHASSIS_SHORT_PISTONS_LEFT_MODULE", 1);
				addToConstants("CHASSIS_SHORT_PISTONS_LEFT_FORWARD", 2);
				addToConstants("CHASSIS_SHORT_PISTONS_LEFT_REVERSE", 3);
				
				addToConstants("CHASSIS_SHORT_PISTONS_RIGHT_MODULE", 1);
				addToConstants("CHASSIS_SHORT_PISTONS_RIGHT_FORWARD", 4);
				addToConstants("CHASSIS_SHORT_PISTONS_RIGHT_REVERSE", 5);
				
				addToConstants("CHASSIS_HALL_EFFECT_LEFT_FRONT", 12);
				addToConstants("CHASSIS_HALL_EFFECT_LEFT_BACK", 13);
				addToConstants("CHASSIS_HALL_EFFECT_RIGHT_FRONT", 11);
				addToConstants("CHASSIS_HALL_EFFECT_RIGHT_BACK", 10);
				

				/*
				 * Intake
				 */
				addToConstants("INTAKE_SOLENOID_MODULE", 0);
				
				addToConstants("INTAKE_SOLENOID_FORWARD", 6); // changed
																		// for
																		// prototype
				addToConstants("INTAKE_SOLENOID_REVERSE", 7); // changed
																		// for
																		// prototype
				addToConstants("INTAKE_MOTOR", 3); // changed for prototype

				addToConstants("INTAKE_LEFT_SWITCH", 7);
				addToConstants("INTAKE_RIGHT_SWITCH", 1);

				addToConstants("INTAKE_MOTOR_REVERSE", false);
				addToConstants("INTAKE_MOTOR_PDP_CHANNEL", 3); // changed for
																// prototype
				addToConstants("INTAKE_MOTOR_MAX_CURRENT", 10.0); // TODO:
																	// Check the
																	// stall
																	// current

				addToConstants("INTAKE_POT", 6);
				addToConstants("INTAKE_POT_FULL_RANGE", 270.0);
				addToConstants("INTAKE_POT_OFFSET", 0.0);

				/*
				 * Transport
				 */
				addToConstants("TRANSPORT_MOTOR", 8);

				addToConstants("TRANSPORT_MOTOR_REVERSE", false);
				addToConstants("TRANSPORT_MOTOR_PDP_CHANNEL", 5);
				addToConstants("TRANSPORT_MOTOR_MAX_CURRENT", 10.0); // TODO:
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
				addToConstants("FLYWHEEL_MOTOR", 4);
				addToConstants("FLYWHEEL_MOTOR_REVERSE", false);
				addToConstants("FLYWHEEL_MOTOR_PDP_CHANNEL", 3);
				addToConstants("FLYWHEEL_MOTOR_MAX_CURRENT", 10.0);
				
				/*
				 * Turret
				 */
				addToConstants("TURRET_MOTOR", 0);
				addToConstants("TURRET_MOTOR_REVERSE", false);
				addToConstants("TURRET_MOTOR_PDP_CHANNEL", 0);
				addToConstants("TURRET_MOTOR_MAX_CURRENT", 10.0); // TODO: Check the stall current
				
				addToConstants("TURRET_POT", 4);
				addToConstants("TURRET_POT_FULL_RANGE", 400.0);
		 		addToConstants("TURRET_POT_OFFSET", 0.0);
				
				/*
				 * Hood
				 */
		 		addToConstants("HOOD_MOTOR", 12);
				addToConstants("HOOD_MOTOR_REVERSE", false);
				addToConstants("HOOD_MOTOR_PDP_CHANNEL", 10);
				addToConstants("HOOD_MOTOR_MAX_CURRENT", 10.0); // TODO: Check the stall current
		 		
				addToConstants("HOOD_POT", 5);
				addToConstants("HOOD_POT_FULL_RANGE", 100.0);
				addToConstants("HOOD_POT_OFFSET", 30.0);
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
			
			/*
			 * Retract Omni
			 */
			{
				addToVariables("chassis_RetractOmni_Timeout", 0.0);
			}
			
			/*
			 * Extend Omni
			 */
			{
				addToVariables("chassis_ExtendOmni_Timeout", 0.0);
				addToVariables("chassis_ExtendOmni_CancelTimeout", 1.5);
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
				addToVariables("flywheel_Bangbang_Setpoint", 40.0);
				addToVariables("flywheel_Bangbang_OnVoltage", 1.0);
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
		
		/*
		 * Turret
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
				addToVariables("turret_Angle_SetPoint", 0.0);
				
				// PID Control
				addToVariables("turret_PID_KP", 0.0);
				addToVariables("turret_PID_KI", 0.0);
				addToVariables("turret_PID_KD", 0.0);
				
				// Bangbang Control
				addToVariables("turret_Bangbang_OnVoltage", 0.0);
				addToVariables("turret_Bangbang_OffVoltage", 0.0);
				
				addToVariables("turret_Pot_LeftThresh", 0.0);
				addToVariables("turret_Pot_RightThresh", 400.0);	
			}
		}
		
		/*
		 * Hood
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
				// PID Control
				addToVariables("hood_PID_KP", 0.0);
				addToVariables("hood_PID_KI", 0.0);
				addToVariables("hood_PID_KD", 0.0);
				
				// Bangbang Control
				addToVariables("hood_Bangbang_OnVoltage", 0.0);
				addToVariables("hood_Bangbang_OffVoltage", 0.0);
				addToVariables("hood_Angle_SetPoint", 0.0);
				
				addToVariables("hood_Pot_BottomThresh", 0.0);
				addToVariables("hood_Pot_TopThresh", 400.0);	
			}
		}

		/*
		 * Transport
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

			}
			
			/*
			 * Roll In
			 */

			{
				addToVariables("transport_RollIn_Speed", 0.5);
			}

			/*
			 * Bangbang
			 */
			{
				addToVariables("transport_Bangbang_Setpoint", 0.0);
				addToVariables("transport_Bangbang_OnVoltage", 1.0);
				addToVariables("transport_Bangbang_OffVoltage", 0.0);
			}
		}
	}
}
