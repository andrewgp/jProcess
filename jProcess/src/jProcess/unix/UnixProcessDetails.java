package jProcess.unix;

import jProcess.ProcessDetails;

public class UnixProcessDetails extends ProcessDetails {

	protected int groupId;		// Process group ID
    protected int sessionId;	// The process session ID.
    protected String tty;		// The tty the process is using
    protected int tpgid;		// The process group ID of the owning process of the tty the current process is connected to
    
    protected String flags;		// Process flags, currently with bugs
    protected String minflt;	// Minor faults the process has made
    protected String cminflt;	// Minor faults the process and its children have made.
    protected String majflt;
    protected String cmajflt;
    protected long utime;		// The number of jiffies (processor time) that this process has been scheduled in user mode
    protected long stime; 		// in kernel mode
    protected long cutime;		// This process and its children in user mode
    protected long cstime;		// in kernel mode
    protected long counter;		// The maximum time of this processes next time slice
    protected int priority;		// The priority of the nice(1) (process priority) value plus fifteen
    protected long timeout;		// The time in jiffies of the process's next timeout
    protected long itrealvalue;	// The time in jiffies before the next SIGALRM is sent to the process because of an internal timer
    protected long starttime;	// Time the process started after system boot
    protected long vsize;		// Virtual memory size
    protected long rlim; 		// Current limit in bytes of the rss of the process
    protected long startcode;	// The address above which program text can run
    protected long endcode;		// The address below which program text can run
    protected long startstack;	// The address of the start of the stack
    protected long kstkesp;		// The current value of esp for the process as found in the kernel stack page
    protected int kstkeip;		// The current 32 bit instruction pointer, EIP
    protected long signal;		// The bitmap of pending signals
    protected long blocked;		// The bitmap of blocked signals
    protected long sigignore;	// The bitmap of ignored signals
    protected long sigcatch; 	// The bitmap of catched signals
    protected long wchan;		// The channel in which the process is waiting. The "ps -l" command gives somewhat of a list
    
    public UnixProcessDetails(int pid) {
		super(pid);
	}

	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getTty() {
		return tty;
	}
	public void setTty(String tty) {
		this.tty = tty;
	}

	public int getTpgid() {
		return tpgid;
	}
	public void setTpgid(int tpgid) {
		this.tpgid = tpgid;
	}

	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getMinflt() {
		return minflt;
	}
	public void setMinflt(String minflt) {
		this.minflt = minflt;
	}

	public String getCminflt() {
		return cminflt;
	}
	public void setCminflt(String cminflt) {
		this.cminflt = cminflt;
	}

	public String getMajflt() {
		return majflt;
	}
	public void setMajflt(String majflt) {
		this.majflt = majflt;
	}

	public String getCmajflt() {
		return cmajflt;
	}
	public void setCmajflt(String cmajflt) {
		this.cmajflt = cmajflt;
	}

	public long getUtime() {
		return utime;
	}
	public void setUtime(long utime) {
		this.utime = utime;
	}

	public long getStime() {
		return stime;
	}
	public void setStime(long stime) {
		this.stime = stime;
	}

	public long getCutime() {
		return cutime;
	}
	public void setCutime(long cutime) {
		this.cutime = cutime;
	}

	public long getCstime() {
		return cstime;
	}
	public void setCstime(long cstime) {
		this.cstime = cstime;
	}

	public long getCounter() {
		return counter;
	}
	public void setCounter(long counter) {
		this.counter = counter;
	}

	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getItrealvalue() {
		return itrealvalue;
	}
	public void setItrealvalue(long itrealvalue) {
		this.itrealvalue = itrealvalue;
	}

	public long getStarttime() {
		return starttime;
	}
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}

	public long getVsize() {
		return vsize;
	}
	public void setVsize(long vsize) {
		this.vsize = vsize;
	}

	public long getRlim() {
		return rlim;
	}
	public void setRlim(long rlim) {
		this.rlim = rlim;
	}

	public long getStartcode() {
		return startcode;
	}
	public void setStartcode(long startcode) {
		this.startcode = startcode;
	}

	public long getEndcode() {
		return endcode;
	}
	public void setEndcode(long endcode) {
		this.endcode = endcode;
	}

	public long getStartstack() {
		return startstack;
	}
	public void setStartstack(long startstack) {
		this.startstack = startstack;
	}

	public long getKstkesp() {
		return kstkesp;
	}
	public void setKstkesp(long kstkesp) {
		this.kstkesp = kstkesp;
	}

	public int getKstkeip() {
		return kstkeip;
	}
	public void setKstkeip(int kstkeip) {
		this.kstkeip = kstkeip;
	}

	public long getSignal() {
		return signal;
	}
	public void setSignal(long signal) {
		this.signal = signal;
	}

	public long getBlocked() {
		return blocked;
	}
	public void setBlocked(long blocked) {
		this.blocked = blocked;
	}

	public long getSigignore() {
		return sigignore;
	}
	public void setSigignore(long sigignore) {
		this.sigignore = sigignore;
	}

	public long getSigcatch() {
		return sigcatch;
	}
	public void setSigcatch(long sigcatch) {
		this.sigcatch = sigcatch;
	}

	public long getWchan() {
		return wchan;
	}
	public void setWchan(long wchan) {
		this.wchan = wchan;
	}
	
	@Override
	protected void toStringExtended(StringBuffer str) {
    	putValue("priority", Integer.toString(priority), str);
    	putValue("vSize", Long.toString(vsize), str);
    	putValue("rLim", Long.toString(rlim), str);
    	putValue("state", state != null ? state.toString() : null, str);
    	putValue("uTime", Long.toString(utime), str);
	}
}
