import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Main 
{
	static Config config;
	
	static Scanner reader = new Scanner (System.in);
	
	public static void main (String [] args)
	{
		/*
		config = new Config();
		
		writeConfig();
		transferConfig();
		getLogList();
		*/
		final JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setBounds(0, 0, 200, 200);

		JButton getlogsButton = new JButton("Transfer Logs");
		JButton uploadConfigButton = new JButton("Transfer Config");

		frame.add(panel);
		panel.add(getlogsButton);
		panel.add(uploadConfigButton);
		frame.setVisible(true);

		uploadConfigButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				writeConfig();
				transferConfig();
			}
		});
		
		getlogsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fetchLogs();
			}
		});
		
	}
	
	private static void writeConfig ()
	{
		FileOutputStream out;
		ObjectOutputStream output;
		
		try 
		{
			//Config A writing
			out = new FileOutputStream("C:/config/configFileA.ser");
			output = new ObjectOutputStream(out);
			
			output.writeObject(config.constantsA);
			output.writeObject(config.variablesA);
			
			output.close();
			out.close();
			
			//Config B writing
			out = new FileOutputStream("C:/config/configFileB.ser");
			output = new ObjectOutputStream(out);
			
			output.writeObject(config.constantsB);
			output.writeObject(config.variablesB);
			
			output.close();
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void transferConfig() 
	{
		JSch jsch = new JSch();
		Session session = null;
		
		String host = "roborio-3316-frc.local";
		String user = "admin";
		String pass = "";
		int port = 22;
		String filePathA = "C:/config/configFileA.ser";
		String filePathB = "C:/config/configFileB.ser";
     
		String uploadPathA = "/home/lvuser/config/configFileA.ser";
		String uploadPathB = "/home/lvuser/config/configFileB.ser";
		
		try 
		{
			session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(pass);
            session.connect();
            
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            
            byte[] buffer = new byte[4096];
			BufferedInputStream bis;
			BufferedOutputStream bos;
			int readCount;
			
			try 
			{	
				//Config A transferring
				bis = new BufferedInputStream (new FileInputStream (filePathA));
				bos = new BufferedOutputStream(sftpChannel.put(uploadPathA));
				
				while( (readCount = bis.read(buffer)) > 0) 
				{
					bos.write(buffer, 0, readCount);
				}
				bis.close();
				bos.close();
				
				//Config B transferring
				bis = new BufferedInputStream (new FileInputStream (filePathB));
			 	bos = new BufferedOutputStream(sftpChannel.put(uploadPathB));
				
				while( (readCount = bis.read(buffer)) > 0) 
				{
					bos.write(buffer, 0, readCount);
				}
				
				bis.close();
				bos.close();
	            System.out.println("File uploaded");
			} 
			
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		catch (JSchException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void fetchLogs ()
	{
		JSch jsch = new JSch();
		Session session = null;
		
		String host = "roborio-3316-frc.local";
		String user = "admin";
		String pass = "";
		int port = 22;
		String sourcePath = "/home/lvuser/logs/";
		String destPath = "C:/logs/";
		try 
		{
			session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(pass);
            session.connect();
            
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            
            byte[] buffer = new byte[4096];
			BufferedInputStream bis;
			BufferedOutputStream bos;
			int readCount;
			
			Vector<?> logs = sftpChannel.ls(sourcePath);
			
			sftpChannel.cd(sourcePath);
			
			for (int i = 0; i < logs.size(); i++)
			{
				String fullString = logs.get(i).toString();
				String fileString = fullString.substring(fullString.lastIndexOf(" ")+1);
				if (fullString.contains("logFile") && !fullString.contains("lck"))
				{
					
					/*
					File existingLog = new File(destPath + fileString);
					if (existingLog.exists())
					{
						continue;
					}
					*/
					
					bis = new BufferedInputStream (sftpChannel.get(fileString));
					bos = new BufferedOutputStream(new FileOutputStream (new File(destPath + fileString)));
					
					while((readCount = bis.read(buffer)) > 0) 
					{
						bos.write(buffer, 0, readCount);
					}
					
					System.out.println("Transferred " + fileString);
					bis.close();
					bos.close();
				} else if (fullString.contains("lck")) {
					sftpChannel.rm(fileString);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


