package com.jschDemo;


import java.util.Vector;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Author: Prakash Narkhede
 * Blog: www.AutomationTalks.com
 * LinkedIn: https://www.linkedin.com/in/panarkhede89/
 */

public class verifyIfFileIsPresent 
{
	public static void main( String[] args ) throws Exception
	{
		JSch jsch = new JSch();
		Session session = null;
		
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
		String command = "./script.sh";
		session.connect();
		Channel channel = session.openChannel("sftp");

		channel.connect();        

		ChannelSftp sftp = (ChannelSftp) channel;

		Vector<LsEntry> Flist = sftp.ls("/home/prakash");
		final int fileCount = Flist.size();
		for(int i = 0; i < fileCount; i++) {
			LsEntry entry = Flist.get(i);

			if(entry.getFilename().equalsIgnoreCase("inputFile.txt1")) {
				System.out.println("File is present");
				channel.disconnect();
				session.disconnect();
				break;						
			}

			if(i == fileCount-1) {
				System.out.println("File is not present");
				channel.disconnect();
				session.disconnect();
				break;
			}
		}



	}

}
