import java.util.HashMap;
import java.util.Map;

public class IO
{
	public static Map<String, Integer> pwmA = new HashMap<>();
	public static Map<String, Integer> canA = new HashMap<>();

	public static Map<String, Integer> pwmB = new HashMap<>();
	public static Map<String, Integer> canB = new HashMap<>();

	public static Map<String, Integer> dio = new HashMap<>();
	public static Map<String, Integer> aio = new HashMap<>();
	public static Map<String, Integer> pdp = new HashMap<>();

	/**
	 * Finds the key that is mapped to a specified channel in the parameter map.
	 * 
	 * @param in
	 *            The map we want to search in.
	 * @param channel
	 *            The specified channel.
	 * @return The key that is mapped to the requested channel. If none exists the method returns null.
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
			throw new Exception("Channel " + channel + " for key " + name + " already exists and is: "
					+ findKey(in, channel));
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
			throw new Exception("Channel " + channel + " for key " + name + " already exists and is: "
					+ findKey(in, channel));
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
			throw new Exception("Channel " + channel + " for key " + name + " already exists and is: "
					+ findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstantsB(name, channel);
	}

	/**
	 * Put method for pwm channels on robot A. Read the documentation of the put method.
	 */
	private static void putPWMA(String name, int channel) throws Exception
	{
		putA(pwmA, name, channel);
	}

	/**
	 * Put method for can channels on robot A. Read the documentation of the put method.
	 */
	private static void putCANA(String name, int channel) throws Exception
	{
		putA(canA, name, channel);
	}

	/**
	 * Put method for pwm channels on robot B. Read the documentation of the put method.
	 */
	private static void putPWMB(String name, int channel) throws Exception
	{
		putB(pwmB, name, channel);
	}

	/**
	 * Put method for can channels on robot B. Read the documentation of the put method.
	 */
	private static void putCANB(String name, int channel) throws Exception
	{
		putB(canB, name, channel);
	}

	/**
	 * Put method for dio channels. Read the documentation of the put method.
	 */
	private static void putDIO(String name, int channel) throws Exception
	{
		put(dio, name, channel);
	}

	/**
	 * Put method for aio channels. Read the documentation of the put method.
	 */
	private static void putAIO(String name, int channel) throws Exception
	{
		put(aio, name, channel);
	}

	/**
	 * Put method for pdp channels. Read the documentation of the put method.
	 */
	private static void putPDP(String name, int channel) throws Exception
	{
		put(pdp, name, channel);
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
					putPWMA("CHASSIS_MOTOR_LEFT_1", 4);
					putPWMA("CHASSIS_MOTOR_LEFT_2", 5);
					putPWMA("CHASSIS_MOTOR_RIGHT_1", 8);
					putPWMA("CHASSIS_MOTOR_RIGHT_2", 9);

					putPWMA("INTAKE_MOTOR", 6);

					putCANA("TRANSPORT_MOTOR", 0);

					putCANA("FLYWHEEL_MOTOR", 1);

					putCANA("TURRET_MOTOR", 2);

					putCANA("HOOD_MOTOR", 3);

					putPWMA("CLIMBING_MOTOR_1", 0);
					putPWMA("CLIMBING_MOTOR_2", 1);
					putPWMA("CLIMBING_MOTOR_3", 2);
					putPWMA("CLIMBING_MOTOR_4", 3);
					
					putCANA("SPARE_MOTOR", 15);
				}
				
				/*
				 * Robot B
				 */
				{
					putPWMB("CHASSIS_MOTOR_LEFT_1", 4);
					putPWMB("CHASSIS_MOTOR_LEFT_2", 5);
					putPWMB("CHASSIS_MOTOR_RIGHT_1", 8);
					putPWMB("CHASSIS_MOTOR_RIGHT_2", 9);

					putPWMB("INTAKE_MOTOR", 0);

					putCANB("TRANSPORT_MOTOR", 0);

					putCANB("FLYWHEEL_MOTOR", 1);

					putCANB("TURRET_MOTOR", 2);

					putCANB("HOOD_MOTOR", 3);

					putCANB("CLIMBING_MOTOR_1", 11);
					putCANB("CLIMBING_MOTOR_2", 12);
					putCANB("CLIMBING_MOTOR_3", 13);
					putCANB("CLIMBING_MOTOR_4", 14);
					
					putCANB("SPARE_MOTOR", 15);
				}
			}

			/*
			 * DIO initialization
			 */
			{
				putDIO("CHASSIS_LEFT_ENCODER_CHANNEL_A", 4);
				putDIO("CHASSIS_LEFT_ENCODER_CHANNEL_B", 5);

				putDIO("CHASSIS_RIGHT_ENCODER_CHANNEL_A", 2);
				putDIO("CHASSIS_RIGHT_ENCODER_CHANNEL_B", 3);

				putDIO("CHASSIS_HALL_EFFECT_LEFT_FRONT", 12);
				putDIO("CHASSIS_HALL_EFFECT_LEFT_BACK", 13);
				putDIO("CHASSIS_HALL_EFFECT_RIGHT_FRONT", 11);
				putDIO("CHASSIS_HALL_EFFECT_RIGHT_BACK", 10);

				putDIO("INTAKE_LEFT_SWITCH", 8);
				putDIO("INTAKE_RIGHT_SWITCH", 9);

				putDIO("TRANSPORT_ENCODER_A", 6);
				putDIO("TRANSPORT_ENCODER_B", 7);

				putDIO("CLIMBING_SWITCH", 1);

				putDIO("FLYWHEEL_HALL_EFFECT_COUNTER", 0);
			}

			/*
			 * AIO initialization
			 */
			{
				putAIO("INTAKE_POT", 0);

				putAIO("TURRET_POT", 1);

				putAIO("HOOD_POT", 2);

				putAIO("CLIMBING_POT", 3);
			}

			/*
			 * PDP initialization
			 */
			{
				putPDP("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL", 4);
				putPDP("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL", 5);

				putPDP("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL", 8);
				putPDP("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL", 9);

				putPDP("INTAKE_MOTOR_PDP_CHANNEL", 10);

				putPDP("TRANSPORT_MOTOR_PDP_CHANNEL", 0);

				putPDP("FLYWHEEL_MOTOR_PDP_CHANNEL", 1);

				putPDP("TURRET_MOTOR_PDP_CHANNEL", 2);

				putPDP("HOOD_MOTOR_PDP_CHANNEL", 3);

				putPDP("CLIMBING_MOTOR_1_PDP_CHANNEL", 11);
				putPDP("CLIMBING_MOTOR_2_PDP_CHANNEL", 12);
				putPDP("CLIMBING_MOTOR_3_PDP_CHANNEL", 13);
				putPDP("CLIMBING_MOTOR_4_PDP_CHANNEL", 14);
				
				putPDP("SPARE_MOTOR_PDP_CHANNEL", 15);
			}

			/*
			 * PCM initialization
			 */
			{
				// Module 1
				Config.addToConstants("CHASSIS_LONG_PISTONS_MODULE", 1);
				Config.addToConstants("CHASSIS_SHORT_PISTONS_LEFT_MODULE", 1);
				Config.addToConstants("CHASSIS_SHORT_PISTONS_RIGHT_MODULE", 1);
				Config.addToConstants("CLIMBING_SOLENOID_MODULE", 1);

				Config.addToConstants("CHASSIS_LONG_PISTONS_FORWARD", 0);
				Config.addToConstants("CHASSIS_LONG_PISTONS_REVERSE", 1);

				Config.addToConstants("CHASSIS_SHORT_PISTONS_LEFT_FORWARD", 2);
				Config.addToConstants("CHASSIS_SHORT_PISTONS_LEFT_REVERSE", 3);

				Config.addToConstants("CHASSIS_SHORT_PISTONS_RIGHT_FORWARD", 4);
				Config.addToConstants("CHASSIS_SHORT_PISTONS_RIGHT_REVERSE", 5);

				Config.addToConstants("CLIMBING_SOLENOID_FORWARD", 6);
				Config.addToConstants("CLIMBING_SOLENOID_REVERSE", 7);

				// Module 0
				Config.addToConstants("INTAKE_SOLENOID_MODULE", 0);

				Config.addToConstants("INTAKE_SOLENOID_FORWARD", 6);
				Config.addToConstants("INTAKE_SOLENOID_REVERSE", 7);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
