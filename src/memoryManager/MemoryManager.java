package memoryManager;

import java.util.ArrayList;
import java.util.Collections;

import processManager.Pcb;
import processManager.Process;
import processManager.State;

public class MemoryManager {

	// private ProcessBlock processBlock;
	private Memory mem;
	private int curPc;
	private Disk disk;

	public MemoryManager(Memory mem, Disk disk) {
		super();
		this.mem = mem;
		this.disk = disk;
	}





	public boolean admitInMemory(ArrayList<String> lines, Pcb pcb) {// process first time
																	// reserve
		
		int sizeToBeAdded = lines.size() + 8;

		if (mem.getPointer() + sizeToBeAdded <= mem.getPointer2()) {

			firstReserve(lines, pcb);// only called if free space is in memory
		} else {

			deleteNextProcessBlock(sizeToBeAdded);
			firstReserve(lines, pcb);
			if(memIsFull())
				mem.decPointer();
		}

		return false;

	}

	private boolean memIsFull()
	{
		boolean isFull = true;
		for(int i =0;i<40;i++)
		{
			if(mem.getMemWord(i).isIstaken()==false || mem.getMemWord(i).getOwnerId()==9999999)
				isFull=false;
		}
		return isFull;
	}
	
	public void reserveMemory(int pid) {
		ArrayList<String> diskList = disk.getDiskList();
		DiskObj obj = disk.getFromDisk(pid, diskList);
		int sizeToBeAdded = obj.getPcbList().size() + obj.getLineList().size() + obj.getObjectsList().size();
	
		if (mem.getPointer() + sizeToBeAdded <= mem.getPointer2()) {

			normalReserve(obj.getLineList(), obj.getPcbList(), obj.getObjectsList());
		} else {
			// mlksh mkan
			deleteNextProcessBlock(sizeToBeAdded);
			normalReserve(obj.getLineList(), obj.getPcbList(), obj.getObjectsList());
			if(memIsFull())
				mem.decPointer();
			

		}

	}

	protected void deleteNextProcessBlock(int minLinesToBeDeleted)// only called when the 2 pointers meet each other
	{
		int minusPointerTimes = 0;
		Word minusWord = mem.getMemWord(mem.getPointer2());
		while (minusWord.getOwnerId() == 9999999) {
			
			minusPointerTimes = minusPointerTimes + 1;
			minLinesToBeDeleted = minLinesToBeDeleted - 1;
			mem.decPointer2();
			minusWord = mem.getMemWord(mem.getPointer2());
		}

		for (int i = 0; i < minusPointerTimes; i++)
			mem.incPointer2();
			

		// every inc should be deced to compensate
		int deletedLines = 0;
		int start = 0;
		int end = 0;
		boolean entered = false;
		//mem.incPointer2();
		for(int i =0;i<40;i++)
		{
			
			if(mem.getMemWord(mem.getPointer2()).getOwnerId()==9999999)
			{
				mem.incPointer2();
				entered = true;
			}
			else
			{
				break;
			}
		}
		if(!entered)
		{
			mem.incPointer2();
		}
		
		Word word = mem.getMemWord(mem.getPointer2());
		int pid = word.getOwnerId();
		for (int i = 0; i < 40; i++) {
			Word tempWord = mem.getMemWord(i);
			if (tempWord.getWord() != null) {
				String[] splitted = tempWord.getWord().toString().split(" ");

				if (splitted[0].equals("pcb") && tempWord.isIstaken()) {
					int id = Integer.parseInt(splitted[3]);
					if (id == pid) {
						int x = i+3;
						if(i==39)x=2;
						if(i==38)x=1;
						if(i==37) x=0;
						int y = i+4;
						if(i==39)y=3;
						if(i==38)y=2;
						if(i==37) y=1;
						if(i==36) y=0;
								
						Word startWord = mem.getMemWord(x);
						String[] startsplitted = startWord.getWord().toString().split(" ");
						start = Integer.parseInt(startsplitted[2]);

						Word endWord = mem.getMemWord(y);
						String[] endsplitted = endWord.getWord().toString().split(" ");
						end = Integer.parseInt(endsplitted[2]);
						break;
					}
				}
			}
		}
		ArrayList<String> lineList = new ArrayList<String>();
		ArrayList<ProcessObject> objList = new ArrayList<ProcessObject>();
		ArrayList<String> pcbList = new ArrayList<String>();
		System.out.println("start = "+start);
		System.out.println("end = "+end);
		int objStart=end;
		int objEnd1=decrement(objStart);
		int objEnd2=decrement(objEnd1);
		int objEnd=decrement(objEnd2);
		while(objStart!=objEnd) {

			mem.getMemWord(objStart).setIstaken(false);
			String[] splitted = mem.getMemWord(objStart).getWord().toString().split(" ");

			ProcessObject obj = new ProcessObject(splitted[0], Integer.parseInt(splitted[1]));
			objList.add(obj);
			mem.setMemWord(objStart, 9999999, "null");
			mem.incPointer2();
			deletedLines = deletedLines + 1;
			objStart=decrement(objStart);

		}

		Collections.reverse(objList);
		int pcbStart=decrement(end); pcbStart=decrement(pcbStart);  pcbStart=decrement(pcbStart);
		int pcbEnd=decrement(end);pcbEnd=decrement(pcbEnd);pcbEnd=decrement(pcbEnd);pcbEnd=decrement(pcbEnd);pcbEnd=decrement(pcbEnd);pcbEnd=decrement(pcbEnd);pcbEnd=decrement(pcbEnd);pcbEnd=decrement(pcbEnd);
		while(pcbStart!=pcbEnd) {
	
			mem.getMemWord(pcbStart).setIstaken(false);
			pcbList.add(mem.getMemWord(pcbStart).getWord().toString());
			mem.setMemWord(pcbStart, 9999999, "null");
			mem.incPointer2();
			deletedLines = deletedLines + 1;
			pcbStart=decrement(pcbStart);
			
		}

		Collections.reverse(pcbList);
		pcbList.set(3, "memStart = 0");
		pcbList.set(4, "memEnd = 0");
		int lineStart=decrement(end); lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);
		int newStart = decrement(start);
		while(lineStart!=newStart) {
			mem.getMemWord(lineStart).setIstaken(false);
			lineList.add((String) mem.getMemWord(lineStart).getWord());
			mem.setMemWord(lineStart, 9999999, "null");
			mem.incPointer2();
			deletedLines = deletedLines + 1;
			lineStart=decrement(lineStart);
			
		}

		Collections.reverse(lineList);

		disk.moveToDisk(pid, objList, lineList, pcbList);
		mem.decPointer2();
		if (minLinesToBeDeleted - deletedLines > 0) {
			deleteNextProcessBlock(minLinesToBeDeleted - deletedLines);
		}
	}
	private int increment(int x) {
		if(x==39) {
			x=0;
		}
		else {
			x=x+1;
		}
		return x;
	}
	private int decrement(int x) {
		if(x==0) {
			x=39;
		}
		else {
			x=x-1;
		}
		return x;
	}

	private void normalReserve(ArrayList<String> lines, ArrayList<String> pcb, ArrayList<String> objList) {// check
																											// before
																											// calling
		int memStart = mem.getPointer();
		String pcbId = pcb.get(0);
		String[] splited = pcbId.split(" ");
		int pid = Integer.parseInt(splited[3]);
		for (int i = 0; i < lines.size(); i++) {
			mem.setMemWord(mem.getPointer(), pid, lines.get(i));
			mem.incPointer();

		}
		int memEnd = mem.getPointer();
		for (int i = 0; i < 7; i++) {
			if (memEnd == 39)
				memEnd = 0;
			else
				memEnd++;
		}

		mem.setMemWord(mem.getPointer(), pid, pcb.get(0));
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, pcb.get(1));
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, pcb.get(2));
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, "memStart = " + memStart);
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, "memEnd = " + memEnd);
		mem.incPointer();


		for (int i = 0; i < objList.size(); i++) {
			mem.setMemWord(mem.getPointer(), pid, objList.get(i));
			mem.incPointer();
		}

 	
	}

	private void firstReserve(ArrayList<String> lines, Pcb pcb) {
		int pid = pcb.getPid();
		pcb.setMemStart(mem.getPointer());

		for (int i = 0; i < lines.size(); i++) {
			mem.setMemWord(mem.getPointer(), pid, lines.get(i));
			mem.incPointer();

		}
		int memEnd = pcb.getMemStart();
		for (int i = 0; i < lines.size() + 7; i++) {
			if (memEnd == 39)
				memEnd = 0;
			else
				memEnd++;
		}
		pcb.setMemEnd(memEnd);

		mem.setMemWord(mem.getPointer(), pid, "pcb id = " + pcb.getPid());
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, "state = " + pcb.getProcessState().toString());
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, "pc = " + pcb.getPc());
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, "memStart = " + pcb.getMemStart());
		mem.incPointer();

		mem.setMemWord(mem.getPointer(), pid, "memEnd = " + pcb.getMemEnd());
		mem.incPointer();

		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				ProcessObject obj = new ProcessObject("X", 5);
				String x = obj.getName();
				x = x.concat(" " + obj.getProcessData());
				mem.setMemWord(mem.getPointer(), pid, x);
				mem.incPointer();
			} else {
				if (i == 1) {
					ProcessObject obj = new ProcessObject("Y", 20);
					String x = obj.getName();
					x = x.concat(" " + obj.getProcessData());
					mem.setMemWord(mem.getPointer(), pid, x);
					mem.incPointer();
				} else {
					ProcessObject obj = new ProcessObject("Z", 55);
					String x = obj.getName();
					x = x.concat(" " + obj.getProcessData());
					mem.setMemWord(mem.getPointer(), pid, x);
					mem.incPointer();
				}
			}

		}

	}

	public int getCurPc() {
		return curPc;
	}

	public Memory getMem() {

		return mem;
	}

	public MemoryManager() {
		super();

	}
	public int remLinesCount(int pid)
	{
		int end=0;
		int start=0;
		for(int i=0;i<40;i++) {
			Word word=mem.getMemWord(i);
			if(word.getOwnerId()==pid && word.isIstaken()) {
				String[] splitted  = word.getWord().toString().split(" ");
				if(splitted[0].equals("memEnd")&&splitted[1].equals("=")) {
					end=Integer.parseInt(splitted[2]);
					break;
				
					}
				if(splitted[0].equals("memStart")&&splitted[1].equals("=")) {
					start=Integer.parseInt(splitted[2]);
				}
			}
		}
		int pc=getPc(pid);   	
		
		ArrayList<String> lineList=new ArrayList<String>();
		
		int lineStart=decrement(end); lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);lineStart=decrement(lineStart);
		int newStart = decrement(start);
		while(lineStart!=newStart) {
			if(mem.getMemWord(lineStart).getWord()!=null) {
				lineList.add( mem.getMemWord(lineStart).getWord().toString());
			}
			lineStart = decrement(lineStart);
		}
		
//		for (int i=end-8; i >= start; i--) {
//			if(mem.getMemWord(i).getWord()!=null) {
//			lineList.add( mem.getMemWord(i).getWord().toString());
//			if(start!=0) {
//			if(i==0) {
//				i=40;
//			}
//			}
//			}
//		}
		Collections.reverse(lineList);
		return lineList.size()-pc;

	}
	public boolean inMemory(int pid) {
		int pid2=-99;
		boolean res=false;
		for (int i = 0; i < 40; i++) {
			if(mem.getMemWord(i).getWord()!=null) {
				String[] splitted  = mem.getMemWord(i).getWord().toString().split(" ");
				if(splitted[0].equals("pcb")&&splitted[1].equals("id")&&splitted[2].equals("=")) {
					 pid2=Integer.parseInt(splitted[3]);
				}
				 if(pid==pid2) {
					res= true;
					break;
				}
				else {
					res= false;
				
				}
			}
		
			
		}
		return res;
	}
	public int getPc (int pid) {
		int pc=2;
		for(int i=0;i<40;i++) {
			Word word=mem.getMemWord(i);
			if(word.getOwnerId()==pid && word.isIstaken()) {
				String[] splitted  = word.getWord().toString().split(" ");
				if(splitted[0].equals("pc")&&splitted[1].equals("=")) {
					 pc=Integer.parseInt(splitted[2]);
				}
			}
			
		}
		return pc;
	}
	public String getNextLine(int pid) 
	{	
		int pcindex=-1;
		int pc=2;
		for(int i=0;i<40;i++) {
			Word word=mem.getMemWord(i);
			if(word.getOwnerId()==pid && word.isIstaken()) {
				String[] splitted  = word.getWord().toString().split(" ");
				if(splitted[0].equals("pc")&&splitted[1].equals("=")) {
					 pcindex=i;
					 pc=Integer.parseInt(splitted[2]);
				}
			}
			
		}
		int start=-20;
		int idCount =0;
		int end=-30;
		
		for(int i=0;i<40;i++) {
			Word word=mem.getMemWord(i);
			
			if(word.getOwnerId()==pid && word.isIstaken()) {
				idCount+=1;
			}
			if(idCount==1)
			{
			 start =i;
			}
			
			
		}
		for(int i=0;i<40;i++) {
			Word word=mem.getMemWord(i);
			if(word.getOwnerId()==pid && word.isIstaken()) {
				String[] splitted  = word.getWord().toString().split(" ");
				if(splitted[0].equals("memStart")&&splitted[1].equals("=")) {
					start=Integer.parseInt(splitted[2]);
					
				}
				if(splitted[0].equals("memEnd")&&splitted[1].equals("=")) {
					end=Integer.parseInt(splitted[2]);
					break;
					
				}
			}
			
		}
		
	
		int newPc=pc+1;
		
		
		mem.setMemWord(pcindex, pid, "pc = "+newPc);
		int lineIndex = start;
		for(int i =0;i<pc;i++)
		{
			lineIndex=increment(lineIndex);
		}
		return mem.getMemWord(lineIndex).getWord().toString();
		
	}

	
	
	
	
}