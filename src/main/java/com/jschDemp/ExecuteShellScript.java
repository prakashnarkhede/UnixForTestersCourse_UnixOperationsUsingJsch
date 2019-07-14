package com.jschDemp;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


/**
 * Author: Prakash Narkhede
 * Blog: www.AutomationTalks.com
 * LinkedIn: https://www.linkedin.com/in/panarkhede89/
 */

public class ExecuteShellScript 
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
        try {
            session.connect();
            Channel channel = session.openChannel("exec");
            
            //define input stream and list to read and store the output from shell
            InputStream in = channel.getInputStream();
            List<String> result = new ArrayList<String>();
            
            
            ((ChannelExec) channel).setCommand(command);
            
            // if true ==>  force the pseudo terminal allocation for the "exec" channel
            ((ChannelExec) channel).setPty(false);
            
            //to get error messages in case command execution is unsuccessful
            ((ChannelExec) channel).setErrStream(System.err);
      
            channel.connect();        
                    
         // Read the output from the input stream we set above
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            //Read each line from the buffered reader and add it to result list
            // You can also simple print the result here
            while ((line = reader.readLine()) != null)
            {
            	System.out.println(line);
                result.add(line);
            }
            //retrieve the exit status of the remote command corresponding to this channel
            //0 == success
            //-1 == if the command not yet terminated (could be an error)   
            waitForChannelClosure(channel, 45555);
            int exitStatus = channel.getExitStatus();
            System.out.println("Exit status code: "+exitStatus);
            
            
            if (channel != null) {
            	channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (JSchException e) {
            throw new RuntimeException("Error durring SSH command execution. Command: " + command);
        }
    }
    
    
    
    //wait for shell script to get executed.
    private static void waitForChannelClosure(Channel channel, long maxwaitMs) {

        final long until = System.currentTimeMillis() + maxwaitMs;

        try {
            while (!channel.isClosed() && System.currentTimeMillis() < until) { 
                Thread.sleep(250);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted", e);
        }

        if (!channel.isClosed()) {
            throw new RuntimeException("Channel not closed in timely manner!");
        }

    };
}
