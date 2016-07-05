package br.ufpi.automatos.modelo.vo;

import br.ufpi.automatos.modelo.petri.Arc;
import br.ufpi.automatos.modelo.petri.Direction;

public class ArcVO {
	private String weight;
	private String origin;
	private String destiny;
	
	public ArcVO(Arc arc) {
		this.weight = String.valueOf(arc.getWeight());
		if(arc.getDirection().equals(Direction.PLACE_TO_TRANSITION)){
			this.origin = arc.getPlace().getName();
			this.destiny = arc.getTransition().getName();
		}else{
			this.destiny = arc.getPlace().getName();
			this.origin  = arc.getTransition().getName();
		}
	}
	
	public ArcVO(String weight, String origin, String destiny) {
		this.weight = weight;
		this.origin = origin;
		this.destiny = destiny;
	}

	public String getWeight() {
		return weight;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getDestiny() {
		return destiny;
	}
	
	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	@Override
	public String toString() {
		return "ArcVO [weight=" + weight + ", origin=" + origin + ", destiny="
				+ destiny + "]";
	}
}
