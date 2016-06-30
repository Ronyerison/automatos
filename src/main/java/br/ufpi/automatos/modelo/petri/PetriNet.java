package br.ufpi.automatos.modelo.petri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;

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
	private Map<String, Place> places;
	/** List with transitions */
	private Map<String, Transition> transitions;

	/**
	 * @param name
	 */
	public PetriNet(String name) {
		super(name);
		this.arcs = new ArrayList<Arc>();
		this.places = new HashMap<String, Place>();
		this.transitions = new HashMap<String, Transition>();
	}
	
	public PetriNet(PetriNet petriNet) {
		super(petriNet.getName());
		this.arcs = new ArrayList<>(petriNet.getArcs());
		this.places = new HashMap<>();
		this.places.putAll(petriNet.getPlaces());
		this.transitions = new HashMap<>();
		this.transitions.putAll(petriNet.getTransitions());
	}

	/**
	 * @return
	 */
	public List<Transition> getTransitionsAbleToFire() {
		ArrayList<Transition> list = new ArrayList<Transition>();
		for (Transition t : this.transitions.values()) {
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
		this.transitions.put(name, t);
		return t;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Place place(String name) {
		Place p = new Place(name);
		this.places.put(name, p);
		return p;
	}

	/**
	 * @param name
	 * @param initial
	 * @return
	 */
	public Place place(String name, int initial) {
		Place p = new Place(name, initial);
		this.places.put(name, p);
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
		arc.setWeight(Integer.valueOf(name));
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
		arc.setWeight(Integer.valueOf(name));
		this.arcs.add(arc);
		return arc;
	}


	/* (non-Javadoc)
	 * @see br.ufpi.easii.athena.core.system.simulation.petri.base.PetriNetObject#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Petrinet ");
		sb.append(super.toString()).append(BREAK_LINE);
		sb.append("---Transitions---").append(BREAK_LINE);
		for (Transition t : transitions.values()) {
			sb.append(t).append(BREAK_LINE);
		}
		sb.append("---Places---").append(BREAK_LINE);
		for (Place p : places.values()) {
			sb.append(p).append(BREAK_LINE);
		}
		return sb.toString();
	}

	public Map<String, Place> getPlaces() {
		return places;
	}

	public Map<String, Transition> getTransitions() {
		return transitions;
	}
	
	public PetriNet clone() {
		return new PetriNet(this);
	}

	public List<Arc> getArcs() {
		return this.arcs;
	}

}
