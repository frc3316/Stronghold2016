import java.util.Hashtable;

public class Config {
	public Hashtable<String, Object> variablesB;
	public Hashtable<String, Object> constantsB;

	public Hashtable<String, Object> variablesA;
	public Hashtable<String, Object> constantsA;

	public Config() {
		variablesB = new Hashtable<String, Object>();
		constantsB = new Hashtable<String, Object>();

		variablesA = new Hashtable<String, Object>();
		constantsA = new Hashtable<String, Object>();

		initConfig();
	}

	private void addToConstantsA(String key, Object value) {
		if (constantsA.containsKey(key)) {
			constantsA.replace(key, value);
		} else {
			constantsA.put(key, value);
		}
	}

	private void addToVariablesA(String key, Object value) {
		if (variablesA.containsKey(key)) {
			variablesA.replace(key, value);
		} else {
			variablesA.put(key, value);
		}
	}

	private void addToConstantsB(String key, Object value) {
		if (constantsB.containsKey(key)) {
			constantsB.replace(key, value);
		} else {
			constantsB.put(key, value);
		}
	}

	private void addToVariablesB(String key, Object value) {
		if (variablesB.containsKey(key)) {
			variablesB.replace(key, value);
		} else {
			variablesB.put(key, value);
		}
	}

	private void addToConstants(String key, Object value) {
		addToConstantsA(key, value);
		addToConstantsB(key, value);
	}

	private void addToVariables(String key, Object value) {
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
	private void initConfig() {
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
				
				/*
				 * Intake
				 */
				addToConstants("INTAKE_SOLENOID_FORWARD_CHANNEL", 0);
				addToConstants("INTAKE_SOLENOID_REVERSE_CHANNEL", 1);
				addToConstants("INTAKE_MOTOR", 0);
				
				addToConstants("INTAKE_LS", 0);
				addToConstants("INTAKE_RS", 1);
				addToConstants("INTAKE_TS", 4);
				addToConstants("INTAKE_BS", 5);
				addToConstants("INTAKE_POT", 6);
				addToConstants("INTAKE_POT_FULL_RANGE", 270.0);
				addToConstants("INTAKE_POT_OFFSET", 0.0);
				addToConstants("INTAKE_POT_LOW_TRESH", 1.0);
				addToConstants("INTAKE_POT_HIGH_TRESH", 130.5);
				
				/*
				 * Transport
				 */
				addToConstants("TRANSPORT_MOTOR", 3);
				
				addToConstants("TRANSPORT_ENCODER_A", 2);
				addToConstants("TRANSPORT_ENCODER_B", 3);
				addToConstants("TRANSPORT_ENCODER_REVERSE_DIRECTION", false);
			}
		}
		
		/*
		 * Chassis
		 */
		{
			/*
			 * Variables
			 */
			{
				addToVariables("chassis_TankDrive_DeadBand", 0.05);
				
				addToVariables("chassis_TankDrive_InvertX", false);
				addToVariables("chassis_TankDrive_InvertY", true);
			}
			
			/*
			 * Constants
			 */
			{
				addToConstants("CHASSIS_DEFENSE_ANGLE_TIMEOUT", 500.0);
				addToConstants("CHASSIS_DEFENSE_ANGLE_RANGE", 4.0);
				addToConstants("CHASSIS_ANGLE_MOVING_AVG_SIZE", 10);
			}
		}
	}
}
