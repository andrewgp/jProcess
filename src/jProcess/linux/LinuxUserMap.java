package jProcess.linux;

import jProcess.util.ProcessHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads simple user information from 'passwd'.
 *
 */
public class LinuxUserMap {
	
	protected Map<Integer, String> uidToUsernameMap;
	
	public LinuxUserMap() {
		uidToUsernameMap = new HashMap<Integer, String>();
	}
	
	public String getUsername(int uid) {
		synchronized (uidToUsernameMap) {
			return uidToUsernameMap.get(uid);
		}
	}
	
	public void refresh() {
		synchronized (uidToUsernameMap) {
			// clear storage
			uidToUsernameMap.clear();
			
			// read new entries
			List<String> entries = readEntries();
			
			// parse them into what we want
			parseEntries(entries);
		}
	}

	protected void parseEntries(List<String> entries) {
		for (String entry : entries) {
			String[] values = entry.split(":");
			
			// uid
			int uid = Integer.parseInt(values[2]);
			
			// username
			String username = values[0];
			uidToUsernameMap.put(uid, username);
		}
	}

	protected List<String> readEntries() {
		try {
			List<String> entries = new ArrayList<String>();
			
			ProcessHandler.executeSync("getent", "passwd", entries);
			
			return entries;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
