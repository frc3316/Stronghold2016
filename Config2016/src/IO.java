import java.util.HashMap;
import java.util.Map;

public class IO
{
	public static Map<String, Integer> pwm = new HashMap<>();
	public static Map<String, Integer> dio = new HashMap<>();
	public static Map<String, Integer> aio = new HashMap<>();
	public static Map<String, Integer> pdp = new HashMap<>();

	private static String find(Map<String, Integer> in, int channel)
	{
		for (String key : in.keySet())
		{
			if (in.get(key).equals(channel))
			{
				return key;
			}
		}
		return null;
	}

	private static void put(String inWat, Map<String, Integer> in, String name, int channel) throws Exception
	{
		if (in.containsValue(channel))
		{
			throw new Exception("Channel " + channel + " for key " + name + " already exists in "
		+ inWat + " and is: " + find(in, channel));
		}
		in.put(name, channel);
		Config.addToConstants(name, channel);
	}
}
