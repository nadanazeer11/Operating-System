package memoryManager;

public class ProcessObject {
	String name;
	int processData;
	String extraData;
	String extraData2 = "No";
	private boolean istaken;

	public String getExtraData2() {
		return extraData2;
	}

	public void setExtraData2(String extraData2) {
		this.extraData2 = extraData2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProcessData() {
		return processData;
	}

	public void setProcessData(int processData) {
		this.processData = processData;
	}

	public ProcessObject(String objectName, int processData) {
		this.name = objectName;
		this.processData = processData;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public boolean isIstaken() {
		return istaken;
	}

	public void setIstaken(boolean istaken) {
		this.istaken = istaken;
	}
	
}
