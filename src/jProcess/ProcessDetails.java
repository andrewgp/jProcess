package jProcess;

import jProcess.util.ByteFormatHelper;
import jProcess.util.TimeFormatHelper;

import java.util.Map;

public abstract class ProcessDetails {
	protected final int pid;
	protected int parentPid;
	
	protected String name;
	private String ownerUser;
	
	protected String cmdLine;
	protected Map<String,String> envVars;
	
	/*	Variable Data	*/
	
	protected long cpuTime;
	protected State state;
	protected long memUsed;
	
	/*	Reformatted Variable Data	*/
	
	protected String cpuTimeFormatted;
	protected String memUsedFormatted;
	
	/*	Constructors	*/
	
	public ProcessDetails(int pid) {
		this.pid = pid;
	}
	
	/*	Properties	*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCmdLine() {
		return cmdLine;
	}
	public void setCmdLine(String cmdLine) {
		this.cmdLine = cmdLine;
	}

	public int getPid() {
		return pid;
	}
	
	public Map<String,String> getEnvVars() {
		return envVars;
	}
	public void setEnvVars(Map<String,String> envVars) {
		this.envVars = envVars;
	}
	
	public int getParentPid() {
		return parentPid;
	}
	public void setParentPid(int parentPid) {
		this.parentPid = parentPid;
	}

	public long getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(long cpuTime) {
		this.cpuTime = cpuTime;
		this.cpuTimeFormatted = null;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	public long getMemUsed() {
		return memUsed;
	}
	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
		this.memUsedFormatted = null;
	}
	
	public String getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(String ownerUser) {
		this.ownerUser = ownerUser;
	}

	public String getCpuTimeFormatted() {
		if (cpuTimeFormatted == null) {
			cpuTimeFormatted = TimeFormatHelper.formatSecs(cpuTime);
		}
		return cpuTimeFormatted;
	}

	public String getMemUsedFormatted() {
		if (memUsedFormatted == null) {
			memUsedFormatted = ByteFormatHelper.formatBytes(memUsed);
		}
		return memUsedFormatted;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		
		putValue("pid", Long.toString(pid), str);
		putValue("user", ownerUser, str);
		
		toStringExtended(str);
		
		putValue("name", name, str);
		
		return str.toString();
	}
	
	protected abstract void toStringExtended(StringBuffer str);

	protected void putValue(String name, String value, StringBuffer str) {
		if (value != null) {
			if (str.length() > 0) {
				str.append(", ");
			}
			str.append(name);
			str.append("=");
			str.append(value);
		}
	}

	public enum State {
		RUNNING,
		SLEEPING,
		SLEEPING_INTR,
		ZOMBIE,
		STOPPED
	}
}
