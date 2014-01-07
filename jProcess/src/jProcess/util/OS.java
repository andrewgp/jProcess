package jProcess.util;

public enum OS {
	
	LINUX,
	MAC,
	SOLARIS,
	UNIX,
	WINDOWS;

	public static OS detect() {
		return detect(System.getProperty("os.name").toLowerCase());
	}
	
	public static OS detect(String name) {
		if (name.contains("win")) {
			return WINDOWS;
		}
		if (name.contains("mac")) {
			return MAC;
		}
		if (name.contains("linux")) {
			return LINUX;
		}
		if (name.contains("nix")
				|| name.contains("nux")
				|| name.indexOf("aix") > 0) {
			return UNIX;
		}
		if (name.contains("sunos")) {
			return SOLARIS;
		}
		return LINUX;
	}
}
