package jProcess.util;

import java.util.Collection;
import java.util.Map;

public class ArrayHelper {

	public static String arrayToDelimitedString(String[] values, String delimiter) {
		StringBuffer str = new StringBuffer();
		for (String val : values) {
			if (str.length() > 0) {
				str.append(delimiter);
			}
			str.append(val);
		}
		return str.toString();
	}

	public static String collectionToDelimitedString(Collection<String> values, String delimiter) {
		StringBuffer str = new StringBuffer();
		for (String val : values) {
			if (str.length() > 0) {
				str.append(delimiter);
			}
			str.append(val);
		}
		return str.toString();
	}

	public static String mapToDelimitedString(Map<?, ?> map, String valueDelimiter, String entryDelimiter) {
		StringBuffer str = new StringBuffer();
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			if (str.length() > 0) {
				str.append(entryDelimiter);
			}
			str.append(entry.getKey().toString());
			str.append(valueDelimiter);
			str.append(entry.getValue().toString());
		}
		return str.toString();
	}
}
