package br.ufpi.automatos.modelo.vo;

import br.ufpi.automatos.modelo.petri.Place;

public class PlaceVO {
	
	private String name;
	private String tokens;
	
	public PlaceVO(Place place) {
		this.name = place.getName();
		this.tokens = String.valueOf(place.getTokens());
	}

	public PlaceVO(String name, String tokens) {
		this.name = name;
		this.tokens = tokens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	@Override
	public String toString() {
		return "PlaceVO [name=" + name + "]";
	}
	
}
