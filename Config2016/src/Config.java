import java.util.Hashtable;

import javax.xml.ws.soap.AddressingFeature;

public class Config
{
	public static Hashtable<String, Object> variablesB;
	public static Hashtable<String, Object> constantsB;

	public static Hashtable<String, Object> variablesA;
	public static Hashtable<String, Object> constantsA;

	private Config()
	{
	}

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
		System.out.println(
				"Trying to add to constants A: key " + key + " value " + value);

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
		System.out.println(
				"Trying to add to variables A: key " + key + " value " + value);

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
		System.out.println(
				"Trying to add to constants B: key " + key + " value " + value);

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
		System.out.println(
				"Trying to add to variables B: key " + key + " value " + value);

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

	public static void addToVariables(String key, Object value)
	{
		addToVariablesA(key, value);
		addToVariablesB(key, value);
	}

	/*
	 * NOTE: constants and variables that are common to both robot A and robot B
	 * should be added with addToConstants() or addToVariables()
	 * 
	 * Use different constants and variables for the two robots only if there is
	 * a difference. TestModeStuff
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
				/*
				 * Joysticks
				 */
				{
					addToConstants("JOYSTICK_LEFT", 0);
					addToConstants("JOYSTICK_RIGHT", 1);
					addToConstants("JOYSTICK_OPERATOR", 2);
				}

				/*
				 * Buttons
				 */
				{
					// Joystick right
					addToVariables("button_Toggle_Omni", 1);
					
					// Joystick operator
			
					addToVariables("button_Intake_Toggle", 1);
					addToVariables("button_Collect_Ball", 8);
					addToVariables("button_Eject_Ball", 6);
					addToVariables("button_Roll_In", 2);
					addToVariables("button_Roll_Out", 3);
					addToVariables("button_Climb", 10);
					addToVariables("button_Warm_Up_Shooter", 7);
					addToVariables("button_Shooting_Trigger", 4);
					addToVariables("button_Warm_Up_Flywheel", 5);
				}
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
				addToConstants("CHASSIS_MOTOR_LEFT_REVERSE", true);
				addToConstants("CHASSIS_MOTOR_RIGHT_REVERSE", true);

				addToConstants("CHASSIS_LEFT_ENCODER_REVERSE", true);
				addToConstants("CHASSIS_RIGHT_ENCODER_REVERSE", false);

				// For some reason the encoders give 4 times less the correct
				// speed
				addToConstants("CHASSIS_LEFT_ENCODER_DISTANCE_PER_PULSE",
						(4 * (6 * Math.PI) / 32) * 0.0254);
				addToConstants("CHASSIS_RIGHT_ENCODER_DISTANCE_PER_PULSE",
						(4 * (6 * Math.PI) / 32) * 0.0254);

				/*
				 * Intake
				 */
				addToConstants("INTAKE_MOTOR_REVERSE", true);

				addToConstants("INTAKE_MOTOR_MAX_CURRENT", 30.0);

				addToConstants("INTAKE_POT_FULL_RANGE", 270.0);
				addToConstants("INTAKE_POT_OFFSET", 0.0);

				/*
				 * Transport
				 */
				addToConstants("TRANSPORT_MOTOR_REVERSE", true);
				addToConstants("TRANSPORT_MOTOR_MAX_CURRENT", 30.0);

				addToConstants("TRANSPORT_ENCODER_REVERSE_DIRECTION", false);

				/*
				 * Flywheel
				 */
				addToConstants("FLYWHEEL_MOTOR_REVERSE", false);

				addToConstants("FLYWHEEL_MOTOR_MAX_CURRENT", 70.0);

				/*
				 * Turret
				 */
				addToConstants("TURRET_MOTOR_REVERSE", false);

				addToConstants("TURRET_MOTOR_MAX_CURRENT", 30.0); // TODO: Check
																	// the stall

				addToConstants("TURRET_POT_FULL_RANGE", -821.269);

				/*
				 * Hood
				 */
				addToConstantsA("HOOD_MOTOR_REVERSE", false);
				addToConstantsB("HOOD_MOTOR_REVERSE", true);

				addToConstants("HOOD_MOTOR_MAX_CURRENT", 30.0); // TODO: Check
																// the stall
																// current

				addToConstantsA("HOOD_POT_FULL_RANGE", -300.0); // negative to flip incrementing direction
				addToConstantsB("HOOD_POT_FULL_RANGE", -270.0); // negative to flip incrementing direction
				
				/*
				 * Climbing
				 */
				addToConstants("CLIMBING_MOTOR_1_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_1_MAX_CURRENT", 50.0);

				addToConstants("CLIMBING_MOTOR_2_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_2_MAX_CURRENT", 50.0);

				addToConstants("CLIMBING_MOTOR_3_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_3_MAX_CURRENT", 50.0);

				addToConstants("CLIMBING_MOTOR_4_REVERSE", false);
				addToConstants("CLIMBING_MOTOR_4_MAX_CURRENT", 50.0);

				addToConstants("CLIMBING_POT_FULL_RANGE", 3600.0);
				addToConstants("CLIMBING_POT_OFFSET", 0.0);

				/*
				 * Spare
				 */
				addToConstants("SPARE_MOTOR_PDP_REVERSE", false);

				addToConstants("SPARE_MOTOR_PDP_MAX_CURRENT", 10.0);
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

			// Cross Defense
			{
				addToConstants("chassis_Defense_Angle_Timeout", 400.0);

				addToVariables("chassis_CrossDefense_Voltage", 0.5);

				addToVariables("chassis_Defense_Pitch_Thresh", 2.5);
				addToVariables("chassis_Defense_Roll_Thresh", 4.0);

				addToVariables("chassis_CrossBrake_Timeout", 400.0);
				addToVariables("chassis_CrossDefense_BrakeV", -0.32);

				addToVariables("chassis_CrossDefense_MinSpeed", 0.35);
				addToVariables("chassis_CrossDefense_DownV", 0.001);
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
				addToVariables("chassis_ExtendOmni_LiftTimeout", 500);
			}

			// TODO: Check what the following variables should be, and
			// change them.
			addToVariables("motionPlanner_MaxAccel", 2.5);
			addToVariables("motionPlanner_MaxDecel", -1.5);
			addToVariables("motionPlanner_MaxVelocity", 1.0);
			addToVariables("motionPlanner_TimeStep", 0.02);

			/*
			 * Drive Distance
			 */
			{
				// PID
				addToVariables("chassis_DriveDistance_PID_KP", 80.0);
				addToVariables("chassis_DriveDistance_PID_KI", 0.0);
				addToVariables("chassis_DriveDistance_PID_KD", 12300.0);

				addToVariables("chassis_DriveDistance_KV", 0.5);

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

				addToVariables("flywheel_CounterFilter_MaxChange", 50.0);
				addToVariables("flywheel_CounterFilter_Period", (long) 10);

				// Bangbang
				addToVariables("flywheel_Bangbang_Setpoint", 45.0);
				addToVariables("flywheel_Bangbang_OnVoltage", 1.0);
				addToVariables("flywheel_Bangbang_OffVoltage", 0.0);

				// PID
				addToVariables("flywheel_PID_Setpoint", 45.0);
				addToVariables("flywheel_PID_Tolerance", 2.0);
				addToVariables("flywheel_PID_KP", 16.0);
				addToVariables("flywheel_PID_KI", 0.54);
				addToVariables("flywheel_PID_KD", 0.010);
				addToVariables("flywheel_PID_KF", 16.0);
			}

			/*
			 * Accelerate Flywheel
			 */
			{
				addToVariables("flywheel_AccelerateFlywheel_Timeout", 0.15);
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
			}

			/*
			 * Roll In
			 */
			{
				addToVariables("intake_RollIn_Speed", 1.0);
				addToVariablesA("intake_OpenIntake_Timeout", 2.0); // In seconds
				addToVariablesB("intake_OpenIntake_Timeout", 2.0); // In seconds
			}

			/*
			 * Roll Out
			 */
			{
				addToVariables("intake_RollOut_Speed", -0.5);
				addToVariablesA("intake_CloseIntake_Timeout", 2.5); // In seconds
				addToVariablesB("intake_CloseIntake_Timeout", 2.5); // In seconds
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
				addToVariablesA("turret_Pot_Offset", 189.54671547503355);
				addToVariablesB("turret_Pot_Offset", 399.4483151915127);
				
				addToVariables("turret_PotFilter_MaxChange", 4.0);
				addToVariables("turret_PotFilter_Period", (long) 10);

				// PID Control
				addToVariables("turret_Angle_SetPoint", 0.0);
				addToVariables("turret_PID_Tolerance", 0.5);
				
				addToVariablesA("turret_PID_KP", 10.0);
				addToVariablesA("turret_PID_KI", 0.0);
				addToVariablesA("turret_PID_KD", -0.01);
				
				addToVariablesB("turret_PID_KP", 15.0);
				addToVariablesB("turret_PID_KI", 1.2);
				addToVariablesB("turret_PID_KD", 0.8);

				// Bangbang Control
				addToVariables("turret_Bangbang_OnVoltage", 0.075);
				addToVariables("turret_Bangbang_OffVoltage", -0.08);
				
				addToVariables("turret_TurretBangbang_BigError", 5.0);
				addToVariables("turret_TurretBangbang_VScale", 2.5);

				addToVariables("turret_Pot_LeftThresh", -180.0);
				addToVariables("turret_Pot_RightThresh", 180.0);
			}

			/*
			 * Set Hood Angle
			 */
			{
				addToVariables("turret_SetTurretAngle_Angle", 0.0);
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
				addToVariablesA("hood_Pot_Offset", 193.51227018845623);
				addToVariablesB("hood_Pot_Offset", 110.31008456258901);
				
				addToVariables("hood_PotFilter_MaxChange", 4.0);
				addToVariables("hood_PotFilter_Period", (long) 10);

				// PID Control
				addToVariables("hood_Angle_SetPoint", 0.0);
				addToVariables("hood_PID_Tolerance", 0.2);
				addToVariables("hood_PID_KP", 90.0);
				addToVariables("hood_PID_KI", 0.0);
				addToVariables("hood_PID_KD", 0.001);

				// Bangbang Control
				addToVariablesA("hood_Bangbang_OnVoltage", 0.33);
				addToVariablesA("hood_Bangbang_OffVoltage", -0.33);
				
				addToVariablesB("hood_Bangbang_OnVoltage", 0.21);
				addToVariablesB("hood_Bangbang_OffVoltage", -0.21);
				
				addToVariablesA("hood_Bangbang_DownOffset", 0.5);
				addToVariablesB("hood_Bangbang_DownOffset", 0.5);

				addToVariablesB("hood_Pot_BottomThresh", 4.0);
				addToVariablesB("hood_Pot_TopThresh", 65.0);
				
				addToVariablesA("hood_Pot_BottomThresh", 6.6);
				addToVariablesA("hood_Pot_TopThresh", 79.0);
			}

			/*
			 * Set Hood Angle
			 */
			{
				addToVariables("hood_SetHoodAngle_Angle", 0.0);
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
			 * Roll Out
			 */

			{
				addToVariables("transport_RollOut_Speed", 0.5);
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

				addToVariables("climbing_UpSpeed", 1.0); // Check this value
				addToVariables("climbing_DownSpeed", -1.0); // Check this value
				addToVariables("climbing_Setpoint", 900.0);
			}
		}
	}
}
