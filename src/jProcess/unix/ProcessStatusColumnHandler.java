package jProcess.unix;

import java.util.List;

public interface ProcessStatusColumnHandler<T extends UnixProcessDetails> {

	List<String> getColumnNames();
	List<Integer> getColumnSizes();
	void handleColumn(String name, String value, T process);
	String getPidColumnName();
	int getPidColumnSize();
	
	T newProcess(int pid);
}
