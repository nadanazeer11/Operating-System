package memoryManager;

import processManager.Pcb;

class Word {
	private Object word;
	private boolean istaken;
	private int ownerId =9999999;

	public Object getWord() {
		return word;
	}

	public void setWord(Object word) {
		this.word = word;
	}

	public boolean isIstaken() {
		return istaken;
	}

	public void setIstaken(boolean istaken) {
		this.istaken = istaken;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public Object getObject() {
		// TODO Auto-generated method stub
		return word;
	}

}
