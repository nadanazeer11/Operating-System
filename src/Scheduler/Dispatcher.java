package Scheduler;

import processManager.Process;

public class Dispatcher 
{
	private ProcessQueue readyQueue;
	
	public Process dequeueReady() {
		Process process = this.readyQueue.dequeue();
		System.out.println("Dequeueing Process in ReadyQ, Id = " + process.getId());
		// printAllQueues();
		return process;

	}

	public void enqueueReady(Process process) {
		System.out.println("Enqueueing Process in ReadyQ, Id = " + process.getId());

		this.readyQueue.enqueue(process);
		printAllQueues();
	}



	public Dispatcher() {
		super();
		this.readyQueue = new ProcessQueue();
//		this.blockedInputQueue = new ProcessQueue();
//		this.blockedOutputQueue = new ProcessQueue();
//		this.blockedFileQueue = new ProcessQueue();
		
	}

	public void printAllQueues() {
		
		System.out.println("---------------------------------------");
		System.out.println("Printing ReadyQueue");
		readyQueue.printQueue();
		System.out.println("---------------------------------------");
	}

	
	public boolean readyIsEmpty() {
		return readyQueue.isEmpty();
	}



}
