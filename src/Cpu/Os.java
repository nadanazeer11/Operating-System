package Cpu;

import java.util.ArrayList;


import memoryManager.*;
import Scheduler.Dispatcher;

import processManager.Pcb;
import processManager.Process;
import processManager.State;


public class Os {
	
	private Dispatcher dispatcher;

	private MemoryManager memoryManager;

	private FileInterpreter interpreter;
	private final int schedulerCycles = 2;
	private int timer = 0;
	private static int totalExecutedLineCount = 0;

	public Os() {
		super();
		Memory mem = new Memory();
		Disk disk = new Disk();
		memoryManager = new MemoryManager(mem, disk);

		dispatcher = new Dispatcher();

		interpreter = new FileInterpreter();

		System.out.println("Os Initialized");
	}

	public void runReadyQ() {
		while (!dispatcher.readyIsEmpty()) {
			Process x = dispatcher.dequeueReady();
			boolean existInMem = memoryManager.inMemory(x.getId());
			
			if (existInMem == false) {

				memoryManager.reserveMemory(x.getId());

			}
				
			int pid = x.getId();
			int remLines = memoryManager.remLinesCount(pid);

			if (remLines > schedulerCycles) {
				for (int i = 0; i < schedulerCycles; i++) {
					boolean executingProcessIsInMemory = memoryManager.inMemory(pid);
					if (!executingProcessIsInMemory) {
						memoryManager.reserveMemory(pid);
					}
					String line = memoryManager.getNextLine(pid);
					System.out.println("Eceuting Line" + memoryManager.getPc(pid) + ": " + line);
					totalExecutedLineCount = totalExecutedLineCount + 1;
					startProgram();
					
				}
				memoryManager.getMem().printMemoryContents();
				dispatcher.enqueueReady(x);
			} else {
				for (int i = 0; i < remLines; i++) {
					boolean executingProcessIsInMemory = memoryManager.inMemory(pid);
					if (!executingProcessIsInMemory) {
						memoryManager.reserveMemory(pid);
					}
					String line = memoryManager.getNextLine(pid);
					System.out.println("Eceuting Line" + memoryManager.getPc(pid) + ": " + line + "  pid" + pid);
					totalExecutedLineCount = totalExecutedLineCount + 1;
					startProgram();
				}
				System.out.println("Process number " + pid + " is exiting");
				memoryManager.getMem().printMemoryContents();
			}

		}
	}

	public void openProgram(String fileName) {

		Process process = new Process();
		ArrayList<String> list = new ArrayList<String>();
		interpreter.readFileIntoList(fileName, list);
		int pid = process.getId();
		Pcb pcb = new Pcb(pid);
		dispatcher.enqueueReady(process);
		pcb.setProcessState(State.Ready);
		memoryManager.admitInMemory(list, pcb);// wa7ed galy dakhalto el queue w el memory
		// runReadyQ();

	}

	// at time kaza dakhal kaza el ready queue
	public void startProgram() {
		if (totalExecutedLineCount == 0) {
			openProgram("Program_1.txt");
			//memoryManager.getMem().printMemoryContents();
		}
		if (totalExecutedLineCount == 1) {
			openProgram("Program_2.txt");
			//memoryManager.getMem().printMemoryContents();
		}
		if (totalExecutedLineCount == 4) {
			openProgram("Program_3.txt");
			//memoryManager.getMem().printMemoryContents();
		}
	}

	public static void main(String[] args) {
		Os os = new Os();
		os.startProgram();
		os.runReadyQ();

	}

}
