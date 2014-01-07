package jProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProcessDetailsFactory<T extends ProcessDetails> {

	public abstract List<Integer> listPIDs();
	public abstract List<T> listProcesses(boolean updateAll);
	
	public abstract T getProcess(int pid);
	
	public abstract void updateStats(ProcessDetails process);
	public abstract void updateAll(T process);
	
	public abstract boolean stopProcess(ProcessDetails process);
	public abstract boolean resumeProcess(ProcessDetails process);
	public abstract boolean killProcess(ProcessDetails process);
	
	protected String readFileLine(File baseDir, String filename) {
		File file = new File(baseDir.getAbsolutePath() + File.separator + filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			return reader.readLine();
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}
	
	protected List<String> readFileContentsRaw(File baseDir, String filename) {
		File file = new File(baseDir.getAbsolutePath() + File.separator + filename);
		InputStream reader = null;
		try {
			reader = new FileInputStream(file);
			byte[] byteBuf = new byte[4096];
			int read;
			StringBuffer buffer = new StringBuffer();
			while ((read = reader.read(byteBuf)) > 0) {
				for (int i = 0; i < read; i++) {
					buffer.append((char)byteBuf[i]);
				}
				reader.skip(2);
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}
	
	protected Map<String, String> listToMap(List<String> list, String delim) {
		Map<String, String> map = new HashMap<String, String>();
		for (String item : list) {
			String[] values = item.split(delim, 2);
			map.put(values[0], values[1]);
		}
		return map;
	}
	
	protected List<String> readFileContents(File baseDir, String filename) {
		File file = new File(baseDir.getAbsolutePath() + File.separator + filename);
		BufferedReader reader = null;
		List<String> buffer = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.add(line);
			}
			return buffer;
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}
}
