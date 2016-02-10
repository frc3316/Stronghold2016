import java.util.Hashtable;

public class Config
{
	public static Hashtable<String, Object> variablesB;
	public static Hashtable<String, Object> constantsB;

	public static Hashtable<String, Object> variablesA;
	public static Hashtable<String, Object> constantsA;

	private Config () {}
	
	static
	{
		variablesB = new Hashtable<String, Object>();
		constantsB = new Hashtable<String, Object>();

		variablesA = new Hashtable<String, Object>();
		constantsA = new Hashtable<String, Object>();

		initConfig();
		IO.initIO();
	}

	public static void addToConstantsA(String key, Object value)
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

	public static void addToVariablesA(String key, Object value)
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

	public static void addToConstantsB(String key, Object value)
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

	public static void addToVariablesB(String key, Object value)
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

	public static void addToConstants(String key, Object value)
	{
		addToConstantsA(key, value);
		addToConstantsB(key, value);
	}

	private static void addToVariables(String key, Object value)
	{
		addToVariablesA(key, value);
		addToVariablesB(key, value);
	}

	/*
	 * NOTE: constants and variables that are common to both robot A and robot B should be added with
	 * addToConstants() or addToVariables()
	 * 
	 * Use different constants and variables for the two robots only if there is a difference. 
	 */
	private static void initConfig()
	{
		/*
		 * Human IO
		 */
		{
			/*
			 * Constants
			 */
			{
				// Joysticks
				addToConstants("JOYSTICK_LEFT", 0);
				addToConstants("JOYSTICK_RIGHT", 1);
				addToConstants("JOYSTICK_OPERATOR", 2);

				// Buttons
				addToConstants("CLIMB_BUTTON", 6);
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
				addToConstants("INTAKE_MOTOR_REVERSE", false);
				
				addToConstants("INTAKE_MOTOR_MAX_CURRENT", 10.0);

				addToConstants("INTAKE_POT_FULL_RANGE", 270.0);
				addToConstants("INTAKE_POT_OFFSET", 0.0);

				/*
				 * Transport
				 */
				addToConstants("TRANSPORT_MOTOR_REVERSE", false);
				addToConstants("TRANSPORT_MOTOR_MAX_CURRENT", 10.0);
				
				addToConstants("TRANSPORT_ENCODER_REVERSE_DIRECTION", false);

				/*
				 * Flywheel
				 */
				addToConstants("FLYWHEEL_MOTOR_REVERSE", false);
			
				addToConstants("FLYWHEEL_MOTOR_MAX_CURRENT", 10.0);

				/*
				 * Turret
				 */
				addToConstants("TURRET_MOTOR_REVERSE", false);
		
				addToConstants("TURRET_MOTOR_MAX_CURRENT", 10.0); // TODO: Check the stall current
				
				addToConstants("TURRET_POT_FULL_RANGE", 400.0);
				addToConstants("TURRET_POT_OFFSET", 0.0);

				/*
				 * Hood
				 */
				addToConstants("HOOD_MOTOR_REVERSE", false);
				
				addToConstants("HOOD_MOTOR_MAX_CURRENT", 10.0); // TODO: Check the stall current

				addToConstants("HOOD_POT_FULL_RANGE", 100.0);
				addToConstants("HOOD_POT_OFFSET", 30.0);

				/*
				 * Climbing
				 */
				
				addToConstants("CLIMBING_MOTOR_1_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_1_MAX_CURRENT", 10.0);

				addToConstants("CLIMBING_MOTOR_2_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_2_MAX_CURRENT", 10.0);

				addToConstants("CLIMBING_MOTOR_3_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_3_MAX_CURRENT", 10.0);
				
				addToConstants("CLIMBING_MOTOR_4_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_4_MAX_CURRENT", 10.0);
				
				addToConstants("CLIMBING_POT_FULL_RANGE", 3600.0);
				addToConstants("CLIMBING_POT_OFFSET", 0.0);
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

				// TODO: Check what the following variables should be, and
				// change them.
				addToVariables("motionPlanner_MaxAccel", 2.5);
				addToVariables("motionPlanner_MaxDecel", -1.5);
				addToVariables("motionPlanner_MaxVelocity", 1.0);
				addToVariables("motionPlanner_TimeStep", 0.02);
			}
			
			/*
			 * Drive Distance
			 */
			{
				// PID
				addToVariables("chassis_DriveDistance_PID_KP", 0.0);
				addToVariables("chassis_DriveDistance_PID_KI", 0.0);
				addToVariables("chassis_DriveDistance_PID_KD", 0.0);
				addToVariables("chassis_DriveDistance_PID_KF", 0.0);
				
				addToVariables("chassis_DriveDistance_PID_Tolerance", 0.01);
				addToVariables("chassis_DriveDistance_PID_Setpoint", 0.0);
			}
			
			/*
			 * Set Constant Voltage
			 */
			{
				addToVariables("chassis_SetConstantVoltage_Voltage", 0.0);
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
				addToConstants("FLYWHEEL_MOTOR_HIGH_THRESH", 10.0);
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
				addToVariables("flywheel_PID_KF", 0.0);
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

		/*
		 * Climbing
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
				addToVariables("climbing_Pot_LowThresh", 100.0); // Check this
																	// value
				addToVariables("climbing_Pot_HighThresh", 3400.0); // Check this
																	// value

				addToVariables("climbing_UpSpeed", 0.0); // Check this value
				addToVariables("climbing_DownSpeed", -0.0); // Check this value
				addToVariables("climbing_Setpoint", 900.0);
			}
		}
	}
}
