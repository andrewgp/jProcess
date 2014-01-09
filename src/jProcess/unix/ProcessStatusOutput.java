package jProcess.unix;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProcessStatusOutput implements Iterable<String[]> {

	protected final String[] columnNames;
	protected final String[][] columnsData;
	protected final Map<String, Integer> columnNameToIdxMap;
	
	public ProcessStatusOutput(String[] columnNames, String[][] columnsData) {
		this.columnNames = columnNames;
		this.columnsData = columnsData;
		
		// check for errors
		if (columnNames == null) {
			throw new RuntimeException("No column names specified");
		}
		if (columnsData == null) {
			throw new RuntimeException("No column data specified");
		}
		if (columnNames.length != columnsData[0].length) {
			throw new RuntimeException("Column names and data do not match");
		}
		
		// map columns
		columnNameToIdxMap = new HashMap<String, Integer>();
		for (int idx = 0; idx < columnNames.length; idx++) {
			columnNameToIdxMap.put(columnNames[idx], idx);
		}
	}
	
	public String[] getColumn(int index) {
		if (index >= 0 && index <= columnsData.length) {
			return columnsData[index];
		}
		return null;
	}
	
	public String[] getColumn(String name) {
		return getColumn(getColumnIndex(name));
	}
	
	public int getColumnIndex(String name) {
		if (columnNameToIdxMap.containsKey(name)) {
			return columnNameToIdxMap.get(name);
		}
		return -1;
	}

	public int size() {
		return columnsData.length;
	}

	@Override
	public Iterator<String[]> iterator() {
		return new Iterator<String[]>() {
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < columnsData.length - 1;
			}

			@Override
			public String[] next() {
				return columnsData[index++];
			}

			@Override
			public void remove() {
			}
		};
	}

	public String[] getColumnNames() {
		return columnNames;
	}
}
