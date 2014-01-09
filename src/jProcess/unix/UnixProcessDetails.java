package jProcess.unix;

import java.util.Date;

import jProcess.ProcessDetails;

public class UnixProcessDetails extends ProcessDetails {

	protected int uid;
	protected long cpuTime;
	protected Date start;
	
	protected long rss;
	protected long vSize;
	
    public UnixProcessDetails(int pid) {
		super(pid);
	}
	
	@Override
	protected void toStringExtended(StringBuffer str) {

	}

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}

	public long getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(long cpuTime) {
		this.cpuTime = cpuTime;
	}

	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}

	public long getRss() {
		return rss;
	}
	public void setRss(long rss) {
		this.rss = rss;
	}

	public long getvSize() {
		return vSize;
	}
	public void setvSize(long vSize) {
		this.vSize = vSize;
	}
}
