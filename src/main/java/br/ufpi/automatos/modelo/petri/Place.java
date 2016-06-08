package br.ufpi.automatos.modelo.petri;


/**
 * @author PedroAlmir
 */
public class Place extends PetriNetObject implements Comparable<Place>{

	// it's a magic number....
	public static final int UNLIMITED = -1;
	
	/** Token count */
	private int tokens = 0;
	private int maxTokens = UNLIMITED;
	
	/**
	 * @param name
	 */
	public Place(String name) {
		super(name);
	}

	/**
	 * @param name
	 * @param initial
	 */
	public Place(String name, int initial) {
		this(name);
		this.tokens = initial;
	}

	/**
	 * @param threshold
	 * @return
	 */
	public boolean hasAtLeastTokens(int threshold) {
		return (tokens >= threshold);
	}

	/**
	 * @param newTokens
	 * @return
	 */
	public boolean maxTokensReached(int newTokens) {
		if (hasUnlimitedMaxTokens()) { return false; }
		return (tokens + newTokens > maxTokens);
	}

	/**
	 * @return
	 */
	private boolean hasUnlimitedMaxTokens() {
		return maxTokens == UNLIMITED;
	}

	/**
	 * @return
	 */
	public int getTokens() {
		return tokens;
	}

	/**
	 * @param tokens
	 */
	public void setTokens(int tokens) {
		this.tokens = tokens;
	}

	/**
	 * @param max
	 */
	public void setMaxTokens(int max) {
		this.maxTokens = max;
	}

	/**
	 * @param weight
	 */
	public void addTokens(int weight) {
		this.tokens += weight;
	}

	/**
	 * @param weight
	 */
	public void removeTokens(int weight) {
		this.tokens -= weight;
	}

	@Override
	public String toString() {
		return super.toString() + " Tokens=" + this.tokens + " max=" + (hasUnlimitedMaxTokens() ? "unlimited" : this.maxTokens);
	}
	
	@Override
	public int compareTo(Place o) {
		return 0;
	}

	
}
