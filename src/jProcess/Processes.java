package jProcess;

import jProcess.linux.LinuxProcessDetailsFactory;
import jProcess.util.OS;

import java.util.List;

public class Processes {
	
	static OS LOCAL_OS;
	static ProcessDetailsFactory<? extends ProcessDetails> PROCESS_FACTORY;
	
	static void init() {
		if (LOCAL_OS == null) {
			LOCAL_OS = OS.detect();
			
			switch (LOCAL_OS) {
			case LINUX:
				PROCESS_FACTORY = new LinuxProcessDetailsFactory();
				break;
			case MAC:
			case SOLARIS:
			case UNIX:
			case WINDOWS:
				break;
			}
		}
	}
	
	public static List<ProcessDetails> listAll() {
		return listAll(false);
	}
	
	@SuppressWarnings("unchecked")
	public static List<ProcessDetails> listAll(boolean updateAll) {
		init();
		return (List<ProcessDetails>) PROCESS_FACTORY.listProcesses(updateAll);
	}

	public static ProcessDetails getProcess(int pid) {
		init();
		return PROCESS_FACTORY.getProcess(pid);
	}
	
	public static void updateStats(ProcessDetails process) {
		init();
		PROCESS_FACTORY.updateStats(process);
	}
	
	public static void stopProcess(ProcessDetails process) {
		init();
		PROCESS_FACTORY.stopProcess(process);
	}
	
	public static void killProcess(ProcessDetails process) {
		init();
		PROCESS_FACTORY.killProcess(process);
	}
}
