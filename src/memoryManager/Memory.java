package memoryManager;

import java.util.ArrayList;

import processManager.Pcb;

public class Memory {

	private Word[] mem;

	private int pointer = 0;
	private int pointer2 = 39;

	public Memory() {

		this.mem = new Word[40];
		fillMemoryWithWords();
	}

	public void printMemoryContents() {
		
		for (int i = 0; i < 5; i++) {
			System.out.print(" Item No = " +i +" , Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		for (int i = 5; i < 10; i++) {
			System.out.print(" Item No = " +i +" , Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		for (int i = 10; i < 15; i++) {
			System.out.print(" Item No = " +i +" , Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		for (int i = 15; i < 20; i++) {
			System.out.print(" Item No = " +i +" , Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		for (int i = 20; i < 25; i++) {
			System.out.print(" Item No = " +i +" , Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		for (int i = 25; i < 30; i++) {
			System.out.print(" Item No = " +i +" Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		for (int i = 30; i < 35; i++) {
			System.out.print(" Item No = " +i +" , Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		for (int i = 35; i < 40; i++) {
			System.out.print(" Item No = " +i +" , Id = " + mem[i].getOwnerId()+" , Word = "+mem[i].getWord() + "||");
		}
		System.out.println();
		System.out.println("Pointer1: "+pointer+" Pointer2: "+pointer2);
		System.out.println("-----------------------------------------------");
		
	}

	private void fillMemoryWithWords() {
		for (int i = 0; i < 40; i++) {
			Word word = new Word();
			mem[i] = word;
		}
	}
	ArrayList<String> lineList = new ArrayList<String>();
	protected Word getMemWord(int index) {
		return mem[index];
	}

	protected ArrayList<ProcessObject> getObjectsList(int pid)
	{
		ArrayList<ProcessObject> objectsList = new ArrayList<>();
		ProcessObject object = null;
		for(int i =0;i<40;i++)
		{
			Word tempWord = mem[i];
			
			if (tempWord.getObject() instanceof ProcessObject && tempWord.isIstaken()) 
			{
				object = (ProcessObject) tempWord.getObject();
				
				if (tempWord.getOwnerId() == pid && tempWord.isIstaken()) {
					
					objectsList.add(object);
				}
			}
			
		}
		return objectsList;
	}
	protected void setMemWord(int cellNumber, int ownerId, Object object) {
		mem[cellNumber].setIstaken(true);
		mem[cellNumber].setOwnerId(ownerId);
		mem[cellNumber].setWord(object);
	}

	protected void incPointer() {
		if (pointer == 39) {
			System.out.println("Pointer reseted");
			pointer = 0;
		} else {
			pointer = pointer + 1;
		}
	}

	protected void incPointer2() {
		if (pointer2 == 39) {
			System.out.println("Pointer2 reseted");
			pointer2 = 0;
		} else {
			pointer2 = pointer2 + 1;
		}
	}
	protected void decPointer2()
	{
		if (pointer2 == 0) {
			System.out.println("Pointer2 reseted BackWards");
			pointer2 = 39;
		} else {
			pointer2 = pointer2 - 1;
		}
	}
	protected void decPointer()
	{
		if (pointer == 0) {
			System.out.println("Pointer reseted BackWards");
			pointer = 39;
		} else {
			pointer = pointer - 1;
		}
	}
	protected int getPointer() {
		return pointer;
	}

	protected int getPointer2() {
		return pointer2;
	}

	protected void reserve(ArrayList<String> lines, Pcb pcb) {

	}

}
