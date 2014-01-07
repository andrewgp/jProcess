package jProcess.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessHandler {

	public static int executeSync(String execPath, String args, List<String> stdOut) throws Exception {
		ProcessBuilder builder = new ProcessBuilder(execPath, args);
		Process process = builder.start();
		
		// read standard out
		InputStream stdOutStream = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdOutStream));
		
		// read lines until process is done
		while (true) {
			String line = reader.readLine();
			if (line != null) {
				stdOut.add(line);
			}
			try {
				return process.exitValue();
			} catch (IllegalThreadStateException ignored) {
			}
		}
	}
}
