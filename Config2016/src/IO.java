import java.util.HashMap;
import java.util.Map;

public class IO
{
	public static Map<String, Integer> pwm = new HashMap<>();
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
			throw new Exception(
					"Channel " + channel + " for key " + name + " already exists and is: " + findKey(in, channel));
		}

		in.put(name, channel);
		Config.addToConstants(name, channel);
	}

	/**
	 * Put method for pwm channels. Read the documentation of the put method.
	 */
	private static void putPWM(String name, int channel) throws Exception
	{
		put(pwm, name, channel);
	}

	/**
	 * Put method for pwm channels. Read the documentation of the put method.
	 */
	private static void putDIO(String name, int channel) throws Exception
	{
		put(dio, name, channel);
	}

	/**
	 * Put method for pwm channels. Read the documentation of the put method.
	 */
	private static void putAIO(String name, int channel) throws Exception
	{
		put(aio, name, channel);
	}

	/**
	 * Put method for pwm channels. Read the documentation of the put method.
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
			 * PWM initialization
			 */
			{
				putPWM("CHASSIS_MOTOR_LEFT_1", 5);
				putPWM("CHASSIS_MOTOR_LEFT_2", 6);
				putPWM("CHASSIS_MOTOR_RIGHT_1", 1);
				putPWM("CHASSIS_MOTOR_RIGHT_2", 2);
				
				putPWM("INTAKE_MOTOR", 3);
				
				putPWM("TRANSPORT_MOTOR", 8);
				
				putPWM("FLYWHEEL_MOTOR", 4);
				
				putPWM("TURRET_MOTOR", 0);
				
				putPWM("HOOD_MOTOR", 16);
				
				putPWM("CLIMBING_MOTOR_1", 17);
				putPWM("CLIMBING_MOTOR_2", 18);
				putPWM("CLIMBING_MOTOR_3", 19);
				putPWM("CLIMBING_MOTOR_4", 15);
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
				putPDP("CHASSIS_MOTOR_LEFT_1_PDP_CHANNEL", 0);
				putPDP("CHASSIS_MOTOR_LEFT_2_PDP_CHANNEL", 1);

				putPDP("CHASSIS_MOTOR_RIGHT_1_PDP_CHANNEL", 2);
				putPDP("CHASSIS_MOTOR_RIGHT_2_PDP_CHANNEL", 3);
				
				putPDP("INTAKE_MOTOR_PDP_CHANNEL", 4);
				
				putPDP("TRANSPORT_MOTOR_PDP_CHANNEL", 5);
				
				putPDP("FLYWHEEL_MOTOR_PDP_CHANNEL", 6);
				
				putPDP("TURRET_MOTOR_PDP_CHANNEL", 7);
				
				putPDP("HOOD_MOTOR_PDP_CHANNEL", 8);
				
				putPDP("CLIMBING_MOTOR_1_PDP_CHANNEL", 10);
				putPDP("CLIMBING_MOTOR_2_PDP_CHANNEL", 11);
				putPDP("CLIMBING_MOTOR_3_PDP_CHANNEL", 12);
				putPDP("CLIMBING_MOTOR_4_PDP_CHANNEL", 13);
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
