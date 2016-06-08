package br.ufpi.automatos.modelo.petri;


/**
 * @author Pedro Almir
 */
public class Arc extends Thread implements Comparable<Arc>{
	
	protected Place place;
	protected Transition transition;
	protected Direction direction;
	protected int weight = 1;

	/**
	 * @param name
	 * @param d
	 * @param p
	 * @param t
	 */
	private Arc(String name, Direction d, Place p, Transition t) {
		super(name);
		this.place = p;
		this.direction = d;
		this.transition = t;
	}

	/**
	 * @param name
	 * @param p
	 * @param t
	 */
	public Arc(String name, Place p, Transition t) {
		this(name, Direction.PLACE_TO_TRANSITION, p, t);
		t.addIncoming(this);
	}

	/**
	 * @param name
	 * @param t
	 * @param p
	 */
	public Arc(String name, Transition t, Place p) {
		this(name, Direction.TRANSITION_TO_PLACE, p, t);
		t.addOutgoing(this);
	}

	/**
	 * @return
	 */
	public boolean canFire() {
		return direction.canFire(this.place, this.weight);
	}

	/**
	 * 
	 */
	public void fire() {
		this.direction.fire(this.place, this.weight);
	}

	/**
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return this.place;
	}

	/**
	 * @return the transition
	 */
	public Transition getTransition() {
		return this.transition;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void run() {
		this.fire();
	}

	@Override
	public int compareTo(Arc o) {
		return 0;
	}
}
