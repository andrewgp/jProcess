package jProcess.linux;

import jProcess.unix.UnixProcessDetails;

public class LinuxProcessDetails extends UnixProcessDetails {
	
	protected int uid;
	
	public LinuxProcessDetails(int pid) {
		super(pid);
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
}
