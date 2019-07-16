package com.jschDemo;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Author: Prakash Narkhede
 * Blog: www.AutomationTalks.com
 * LinkedIn: https://www.linkedin.com/in/panarkhede89/
 */

public class downloadFileFromUnixServer 
{
	public static void main( String[] args ) throws Exception
	{
		System.out.println( "Execution started !!!!!" );
		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;
		ChannelSftp sftp = null;
		
        //if your unix server need private key, then use addIdentity method with keypath
		String privateKeyPath = "G:\\1. My Cources\\UNIX for Testers\\key.ppk";
		try {
			jsch.addIdentity(privateKeyPath);        
			session = jsch.getSession("prakash", "34.68.117.110", 22);

			//to set password (if your server does not use private keys)
			//  session.setPassword("");   --> not req for my case

			//to set preferred authentication method
			// session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			java.util.Properties config = new java.util.Properties(); 

			//disabling strict host key checking
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
		} catch (JSchException e) {
			throw new RuntimeException("Failed to create Jsch Session object.", e);
		}
		try {
			session.connect();
			channel = session.openChannel("sftp");

			channel.connect();        

			sftp = (ChannelSftp) channel;

			sftp.get("/home/prakash/Filetoupload.txt", "G:\\FileDownload.txt");

		} catch(Exception e) 
		{
			System.out.println(e.getMessage());
		} finally {

			System.out.println("Execution completed !!!!!!");

			// Validating if channel sftp is not null to exit
			if (sftp != null) {
				sftp.exit();
				System.out.println("sftp exited !!!!");
			}
			// Validating if channel is not null to disconnect
			if (channel != null) {
				channel.disconnect();
				System.out.println("Channel disconnected !!!!");
			}
			// Validating if session instance is not null to disconnect
			if (session != null) {
				session.disconnect();
				System.out.println("Session disconnected !!!!");

			}    
		}
	}
}
