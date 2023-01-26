package memoryManager;

import processManager.Process;

public interface memoryInterface 
{
	public static void addProccessObjectToProcessBlock(int processId, ProcessObject processObject) {
	
	}
	public static void printObjectsInProcessBlock(int processId) {
	
	}
	public static ProcessObject getProccessObjectFromProcessBlock(int processId, String objectName) {
		return null;
		
	}
	public static void setProccessObjectFromProcessBlock(int processId, String objectName, int processData) {
		
	}
	public static void printMemoryProcessBlocks() {
		
	}
	public static String getNextLine(Process process) {
		return null;
		
	}
	public static ProcessBlock currProcessBlock(Process process) {
		return null;
	}

	public static int countRemLines(int pc, int pid) {
		
		return 0;
	}
}
