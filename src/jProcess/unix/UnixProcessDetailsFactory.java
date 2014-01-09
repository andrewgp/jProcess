package jProcess.unix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jProcess.ProcessDetails;
import jProcess.ProcessDetailsFactory;
import jProcess.util.ArrayHelper;
import jProcess.util.FixedWidthColumnHelper;
import jProcess.util.FixedWidthColumnHelper.Column;
import jProcess.util.ProcessHandler;

/**
 * Gets process status from the 'ps' command. Should work on all unix-based machines.
 *
 */
public class UnixProcessDetailsFactory<T extends UnixProcessDetails> extends ProcessDetailsFactory<T> {
	
	protected String psCmd = "ps";
	protected List<String> psArgs;
	protected ProcessStatusColumnHandler<T> colHandler;
	protected Map<String, String> colToHeaderMap;
	
	public UnixProcessDetailsFactory() {
		this(new UnixColumnHandler<T>());
	}
	
	public UnixProcessDetailsFactory(ProcessStatusColumnHandler<T> colHandler) {
		this.colHandler = colHandler;
		
		psArgs = new ArrayList<String>();
		psArgs.add("-e");
		psArgs.add("--cols");
		psArgs.add("4000");
		psArgs.add("-o");
		
		colToHeaderMap = new HashMap<String, String>();
	}
	
	@Override
	public void checkSupported() {
		// Run PS to get mapping and ensure all columns are supported
		List<String> results = new ArrayList<String>();
		int result = ProcessHandler.executeSync(psCmd, "L", results);
		if (result != 0) {
			throw new RuntimeException("PS not supported");
		}
		
		// map results
		colToHeaderMap.clear();
		for (String item : results) {
			String[] values = item.split(" ", 2);
			String key = values[0].trim();
			String value = values[1].trim();
			
			colToHeaderMap.put(key, value);
		}
	}
	
	@Override
	public List<Integer> listPIDs() {
		ProcessStatusOutput output = readPS(false);
		
		// read correct column
		int column = output.getColumnIndex(colHandler.getPidColumnName());
		if (column < 0) {
			throw new RuntimeException("Failed to locate PID column of PS");
		}
		
		List<Integer> pids = new ArrayList<Integer>(output.size());
		for (String[] row : output) {
			pids.add(Integer.parseInt(row[column]));
		}
		return pids;
	}

	@Override
	public List<T> listProcesses(boolean updateAll) {
		ProcessStatusOutput output = readPS(true);
		
		// look for column mappings
		int pidCol = 0;
		List<String> colNames = colHandler.getColumnNames();
		
		List<T> processes = new ArrayList<T>();
		for (String[] row : output) {
			int pid = Integer.parseInt(row[pidCol]);
			T process = colHandler.newProcess(pid);
			
			// handle each column requested (we skip PID)
			for (int i = 1; i < row.length; i++) {
				colHandler.handleColumn(colNames.get(i - 1), row[i], process);
			}
			
			processes.add(process);
		}
		
		return processes;
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

	protected ProcessStatusOutput readPS(boolean full) {
		List<String> results = new ArrayList<String>();
		
		List<String> colNames = colHandler.getColumnNames();
		List<Integer> colSizes = colHandler.getColumnSizes();
		
		// run PS only for PID
		List<String> args = new ArrayList<String>();
		args.addAll(psArgs);
		if (full) {
			Map<String, String> cols = new LinkedHashMap<String, String>();
			for (int i = 0; i < colNames.size(); i++) {
				String colName = colNames.get(i);
				int colSize = colSizes.get(i);
				cols.put(colName, Integer.toString(colSize));
			}
			args.add(colHandler.getPidColumnName() + ":" + colHandler.getPidColumnSize() + "," + ArrayHelper.mapToDelimitedString(cols, ":", ","));
		} else {
			args.add(colHandler.getPidColumnName() + ":" + colHandler.getPidColumnSize());
		}
		ProcessHandler.executeSync(psCmd, args, results);
		
		// read column definitions
//		FixedWidthColumnHelper.Column[] cols = FixedWidthColumnHelper.readHeader(results.get(0));
		FixedWidthColumnHelper.Column[] cols = new Column[colSizes.size() + 1];
		cols[0] = new Column(colHandler.getPidColumnName(), colHandler.getPidColumnSize() + 1);
		for (int i = 1; i < cols.length; i++) {
			cols[i] = new Column(colNames.get(i - 1), cols[i - 1].endPos + colSizes.get(i - 1) + 1);
		}
		
		// read each rows column values
		String[][] rows = new String[results.size() - 1][];
		for (int i = 1; i < results.size(); i++) {
			String[] values = FixedWidthColumnHelper.splitLine(results.get(i), cols);
			rows[i - 1] = values;
		}
		
		String[] colNamesArray = new String[cols.length];
		for (int i = 0; i < cols.length; i++) {
			colNamesArray[i] = cols[i].name;
		}
		return new ProcessStatusOutput(colNamesArray, rows);
	}
}
