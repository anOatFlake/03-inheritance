package ohm.softa.a03;

public class Cat {

	// initially, animals are sleeping
	private State currentState = new SleepingState();

	// state durations (set via constructor), ie. the number of ticks in each state
	private final int sleep;
	private final int awake;
	private final int digest;

	private final String name;

	public Cat(String name, int sleep, int awake, int digest) {
		this.name = name;
		this.sleep = sleep;
		this.awake = awake;
		this.digest = digest;
	}

	public void tick(){
		currentState = currentState.tick(this);
	}

	/**
	 * This would be a user interaction: feed the cat to change its state!
	 */
	public void feed(){
		if (!isHungry())
			throw new IllegalStateException("Can't stuff a cat...");
		else {
			currentState = ((HungryState) currentState).feed(this);
		}
	}

	public boolean isAsleep() {	return currentState instanceof SleepingState; }

	public boolean isPlayful() { return currentState instanceof PlayfulState; }

	public boolean isHungry() {	return currentState instanceof HungryState;	}

	public boolean isDigesting() { return currentState instanceof DigestingState; }

	public boolean isDead() { return currentState instanceof DeathState; }

	public int getSleep() { return sleep; }
	public int getDigest() { return digest; }
	public int getAwake() {	return awake; }

	@Override
	public String toString() {
		return name;
	}

}
