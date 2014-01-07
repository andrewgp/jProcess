package jProcess;

public class Demo {
	public static void main(String[] args) {
//		System.out.println(Processes.getProcess(2473).toString());
		for (ProcessDetails process : Processes.listAll()) {
			System.out.println(process.toString());
		}
	}
}
