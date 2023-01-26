package processManager;

public class Process {
	private int id;
	private static int count = -1;
	private State state = State.Start;

	public int getId() {
		return id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Process() {
		super();
		this.id = ++count;
	}

}
