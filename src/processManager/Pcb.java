package processManager;

public class Pcb {
	private int pid;
	private State processState;
	private int pc;
	private int memStart;
	private int memEnd;

	public Pcb(int pid) {
		this.pid = pid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public State getProcessState() {
		return processState;
	}

	public void setProcessState(State processState) {
		this.processState = processState;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public int getMemStart() {
		return memStart;
	}

	public void setMemStart(int memStart) {
		this.memStart = memStart;
	}

	public int getMemEnd() {
		return memEnd;
	}

	public void setMemEnd(int memEnd) {
		this.memEnd = memEnd;
	}

}
