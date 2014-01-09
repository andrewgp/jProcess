package jProcess.unix;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jProcess.util.Callable1P;
import jProcess.util.Callable2PNR;

public abstract class AutoParseProcStatColHandler<T extends UnixProcessDetails> implements ProcessStatusColumnHandler<T> {

	protected Map<String, Callable2PNR<?,T>> colCallbackMap;
	protected Map<String, Callable1P<String,?>> colParseMap;
	protected Map<String, Class<?>> colTypeMap;
	protected Map<String, Integer> colSizesMap;
	
	public AutoParseProcStatColHandler() {
		colCallbackMap = new HashMap<String, Callable2PNR<?,T>>();
		colParseMap = new HashMap<String, Callable1P<String,?>>();
		colTypeMap = new HashMap<String, Class<?>>();
		colSizesMap = new HashMap<String, Integer>();
	}
	
	public <Y> void register(String colName, Callable1P<String,Y> parseCallback, Class<Y> cls, Callable2PNR<Y,T> handleCallback, int colSize) {
		colTypeMap.put(colName, cls);
		colCallbackMap.put(colName, handleCallback);
		colParseMap.put(colName, parseCallback);
		colSizesMap.put(colName, colSize);
	}
	
	public void registerInt(String colName, Callable2PNR<Integer,T> handleCallback) {
		register(colName, new Callable1P<String, Integer>() {
			@Override
			public Integer call(String value) {
				return Integer.parseInt(value);
			}
		}, Integer.class, handleCallback, Integer.toString(Integer.MIN_VALUE).length());
	}
	
	public void registerLong(String colName, Callable2PNR<Long,T> handleCallback) {
		register(colName, new Callable1P<String, Long>() {
			@Override
			public Long call(String value) {
				return Long.parseLong(value);
			}
		}, Long.class, handleCallback, Long.toString(Long.MIN_VALUE).length());
	}
	
	public void registerDate(String colName, Callable2PNR<Date,T> handleCallback, int maxSize, final DateFormat format) {
		register(colName, new Callable1P<String, Date>() {
			@Override
			public Date call(String value) {
				try {
					return format.parse(value);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}, Date.class, handleCallback, maxSize);
	}
	
	public void registerString(String colName, Callable2PNR<String,T> handleCallback, int maxSize) {
		register(colName, null, String.class, handleCallback, maxSize);
	}

	public List<String> getColumnNames() {
		return new ArrayList<String>(colSizesMap.keySet());
	}

	public List<Integer> getColumnSizes() {
		return new ArrayList<Integer>(colSizesMap.values());
	}

	@Override
	public void handleColumn(String name, String value, T process) {
		// get type
		Class<?> cls = colTypeMap.get(name);
		
		parseAndHandle(cls, name, value, process);
	}

	@SuppressWarnings("unchecked")
	protected <Y> void parseAndHandle(Class<Y> cls, String name, String value, T process) {
		Y parsedValue;
		if (cls != String.class) {
			// parse
			Callable1P<String,Y> parser = (Callable1P<String, Y>) colParseMap.get(name);
			parsedValue = parser.call(value);
		} else {
			parsedValue = (Y) value;
		}
		
		// handle
		Callable2PNR<Y,T> callback = (Callable2PNR<Y, T>) colCallbackMap.get(name);
		callback.call(parsedValue, process);
	}
}
