package jProcess.unix;

import jProcess.ProcessDetails;

public class UnixProcessDetails extends ProcessDetails {

    public UnixProcessDetails(int pid) {
		super(pid);
	}
	
	@Override
	protected void toStringExtended(StringBuffer str) {

	}
}
