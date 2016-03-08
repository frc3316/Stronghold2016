import java.util.Hashtable;

import javax.xml.ws.soap.AddressingFeature;

public class Config 
{
	public static Hashtable<String, Object> variablesB;
	public static Hashtable<String, Object> constantsB;

	public static Hashtable<String, Object> variablesA;
	public static Hashtable<String, Object> constantsA;

	private Config() {}

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
		System.out.println("Trying to add to constants A: key " + key + " value " + value);

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
		System.out.println("Trying to add to variables A: key " + key + " value " + value);

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
		System.out.println("Trying to add to constants B: key " + key + " value " + value);

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
		System.out.println("Trying to add to variables B: key " + key + " value " + value);

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
	private static void initConfig() {
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
					addToVariables("button_Toggle_Omni_1", 1);
					addToVariables("button_Toggle_Omni_2", 11);

					// Joystick operator

					addToVariables("button_Intake_Toggle", 3);
					addToVariables("button_Collect_Ball", 1);
					addToVariables("button_Roll_In", 8);
					addToVariables("button_Roll_Out", 2);
					addToVariables("button_Allow_Climbing", 7);
					addToVariables("button_Pull_Up", 0);
					addToVariables("button_Warm_Up_Shooter", 5);
					addToVariables("button_Shooting_Trigger", 4);
					addToVariables("button_Warm_Up_Flywheel", 6);
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
				addToConstantsB("CHASSIS_MOTOR_LEFT_REVERSE", true);
				addToConstantsB("CHASSIS_MOTOR_RIGHT_REVERSE", true);
				
				addToConstantsA("CHASSIS_MOTOR_LEFT_REVERSE", true);
				addToConstantsA("CHASSIS_MOTOR_RIGHT_REVERSE", false);

				addToConstantsA("CHASSIS_LEFT_ENCODER_REVERSE", true);
				addToConstantsA("CHASSIS_RIGHT_ENCODER_REVERSE", false);

				// For some reason the encoders give 4 times less the correct
				// speed
				addToConstants("CHASSIS_LEFT_ENCODER_DISTANCE_PER_PULSE", (4 * (6 * Math.PI) / 32) * 0.0254);
				addToConstants("CHASSIS_RIGHT_ENCODER_DISTANCE_PER_PULSE", (4 * (6 * Math.PI) / 32) * 0.0254);

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
				addToConstantsA("FLYWHEEL_MOTOR_REVERSE", false);
				addToConstantsB("FLYWHEEL_MOTOR_REVERSE", true);

				addToConstants("FLYWHEEL_MOTOR_MAX_CURRENT", 70.0);

				/*
				 * Turret
				 */
				addToConstants("TURRET_MOTOR_REVERSE", false);

				addToConstants("TURRET_MOTOR_MAX_CURRENT", 50.0); // TODO: Check
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

				addToConstantsA("HOOD_POT_FULL_RANGE", -300.0); // negative to
																// flip
																// incrementing
																// direction
				addToConstantsB("HOOD_POT_FULL_RANGE", -270.0); // negative to
																// flip
																// incrementing
																// direction

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
				addToConstants("CHASSIS_ANGLE_MOVING_AVG_SIZE", 45);
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
			 * Cross Defense
			 */
			{
				addToConstants("chassis_Defense_Angle_Timeout", 750.0); // In milliseconds

				addToVariables("chassis_CrossDefense_Voltage", 0.65);
				addToVariables("chassis_CrossDefense_MinSpeed", 0.5);
				addToVariables("chassis_CrossDefense_DownV", 0.001);
				
				addToVariables("chassis_Defense_Pitch_Thresh", 4.0);
				addToVariables("chassis_Defense_Roll_Thresh", 4.0);

				addToVariables("chassis_CrossBrake_Timeout", 0.0);
				addToVariables("chassis_CrossDefense_BrakeV", -0.0);
			}
			
			/*
			 * Reach Defense
			 */
			{
				addToVariables("chassis_ReachDefense_Speed", 0.7);
				addToVariables("chassis_ReachDefense_Timeout", 0.5);
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
			 * Drive Distance (using camera)
			 */
			{
				addToVariables("chassis_DriveDistanceCamera_Speed", 0.5);
			}
			
			/*
			 * Drive Distance Camera
			 */
			{
				
			}
			
			/*
			 * Autonomous
			 */
			{
				addToVariables("chassis_Autonomous_Distance", 0.5); // In meters
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
				addToVariables("flywheel_PID_KP", 180.0);
				addToVariables("flywheel_PID_KI", 332.5);
				addToVariables("flywheel_PID_KD", 83.125); // Calibrated using
															// Z-N method
				addToVariables("flywheel_PID_KF", 0.0);
			}

			/*
			 * Accelerate Flywheel
			 */
			{
				addToVariables("flywheel_AccelerateFlywheel_Timeout", 0.15);
			}
		}

		/*
		 * Shooter
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
				addToVariablesA("triggetShooting_IntakeRollIn_Timeout", 0.25);
				addToVariablesB("triggetShooting_IntakeRollIn_Timeout", 0.25);

				addToVariablesA("triggetShooting_IntakeRollOut_Timeout", 0.5);
				addToVariablesB("triggetShooting_IntakeRollOut_Timeout", 0.5);
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
				addToVariables("intake_RollOut_Speed", -1.0);
				addToVariablesA("intake_CloseIntake_Timeout", 2.5); // In
																	// seconds
				addToVariablesB("intake_CloseIntake_Timeout", 2.5); // In
																	// seconds
			}

			/*
			 * Collect Ball
			 */
			{
				addToVariablesA("collectBall_CloseIntake_Timeout", 1.642); // In
																			// seconds
				addToVariablesB("collectBall_CloseIntake_Timeout", 1.642); // In
																			// seocnds
			}

			/*
			 * Eject Ball
			 */
			{
				addToVariablesA("ejectBall_WaitForBallIn_Timeout", 0.5); // In
																			// seconds
				addToVariablesB("ejectBall_WaitForBallIn_Timeout", 0.5); // In
																			// seconds
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
				addToVariablesA("turret_Pot_Offset", 632.8565501587938);
				addToVariablesB("turret_Pot_Offset", 399.4483151915127);

				addToVariables("turret_PotFilter_MaxChange", 4.0);
				addToVariables("turret_PotFilter_Period", (long) 10);

				// PID Control
				addToVariables("turret_Angle_SetPoint", 0.0);
				addToVariables("turret_PID_Tolerance", 0.6);

				addToVariables("turret_PID_KP", 20.0);
				addToVariables("turret_PID_KI", 2.0);
				addToVariables("turret_PID_KD", 50.0);

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
				addToVariablesA("hood_Pot_Offset", 170.574220260749);
				addToVariablesB("hood_Pot_Offset", 103.00425942695641);

				addToVariables("hood_PotFilter_MaxChange", 4.0);
				addToVariables("hood_PotFilter_Period", (long) 10);

				// PID Control
				addToVariables("hood_Angle_SetPoint", 0.0);
				addToVariables("hood_PID_Tolerance", 0.55);
				addToVariables("hood_PID_KP", 96.0);
				addToVariables("hood_PID_KI", 0.0);
				addToVariables("hood_PID_KD", 168.75); // Calculated using Z-N
														// method

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
			 * Hood Tables
			 */
			{
				addToVariablesA("alignShooter_HoodTable",
						new double[][] { { 150.0, 200.0, 250.0, 300.0, 350.0, 400.0, 450.0, 500.0, 550.0 },
								{ 40.0, 58.7, 63.0, 66.5, 65.5, 65.0, 62.0, 58.625, 49.5 } });
				
				addToVariablesB("alignShooter_HoodTable",
						new double[][] { { 205.0, 255.0, 310.0, 357.0, 417.0, 475.0, 510.0, 560.0 },
								{ 38.7, 50.3, 53.1, 52.0, 52.0, 49.7, 45.6, 40.0 } });
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

				addToVariables("climbing_UpSpeed", -1.0); // Check this value
				addToVariables("climbing_DownSpeed", 1.0); // Check this value
				addToVariables("climbing_Setpoint", 900.0);
			}
		}
	}
}
