package jProcess.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessHandler {
	
	public static int executeSync(String execPath, String arg, List<String> stdOut) {
		List<String> args = new ArrayList<String>(1);
		args.add(arg);
		return executeSync(execPath, args, stdOut);
	}

	public static int executeSync(String execPath, List<String> args, List<String> stdOut) {
		try {
			List<String> cmd = new ArrayList<String>(args != null ? args.size() + 1 : 1);
			cmd.add(execPath);
			if (args != null) {
				cmd.addAll(args);
			}
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			
			// read standard out
			InputStream stdOutStream = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdOutStream));
			
			// read lines until process is done
			int returnCode = -1;
			while (true) {
				String line = reader.readLine();
				if (line != null) {
					stdOut.add(line);
				}
				if (returnCode != -1) {
					if (line == null) {
						return returnCode;
					}
				} else {
					try {
						returnCode = process.exitValue();
					} catch (IllegalThreadStateException ignored) {
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
