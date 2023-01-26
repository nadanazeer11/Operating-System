package Scheduler;

import memoryManager.ProcessObject;
import processManager.Process;

public class ProcessQueue {
	private QueueObj queue;

	protected ProcessQueue() {
		this.queue = new QueueObj(3);

	}

	public void enqueue(Process process) {
		queue.enqueue(process);
	}

	public Process dequeue() {
		Object obj = queue.dequeue();
		Process process = ((Process) obj);
		return process;
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public void printQueue() {
		for (int i = 0; i < queue.size(); i++) {

			Process process = (Process) queue.dequeue();
			if (process == null) {
				break;
			}
			System.out.print(" " + "Process Id = " + process.getId());
			queue.enqueue(process);
		}
		System.out.println();

	}

}
