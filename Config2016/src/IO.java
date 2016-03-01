import java.util.HashMap;
import java.util.Map;

public class IO
{
	public static Map<String, Integer> pwmA = new HashMap<>();
	public static Map<String, Integer> canA = new HashMap<>();

	public static Map<String, Integer> pwmB = new HashMap<>();
	public static Map<String, Integer> canB = new HashMap<>();

	public static Map<String, Integer> dioA = new HashMap<>();
	public static Map<String, Integer> dioB = new HashMap<>();

	public static Map<String, Integer> aioA = new HashMap<>();
	public static Map<String, Integer> aioB = new HashMap<>();

	public static Map<String, Integer> pdpA = new HashMap<>();
	public static Map<String, Integer> pdpB = new HashMap<>();

	/**
	 * Finds the key that is mapped to a specified channel in the parameter map.
	 * 
	 * @param in
	 *            The map we want to search in.
	 * @param channel
	 *            The specified channel.
	 * @return The key that is mapped to the requested channel. If none exists
	 *         the method returns null.
	 */
	private static Object findKey(Map<?, ?> in, Object value)
	{
		for (Object key : in.keySet())
		{
			if (in.get(key).equals(value))
			{
				return key;
			}
		}
		return null;
	}

	/**
	 * Puts a mapping of a key to a channel in the requested map.
	 * 
	 * @param in
	 *            The map to add the key.
	 * @param name
	 *            The key.
	 * @param channel
	 *            The channel to map the key to.
	 * @throws Exception
	 *             If the channel already has a mapping, throws an exception.
	 */
	private static void put(Map<String, Integer> in, String name, int channel) throws Exception
	{
		if (in.containsValue(channel))
		{
			throw new Exception(
					"Channel " + channel + " for key " + name + " already exists and is: " + findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstants(name, channel);
	}

	/**
	 * Puts a mapping of a key to a channel in the requested map of ROBOT A.
	 * 
	 * @param in
	 *            The map to add the key.
	 * @param name
	 *            The key.
	 * @param channel
	 *            The channel to map the key to.
	 * @throws Exception
	 *             If the channel already has a mapping, throws an exception.
	 */
	private static void putA(Map<String, Integer> in, String name, int channel) throws Exception
	{
		if (in.containsValue(channel))
		{
			throw new Exception(
					"Channel " + channel + " for key " + name + " already exists and is: " + findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstantsA(name, channel);
	}

	/**
	 * Puts a mapping of a key to a channel in the requested map of ROBOT B.
	 * 
	 * @param in
	 *            The map to add the key.
	 * @param name
	 *            The key.
	 * @param channel
	 *            The channel to map the key to.
	 * @throws Exception
	 *             If the channel already has a mapping, throws an exception.
	 */
	private static void putB(Map<String, Integer> in, String name, int channel) throws Exception
	{
		if (in.containsValue(channel))
		{
			throw new Exception(
					"Channel " + channel + " for key " + name + " already exists and is: " + findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstantsB(name, channel);
	}

	/**
	 * Put method for pwm channels on robot A. Read the documentation of the put
	 * method.
	 */
	private static void putPWMA(String name, int channel) throws Exception
	{
		putA(pwmA, name, channel);
	}

	/**
	 * Put method for can channels on robot A. Read the documentation of the put
	 * method.
	 */
	private static void putCANA(String name, int channel) throws Exception
	{
		putA(canA, name, channel);
	}

	/**
	 * Put method for pwm channels on robot B. Read the documentation of the put
	 * method.
	 */
	private static void putPWMB(String name, int channel) throws Exception
	{
		putB(pwmB, name, channel);
	}

	/**
	 * Put method for can channels on robot B. Read the documentation of the put
	 * method.
	 */
	private static void putCANB(String name, int channel) throws Exception
	{
		putB(canB, name, channel);
	}

	/**
	 * Put method for dio channels. Read the documentation of the put method.
	 */
	private static void putDIOA(String name, int channel) throws Exception
	{
		putA(dioA, name, channel);
	}

	/**
	 * Put method for dio channels. Read the documentation of the put method.
	 */
	private static void putDIOB(String name, int channel) throws Exception
	{
		putB(dioB, name, channel);
	}

	/**
	 * Put method for aio channels. Read the documentation of the put method.
	 */
	private static void putAIOA(String name, int channel) throws Exception
	{
		putA(aioA, name, channel);
	}

	/**
	 * Put method for aio channels. Read the documentation of the put method.
	 */
	private static void putAIOB(String name, int channel) throws Exception
	{
		putB(aioB, name, channel);
	}

	/**
	 * Put method for pdp channels. Read the documentation of the put method.
	 */
	private static void putPDPA(String name, int channel) throws Exception
	{
		putA(pdpA, name, channel);
	}

	/**
	 * Put method for pdp channels. Read the documentation of the put method.
	 */
	private static void putPDPB(String name, int channel) throws Exception
	{
		putB(pdpB, name, channel);
	}

	public static void initIO()
	{
		try
		{
			/*
			 * PWM and CAN initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
					putPWMA("CHASSIS_MOTOR_LEFT_1", 3);
					putCANA("CHASSIS_MOTOR_LEFT_2", 14);
					putPWMA("CHASSIS_MOTOR_RIGHT_1", 2);
					putCANA("CHASSIS_MOTOR_RIGHT_2", 15);

					putPWMA("INTAKE_MOTOR", 4);

					putPWMA("TRANSPORT_MOTOR", 7); //should be PWMA 6

					putPWMA("FLYWHEEL_MOTOR", 8); //should be PWMA

					putPWMA("TURRET_MOTOR", 0);

					putPWMA("HOOD_MOTOR", 1);

					putCANA("CLIMBING_MOTOR_1", 7);
					putPWMA("CLIMBING_MOTOR_2", 6);
					putPWMA("CLIMBING_MOTOR_3", 5);
					putCANA("CLIMBING_MOTOR_4", 6);

					putPWMA("SPARE_MOTOR", 9);
				}

				/*
				 * Robot B
				 */
				{
					putCANB("CHASSIS_MOTOR_LEFT_1", 12);
					putCANB("CHASSIS_MOTOR_LEFT_2", 11);
					putCANB("CHASSIS_MOTOR_RIGHT_1", 0);
					putPWMB("CHASSIS_MOTOR_RIGHT_2", 3);

					putPWMB("INTAKE_MOTOR", 9);

					putCANB("TRANSPORT_MOTOR", 1);

					putCANB("FLYWHEEL_MOTOR", 2);

					putPWMB("TURRET_MOTOR", 8);

					putCANB("HOOD_MOTOR", 4);

					putPWMB("CLIMBING_MOTOR_1", 10);
					putCANB("CLIMBING_MOTOR_2", 13);
					putCANB("CLIMBING_MOTOR_3", 14);
					putCANB("CLIMBING_MOTOR_4", 15);

					putPWMB("SPARE_MOTOR", 5);
				}
			}

			/*
			 * DIO initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
					putDIOA("CHASSIS_LEFT_ENCODER_CHANNEL_A", 2);
					putDIOA("CHASSIS_LEFT_ENCODER_CHANNEL_B", 3);

					putDIOA("CHASSIS_RIGHT_ENCODER_CHANNEL_A", 0);
					putDIOA("CHASSIS_RIGHT_ENCODER_CHANNEL_B", 1);

					putDIOA("INTAKE_SWITCH", 12);

					putDIOA("CLIMBING_SWITCH", 9);

					putDIOA("FLYWHEEL_HALL_EFFECT_COUNTER", 8);
				}

				/*
				 * Robot B
				 */
				{
					putDIOB("CHASSIS_LEFT_ENCODER_CHANNEL_A", 2);
					putDIOB("CHASSIS_LEFT_ENCODER_CHANNEL_B", 3);

					putDIOB("CHASSIS_RIGHT_ENCODER_CHANNEL_A", 0);
					putDIOB("CHASSIS_RIGHT_ENCODER_CHANNEL_B", 1);

					putDIOB("INTAKE_SWITCH", 5);
 
					putDIOB("CLIMBING_SWITCH", 6);

					putDIOB("FLYWHEEL_HALL_EFFECT_COUNTER", 7);
				}
			}

			/*
			 * AIO initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
					putAIOA("TURRET_POT", 0);

					putAIOA("HOOD_POT", 1);
					
					putAIOA("CLIMBING_POT", 3);
				}

				/*
				 * Robot B
				 */
				{
					putAIOB("TURRET_POT", 0);

					putAIOB("HOOD_POT", 1);
					
					putAIOB("CLIMBING_POT", 3);
				}
			}

			/*
			 * PDP initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
					putPDPA("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL", 2);
					putPDPA("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL", 3);

					putPDPA("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL", 4);
					putPDPA("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL", 5); 

					putPDPA("INTAKE_MOTOR_PDP_CHANNEL", 11);

					putPDPA("TRANSPORT_MOTOR_PDP_CHANNEL", 10); 

					putPDPA("FLYWHEEL_MOTOR_PDP_CHANNEL", 15); 

					putPDPA("TURRET_MOTOR_PDP_CHANNEL", 0);

					putPDPA("HOOD_MOTOR_PDP_CHANNEL", 1); 

					putPDPA("CLIMBING_MOTOR_1_PDP_CHANNEL", 8); 
					putPDPA("CLIMBING_MOTOR_2_PDP_CHANNEL", 13);
					putPDPA("CLIMBING_MOTOR_3_PDP_CHANNEL", 14);
					putPDPA("CLIMBING_MOTOR_4_PDP_CHANNEL", 6); 

					putPDPA("SPARE_MOTOR_PDP_CHANNEL", 7);
				}

				/*
				 * Robot B
				 */
				{
					putPDPB("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL", 11);
					putPDPB("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL", 12);

					putPDPB("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL", 0);
					putPDPB("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL", 3);

					putPDPB("INTAKE_MOTOR_PDP_CHANNEL", 9);

					putPDPB("TRANSPORT_MOTOR_PDP_CHANNEL", 1);

					putPDPB("FLYWHEEL_MOTOR_PDP_CHANNEL", 2);

					putPDPB("TURRET_MOTOR_PDP_CHANNEL", 8);

					putPDPB("HOOD_MOTOR_PDP_CHANNEL", 4);

					putPDPB("CLIMBING_MOTOR_1_PDP_CHANNEL", 10);
					putPDPB("CLIMBING_MOTOR_2_PDP_CHANNEL", 13);
					putPDPB("CLIMBING_MOTOR_3_PDP_CHANNEL", 14);
					putPDPB("CLIMBING_MOTOR_4_PDP_CHANNEL", 15);

					putPDPB("SPARE_MOTOR_PDP_CHANNEL", 5);
				}
			}

			/*
			 * PCM initialization
			 */
			{
				/*
				 * Robot A
				 */
				{
					Config.addToConstantsA("CHASSIS_LONG_PISTONS_MODULE", 0);

					Config.addToConstantsA("CHASSIS_LONG_PISTONS_FORWARD", 0);
					Config.addToConstantsA("CHASSIS_LONG_PISTONS_REVERSE", 1);

					Config.addToConstantsA("CHASSIS_SHORT_PISTONS_LEFT_MODULE", 0);

					Config.addToConstantsA("CHASSIS_SHORT_PISTONS_LEFT_FORWARD", 3);
					Config.addToConstantsA("CHASSIS_SHORT_PISTONS_LEFT_REVERSE", 2);

					Config.addToConstantsA("CHASSIS_SHORT_PISTONS_RIGHT_MODULE", 1); // FAKE
					
					Config.addToConstantsA("CHASSIS_SHORT_PISTONS_RIGHT_FORWARD", 4);
					Config.addToConstantsA("CHASSIS_SHORT_PISTONS_RIGHT_REVERSE", 5);

					Config.addToConstantsA("INTAKE_SOLENOID_MODULE", 0);

					Config.addToConstantsA("INTAKE_SOLENOID_FORWARD", 4);
					Config.addToConstantsA("INTAKE_SOLENOID_REVERSE", 5);

					Config.addToConstantsA("CLIMBING_SOLENOID_MODULE", 1); // FAKE

					Config.addToConstantsA("CLIMBING_SOLENOID_FORWARD", 6);
					Config.addToConstantsA("CLIMBING_SOLENOID_REVERSE", 7);
				}

				/*
				 * Robot B
				 */
				{
					Config.addToConstantsB("CHASSIS_LONG_PISTONS_MODULE", 0);
					
					Config.addToConstantsB("CHASSIS_LONG_PISTONS_FORWARD", 5);
					Config.addToConstantsB("CHASSIS_LONG_PISTONS_REVERSE", 1);

					Config.addToConstantsB("CHASSIS_SHORT_PISTONS_LEFT_MODULE", 0);

					Config.addToConstantsB("CHASSIS_SHORT_PISTONS_LEFT_FORWARD", 6);
					Config.addToConstantsB("CHASSIS_SHORT_PISTONS_LEFT_REVERSE", 2);

					Config.addToConstantsB("CHASSIS_SHORT_PISTONS_RIGHT_MODULE", 0);

					Config.addToConstantsB("CHASSIS_SHORT_PISTONS_RIGHT_FORWARD", 0);
					Config.addToConstantsB("CHASSIS_SHORT_PISTONS_RIGHT_REVERSE", 7);

					Config.addToConstantsB("INTAKE_SOLENOID_MODULE", 0);

					Config.addToConstantsB("INTAKE_SOLENOID_FORWARD", 3);
					Config.addToConstantsB("INTAKE_SOLENOID_REVERSE", 4);

					Config.addToConstantsB("CLIMBING_SOLENOID_MODULE", 1);

					Config.addToConstantsB("CLIMBING_SOLENOID_FORWARD", 6);
					Config.addToConstantsB("CLIMBING_SOLENOID_REVERSE", 7);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
