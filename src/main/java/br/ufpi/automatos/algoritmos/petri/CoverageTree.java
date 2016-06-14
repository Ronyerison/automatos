package br.ufpi.automatos.algoritmos.petri;

import java.util.Collection;
import java.util.List;

import br.ufpi.automatos.modelo.petri.Arc;
import br.ufpi.automatos.modelo.petri.Direction;
import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.modelo.petri.Place;
import br.ufpi.automatos.modelo.petri.Transition;

public class CoverageTree {
	
	public int[][] incidenceMatrix(PetriNet petriNet){
		int linesNum = petriNet.getTransitions().size();
		int colunmNum = petriNet.getPlaces().size();
		int[][] matrix = new int[linesNum][colunmNum];
		
		Collection<Place> places = petriNet.getPlaces().values();
		Collection<Transition> transitions = petriNet.getTransitions().values();
		
		int i = 0, j = 0;
		
		for (Transition transition : transitions) {
			for (Place place : places) {
				matrix[i][j] = calculeValue(petriNet.getArcs(), place.getName(), transition.getName());
				j++;
			}
			j=0;
			i++;
		}
		return matrix;
	}
	
	private int calculeValue(List<Arc> arcs, String placeName, String transitionName){
		int in = 0;
		int out = 0;

		for (Arc arc : arcs) {
			if(arc.getPlace().getName().equals(placeName) && arc.getTransition().getName().equals(transitionName)){
				if(arc.getDirection().equals(Direction.TRANSITION_TO_PLACE)){
					out += arc.getWeight();
				}else{
					in += arc.getWeight(); 
				}
			}
		}
		return out - in;
	}
}
