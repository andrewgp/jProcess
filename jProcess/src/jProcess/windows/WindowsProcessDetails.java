package jProcess.windows;

import jProcess.ProcessDetails;

public class WindowsProcessDetails extends ProcessDetails {

	protected String windowTitle;
	
	public WindowsProcessDetails(int pid) {
		super(pid);
	}

	@Override
	protected void toStringExtended(StringBuffer str) {
		
	}
}
