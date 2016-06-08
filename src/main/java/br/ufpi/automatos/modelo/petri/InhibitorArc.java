package br.ufpi.automatos.modelo.petri;


/**
 * @author PedroAlmir
 */
public class InhibitorArc extends Arc {

	/**
	 * @param name
	 * @param p
	 * @param t
	 */
	public InhibitorArc(String name, Place p, Transition t) {
		super(name, p, t);
	}

	@Override
	public boolean canFire() {
		return (place.getTokens() < this.getWeight());
	}
	
	@Override
	public void fire() {/* Do nothing */}

}
