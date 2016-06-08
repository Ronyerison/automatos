package br.ufpi.automatos.modelo.petri;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PedroAlmir
 *
 */
public class Transition extends PetriNetObject implements Comparable<Transition>{

	private List<Arc> incoming;
	private List<Arc> outgoing;

	/**
	 * @param name
	 */
	public Transition(String name) {
		super(name);
		this.incoming = new ArrayList<Arc>();
		this.outgoing = new ArrayList<Arc>();
	}

	/**
	 * @return
	 */
	public boolean canFire() {
		boolean canFire = true;
		canFire = !this.isNotConnected();
		
		for (Arc arc : this.incoming) {
			canFire = canFire & arc.canFire();
		}

		for (Arc arc : this.outgoing) {
			canFire = canFire & arc.canFire();
		}
		return canFire;
	}

	/**
	 * Fire this transition
	 */
	public void fire() {
		for (Arc arc : this.incoming) {
			arc.fire();
		}
		for (Arc arc : this.outgoing) {
			arc.fire();
		}
	}
	
	/**
	 * @param arc
	 */
	public void addIncoming(Arc arc) {
		this.incoming.add(arc);
	}

	/**
	 * @param arc
	 */
	public void addOutgoing(Arc arc) {
		this.outgoing.add(arc);
	}

	/**
	 * @return
	 */
	public boolean isNotConnected() {
		return incoming.isEmpty() && outgoing.isEmpty();
	}

	/**
	 * @return the incoming
	 */
	public List<Arc> getIncoming() {
		return incoming;
	}

	/**
	 * @return the outgoing
	 */
	public List<Arc> getOutgoing() {
		return outgoing;
	}
	
	/**
	 * @return
	 */
	public List<Arc> getArcs(){
		this.incoming.addAll(this.outgoing);
		return this.incoming;
	}
	
	@Override
	public String toString() {
		return super.toString() + (isNotConnected() ? " IS NOT CONNECTED" : "") + (canFire() ? " READY TO FIRE" : "");
	}

	@Override
	public int compareTo(Transition o) {
		return 0;
	}
}
