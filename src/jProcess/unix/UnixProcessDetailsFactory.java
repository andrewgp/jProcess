package jProcess.unix;

import java.util.List;

import jProcess.ProcessDetails;
import jProcess.ProcessDetailsFactory;

/**
 * Gets process status from the 'ps' command. Should work on all unix-based machines.
 *
 */
public abstract class UnixProcessDetailsFactory<T extends UnixProcessDetails> extends ProcessDetailsFactory<T> {

	@Override
	public List<Integer> listPIDs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> listProcesses(boolean updateAll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getProcess(int pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStats(ProcessDetails process) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(T process) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean stopProcess(ProcessDetails process) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resumeProcess(ProcessDetails process) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean killProcess(ProcessDetails process) {
		// TODO Auto-generated method stub
		return false;
	}

}
