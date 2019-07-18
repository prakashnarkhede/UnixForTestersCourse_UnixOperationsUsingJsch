
package com.puttyExecute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

public class AutomateUnixProcessesUsingPutty {

	public static void main(String[] args) throws IOException, Exception {
		
		///// Using plexus utils library
		Commandline cmd = new Commandline();
		
		cmd.setExecutable(System.getProperty("user.dir")+"//src//main//Resources//sendFileToHost.bat");

		WriterStreamConsumer systemOut = new WriterStreamConsumer(
	             new OutputStreamWriter(System.out));
	 
	 WriterStreamConsumer systemErr = new WriterStreamConsumer(
	             new OutputStreamWriter(System.out));
	 
	 int returnCode = CommandLineUtils.executeCommandLine(cmd, systemOut, systemErr);
	 if (returnCode != 0) {
	     System.out.println("Non Zero return code - Unsuccessful Execution");
	 } else {
	     System.out.println("Zero return code - Successful Execution");
	 }
	 
	 
	//Second way - Mostly used - Using processBuilder
	ProcessBuilder processBuilder = new ProcessBuilder(System.getProperty("user.dir")+"//src//main//Resources//sendFileToHost.bat");
    try {

        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println(output);
   	     System.out.println("Zero return code - Successful Execution");
            System.exit(0);
        } else {
        }

    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}
}
