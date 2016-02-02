package org.usfirst.frc.team3316.robot.vision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class VisionNetworker implements Runnable {
	BufferedReader in;

	public void change(Socket s) throws IOException {
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}

	@Override
	public void run() {
		while (true) {
			if (in != null) {
				synchronized (in) {
					while (true) {
						String nextLine = null;
						try {
							nextLine = in.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (nextLine != null) {
							System.out.println(nextLine);
						}
					}
				}
			}
		}
	}

}
