package memoryManager;

import java.util.ArrayList;

import processManager.Pcb;

public class ProcessBlock {
	private int processId;
	private ArrayList<String> file;
	private ArrayList<ProcessObject> variableList;
	private Pcb pcb;
	private boolean isValid;

	protected ProcessBlock(int processId,ArrayList<ProcessObject> objectsList,ArrayList<String> lines,Pcb pcb) {
		super();
		this.processId = processId;
		this.variableList = objectsList;
		this.file = lines;
		this.pcb = pcb;

	}

	protected boolean isValid() {
		return isValid;
	}

	protected void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	protected void setProcessId(int processId) {
		this.processId = processId;
	}

	protected void setFile(ArrayList<String> file) {
		this.file = file;
	}

	protected ArrayList<ProcessObject> getVariableList() {
		return variableList;
	}

	protected void setVariableList(ArrayList<ProcessObject> variableList) {
		this.variableList = variableList;
	}

	protected int getProcessId() {
		return processId;
	}

	protected ArrayList<String> getFile() {
		return file;
	}

	protected Pcb getPcb() {
		return pcb;
	}

	protected void setPcb(Pcb pcb) {
		this.pcb = pcb;
	}
	

}
