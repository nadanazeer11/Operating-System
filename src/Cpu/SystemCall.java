package Cpu;

import java.util.ArrayList;

public class SystemCall {
	ArrayList<String> lineList;
	int processId;
//	Mutex inMutex;
//	Mutex outMutex;
//	Mutex fileMutex;
	boolean isTwoLines;

//	protected void setInMutex(Mutex inMutex) {
//		this.inMutex = inMutex;
//	}
//
//	protected void setOutMutex(Mutex outMutex) {
//		this.outMutex = outMutex;
//	}
//
//	protected void setFileMutex(Mutex fileMutex) {
//		this.fileMutex = fileMutex;
//	}
//
//	public void setLineList(ArrayList<String> lineList) {
//		this.lineList = lineList;
//	}

	protected SystemCall(ArrayList<String> lineList, int processId) {
		super();
		this.lineList = lineList;
		this.processId = processId;
	}

	protected boolean isTwoLines() {
		return isTwoLines;
	}

	protected void setTwoLines(boolean isTwoLines) {
		this.isTwoLines = isTwoLines;
	}

}
