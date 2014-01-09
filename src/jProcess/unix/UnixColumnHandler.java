package jProcess.unix;

import jProcess.ProcessDetails.State;
import jProcess.util.Callable2PNR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixColumnHandler<T extends UnixProcessDetails> extends AutoParseProcStatColHandler<T> {

	public static final String COL_PID = "pid";
	public static final String COL_PPID = "ppid";
	
	public static final String COL_UNAME = "uname";
	public static final String COL_CMD = "cmd";
	public static final String COL_FNAME = "fname";
	
	public static final String COL_UID = "uid";
	public static final String COL_STATE = "state";
	
	public static final String COL_CPUTIME = "cputime";
	public static final String COL_START = "start";
	
	
	public static final String COL_RSS = "rss";
	public static final String COL_VSIZE = "vsize";
	
	protected final DateFormat timeFormat;
	
	public UnixColumnHandler() {
		timeFormat = new SimpleDateFormat("HH:mm:ss");
		
		registerInt(COL_PPID, new Callable2PNR<Integer, T>() {
			@Override
			public void call(Integer value, T process) {
				process.setParentPid(value);
			}
		});
		registerString(COL_UNAME, new Callable2PNR<String, T>() {
			@Override
			public void call(String value, T process) {
				process.setOwnerUser(value);
			}
		}, 64);
		registerString(COL_CMD, new Callable2PNR<String, T>() {
			@Override
			public void call(String value, T process) {
				process.setCmdLine(value);
			}
		}, 128);
		registerString(COL_FNAME, new Callable2PNR<String, T>() {
			@Override
			public void call(String value, T process) {
				process.setName(value);
			}
		}, 64);
		registerInt(COL_UID, new Callable2PNR<Integer, T>() {
			@Override
			public void call(Integer value, T process) {
				process.setUid(value);
			}
		});
		registerDate(COL_CPUTIME, new Callable2PNR<Date, T>() {
			@Override
			public void call(Date value, T process) {
				process.setCpuTime(value.getTime());
			}
		}, 18, timeFormat);
		registerDate(COL_START, new Callable2PNR<Date, T>() {
			@Override
			public void call(Date value, T process) {
				// TODO set date
				process.setStart(value);
			}
		}, 18, timeFormat);
		registerLong(COL_RSS, new Callable2PNR<Long, T>() {
			@Override
			public void call(Long value, T process) {
				process.setRss(value);
				process.setMemUsed(value);
			}
		});
		registerLong(COL_VSIZE, new Callable2PNR<Long, T>() {
			@Override
			public void call(Long value, T process) {
				process.setvSize(value);
			}
		});
		registerString(COL_STATE, new Callable2PNR<String, T>() {
			public void call(String value, T process) {
				State state = null;
				if (value.equals("S")) {
					state = State.SLEEPING_INTR;
				} else if (value.equals("R")) {
					state = State.RUNNING;
				} else if (value.equals("D")) {
					state = State.SLEEPING;
				} else if (value.equals("Z")) {
					state = State.ZOMBIE;
				} else if (value.equals("T")) {
					state = State.STOPPED;
				}
				process.setState(state);
			}
		}, 1);
	}

	public String getPidColumnName() {
		return COL_PID;
	}
	
	public int getPidColumnSize() {
		return 18;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T newProcess(int pid) {
		return (T) new UnixProcessDetails(pid);
	}
}
