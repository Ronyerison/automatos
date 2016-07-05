package br.ufpi.automatos.modelo.vo;

import br.ufpi.automatos.modelo.petri.Transition;

public class TransitionVO {
	
	private String name;

	public TransitionVO(Transition transition) {
		this.name = transition.getName();
	}
	
	public TransitionVO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TransitionVO [name=" + name + "]";
	}
	
}
