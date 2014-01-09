package jProcess.linux;

import jProcess.ProcessDetails;
import jProcess.ProcessDetails.State;
import jProcess.ProcessDetailsFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Uses 'procfs' to get more detail than a standard unix 'ps' query.
 *
 */
public class LinuxProcessDetailsFactory extends ProcessDetailsFactory<LinuxProcessDetails> {
	
	static String PROCFS_PATH = "/proc";
	
	protected LinuxUserMap userMap;
	
	public LinuxProcessDetailsFactory() {
		userMap = new LinuxUserMap();
	}

	@Override
	public List<Integer> listPIDs() {
		List<Integer> pids = new ArrayList<Integer>();
		
		// list all dirs
		File procFs = new File(PROCFS_PATH);
		File[] files = procFs.listFiles();
		
		// only use numeric dirs
		for (File file : files) {
			if (file.isDirectory()) {
				String name = file.getName();
				boolean success = true;
				for (int i = 0; i < name.length(); i++) {
					char c = name.charAt(i);
					if (c < '0' || c > '9') {
						success = false;
						break;
					}
				}
				
				if (success) {
					pids.add(Integer.parseInt(name));
				}
			}
		}
		return pids;
	}

	@Override
	public List<LinuxProcessDetails> listProcesses(boolean updateAll) {
		// get list of PIDs
		List<Integer> pids = listPIDs();
		
		userMap.refresh();
		
		// read info for each process
		List<LinuxProcessDetails> processes = new ArrayList<LinuxProcessDetails>();
		for (Integer pid : pids) {
			LinuxProcessDetails process = getProcess(pid);
			if (process != null) {
				processes.add(process);
			}
		}
		return processes;
	}

	@Override
	public LinuxProcessDetails getProcess(int pid) {
		File baseDir = new File(PROCFS_PATH + File.separator + pid);
		LinuxProcessDetails process = new LinuxProcessDetails(pid);
		
		String cmdLine = readFileLine(baseDir, "cmdline");
		process.setCmdLine(cmdLine);
		
		List<String> envVarsList = readFileContentsRaw(baseDir, "environ");
		if (envVarsList != null) {
			Map<String, String> envVarsMap = listToMap(envVarsList, "=");
			process.setEnvVars(envVarsMap);
		}
		
		// read status file
		List<String> statusItems = readFileContents(baseDir, "status");
		if (statusItems != null) {
			Map<String, String> statusMap = listToMap(statusItems, ":");
			
			// UID
			String uidStr = statusMap.get("Uid");
			if (uidStr != null) {
				String[] values = uidStr.trim().split("\t");
				int uid = Integer.parseInt(values[0]);
				process.setUid(uid);
				process.setOwnerUser(userMap.getUsername(uid));
				if (process.getOwnerUser() == null) {
					// refresh and try again
					userMap.refresh();
					process.setOwnerUser(userMap.getUsername(uid));
				}
			}
			
			// Process name
			String name = statusMap.get("Name");
			process.setName(name.trim());
		}
		
		updateStats(process);
		
		return process;
	}

	@Override
	public void updateStats(ProcessDetails process) {
		LinuxProcessDetails p = (LinuxProcessDetails) process;
		
		// read stats file
		File baseDir = new File(PROCFS_PATH + File.separator + process.getPid());
		String statsStr = readFileLine(baseDir, "stat");
		String[] values = statsStr.split(" ");
		
		String value = values[2];
		State state = null;
		if (value.equals("R")) {
			state = State.RUNNING;
		} else if (value.equals("S")) {
			state = State.SLEEPING_INTR;
		} else if (value.equals("D")) {
			state = State.SLEEPING;
		} else if (value.equals("Z")) {
			state = State.ZOMBIE;
		} else if (value.equals("T")) {
			state = State.STOPPED;
		}
		process.setState(state);
		process.setParentPid(Integer.parseInt(values[3]));
		p.setGroupId(Integer.parseInt(values[4]));
		p.setSessionId(Integer.parseInt(values[5]));
		p.setTty(values[6]);
		p.setTpgid(Integer.parseInt(values[7]));
		p.setFlags(values[8]);
		p.setMinflt(values[9]);
		p.setCminflt(values[10]);
		p.setMajflt(values[11]);
		p.setCmajflt(values[12]);
		p.setUtime(Long.parseLong(values[13]));
		p.setStime(Long.parseLong(values[14]));
		p.setCutime(Long.parseLong(values[15]));
		p.setCstime(Long.parseLong(values[16]));
		p.setCounter(Long.parseLong(values[17]));
		p.setPriority(Integer.parseInt(values[18]));
		p.setTimeout(Long.parseLong(values[19]));
		p.setItrealvalue(Long.parseLong(values[20]));
		p.setStarttime(Long.parseLong(values[21]));
		p.setVsize(Long.parseLong(values[22]));
		p.setRlim(Long.parseLong(values[23]));
//		process.setStartcode(Long.parseLong(values[24]));
//		process.setEndcode(Long.parseLong(values[25]));
//		process.setStartstack(Long.parseLong(values[26]));
//		process.setKstkesp(Long.parseLong(values[27]));
//		process.setKstkeip(Integer.parseInt(values[28]));
//		process.setSignal(Long.parseLong(values[29]));
//		process.setBlocked(Long.parseLong(values[30]));
//		process.setSigignore(Long.parseLong(values[31]));
//		process.setSigcatch(Long.parseLong(values[32]));
//		process.setWchan(Long.parseLong(values[33]));
		
		process.setMemUsed(p.getRlim());
	}

	@Override
	public void updateAll(LinuxProcessDetails process) {
		updateStats(process);
		// TODO
	}

	@Override
	public boolean stopProcess(ProcessDetails process) {
		// TODO
		return false;
	}

	@Override
	public boolean resumeProcess(ProcessDetails process) {
		// TODO
		return false;
	}

	@Override
	public boolean killProcess(ProcessDetails process) {
		// TODO
		return false;
	}

	@Override
	public void checkSupported() {
		// TODO Auto-generated method stub
		
	}
}
