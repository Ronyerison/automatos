package br.ufpi.automatos.modelo.petri;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Petri Network.
 * This code was inspired in Simple-java-petrinet.
 * @see https://github.com/rmetzler/simple-java-petrinet
 * 
 * @author Pedro Almir
 */
public class PetriNet extends PetriNetObject {

	private static final String BREAK_LINE = "\n";
	/** List with arcs */
	private List<Arc> arcs;
	/** List with places */
	private List<Place> places;
	/** List with transitions */
	private List<Transition> transitions;
	/** List with inhibitor arcs */
	private List<InhibitorArc> inhibitors;

	/**
	 * @param name
	 */
	public PetriNet(String name) {
		super(name);
		this.arcs = new ArrayList<Arc>();
		this.places = new ArrayList<Place>();
		this.transitions = new ArrayList<Transition>();
		this.inhibitors = new ArrayList<InhibitorArc>();
	}

	/**
	 * @param o
	 */
	@Deprecated
	public void add(PetriNetObject o) {
		if (o instanceof Place) {
			this.places.add((Place) o);
		} else if (o instanceof Transition) {
			this.transitions.add((Transition) o);
		}
	}

	/**
	 * @return
	 */
	public List<Transition> getTransitionsAbleToFire() {
		ArrayList<Transition> list = new ArrayList<Transition>();
		for (Transition t : this.transitions) {
			if (t.canFire()) {
				list.add(t);
			}
		}
		return list;
	}

	/**
	 * @param name
	 * @return
	 */
	public Transition transition(String name) {
		Transition t = new Transition(name);
		this.transitions.add(t);
		return t;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Place place(String name) {
		Place p = new Place(name);
		this.places.add(p);
		return p;
	}

	/**
	 * @param name
	 * @param initial
	 * @return
	 */
	public Place place(String name, int initial) {
		Place p = new Place(name, initial);
		this.places.add(p);
		return p;
	}

	/**
	 * @param name
	 * @param p
	 * @param t
	 * @return
	 */
	public Arc arc(String name, Place p, Transition t) {
		Arc arc = new Arc(name, p, t);
		this.arcs.add(arc);
		return arc;
	}

	/**
	 * @param name
	 * @param t
	 * @param p
	 * @return
	 */
	public Arc arc(String name, Transition t, Place p) {
		Arc arc = new Arc(name, t, p);
		this.arcs.add(arc);
		return arc;
	}

	/**
	 * @param name
	 * @param p
	 * @param t
	 * @return
	 */
	public InhibitorArc inhibitor(String name, Place p, Transition t) {
		InhibitorArc i = new InhibitorArc(name, p, t);
		this.inhibitors.add(i);
		return i;
	}

	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.system.simulation.petri.base.PetriNetObject#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Petrinet ");
		sb.append(super.toString()).append(BREAK_LINE);
		sb.append("---Transitions---").append(BREAK_LINE);
		for (Transition t : transitions) {
			sb.append(t).append(BREAK_LINE);
		}
		sb.append("---Places---").append(BREAK_LINE);
		for (Place p : places) {
			sb.append(p).append(BREAK_LINE);
		}
		return sb.toString();
	}

	/**
	 * @return
	 */
	public List<Place> getPlaces() {
		return this.places;
	}

	/**
	 * @return
	 */
	public List<Transition> getTransitions() {
		return this.transitions;
	}

	/**
	 * @return
	 */
	public List<Arc> getArcs() {
		return this.arcs;
	}

	/**
	 * @return
	 */
	public List<InhibitorArc> getInhibitors() {
		return this.inhibitors;
	}
}
