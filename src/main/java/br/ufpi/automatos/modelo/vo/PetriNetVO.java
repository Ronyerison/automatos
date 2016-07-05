package br.ufpi.automatos.modelo.vo;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.petri.Arc;
import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.modelo.petri.Place;
import br.ufpi.automatos.modelo.petri.Transition;

public class PetriNetVO {
	private List<ArcVO> arcs;
	private List<PlaceVO> places;
	private List<TransitionVO> transitions;
	
	public PetriNetVO(PetriNet petriNet) {
		this.arcs = new ArrayList<ArcVO>();
		this.places = new ArrayList<PlaceVO>();
		this.transitions = new ArrayList<TransitionVO>();
		for (Place place : petriNet.getPlaces().values()) {
			this.places.add(new PlaceVO(place));
		}
		for (Transition transition : petriNet.getTransitions().values()) {
			this.transitions.add(new TransitionVO(transition));
		}
		for (Arc arc : petriNet.getArcs()) {
			this.arcs.add(new ArcVO(arc));
		}
	}
	
	public List<ArcVO> getArcs() {
		return arcs;
	}
	
	public void setArcs(List<ArcVO> arcs) {
		this.arcs = arcs;
	}
	
	public List<PlaceVO> getPlaces() {
		return places;
	}
	
	public void setPlaces(List<PlaceVO> places) {
		this.places = places;
	}
	
	public List<TransitionVO> getTransitions() {
		return transitions;
	}
	
	public void setTransitions(List<TransitionVO> transitions) {
		this.transitions = transitions;
	}
	
	
}
