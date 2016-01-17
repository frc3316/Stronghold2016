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
			/*
			 * Constants
			 */
			addToConstants("JOYSTICK_LEFT", 0);
			addToConstants("JOYSTICK_RIGHT", 1);
			addToConstants("JOYSTICK_OPERATOR", 2);
			addToConstants("DEFENSE_ANGLE_TIMEOUT", 500);
			addToConstants("DEFENSE_ANGLE_RANGE", 10);
			addToConstants("ANGLE_MOVING_AVG_SIZE", 10);
	}
}
