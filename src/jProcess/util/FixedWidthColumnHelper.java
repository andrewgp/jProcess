package jProcess.util;

import java.util.ArrayList;
import java.util.List;

public abstract class FixedWidthColumnHelper {

	public static Column[] readHeader(String line) {
		StringBuffer buffer = new StringBuffer();
		List<String> values = new ArrayList<String>();
		List<Integer> positions = new ArrayList<Integer>();
		int lastPos = 0;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == ' ') {
				if (buffer.length() > 0) {
					values.add(buffer.toString());
					positions.add(lastPos);
					buffer = new StringBuffer();
				}
			} else {
				if (buffer.length() == 0) {
					lastPos = i;
				}
				buffer.append(c);
			}
		}
		if (buffer.length() > 0) {
			values.add(buffer.toString());
			positions.add(lastPos);
			buffer = new StringBuffer();
		}
		Column[] cols = new Column[values.size()];
		for (int i = 0; i < values.size(); i++) {
			int endPos = i < values.size() - 1 ? positions.get(i + 1) : line.length();
			cols[i] = new Column(values.get(i), endPos);
		}
		return cols;
	}
	
	public static String[] splitLine(String line, Column[] cols) {
//		StringBuffer buffer = new StringBuffer();
//		String[] values = new String[cols.length];
//		int valIdx = 0;
//		for (int i = 0; i < line.length(); i++) {
//			char c = line.charAt(i);
//			if (c == ' ') {
//				if (buffer.length() > 0 && i <= cols[valIdx].endPos) {
//					values[valIdx++] = buffer.toString().trim();
//					buffer = new StringBuffer();
//					
//					if (valIdx == cols.length - 1) {
//						// last column is remainder
//						values[valIdx++] = line.substring(i).trim();
//					}
//				}
//			} else {
//				buffer.append(c);
//			}
//		}
		String[] values = new String[cols.length];
		int lastPos = 0;
		for (int i = 0; i < cols.length - 1; i++) {
			values[i] = line.substring(lastPos, cols[i].endPos).trim();
			lastPos = cols[i].endPos - 1;
		}
		values[values.length - 1] = line.substring(cols[cols.length - 2].endPos).trim();
		return values;
	}
	
	public static class Column {
		public String name;
		public int endPos;
		
		public Column(String name, int endPos) {
			this.name = name;
			this.endPos = endPos;
		}
	}
}
