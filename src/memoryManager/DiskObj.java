package memoryManager;

import java.util.ArrayList;

public class DiskObj {
	private ArrayList<String> lineList;
	private ArrayList<String> pcbList;
	private ArrayList<String> objectsList;
	public DiskObj(ArrayList<String> lineList, ArrayList<String> pcbList, ArrayList<String> objectsList) {
		super();
		this.lineList = lineList;
		this.pcbList = pcbList;
		this.objectsList = objectsList;
	}
	public ArrayList<String> getLineList() {
		return lineList;
	}
	public void setLineList(ArrayList<String> lineList) {
		this.lineList = lineList;
	}
	public ArrayList<String> getPcbList() {
		return pcbList;
	}
	public void setPcbList(ArrayList<String> pcbList) {
		this.pcbList = pcbList;
	}
	public ArrayList<String> getObjectsList() {
		return objectsList;
	}
	public void setObjectsList(ArrayList<String> objectsList) {
		this.objectsList = objectsList;
	}
	
	
}
