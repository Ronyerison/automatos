package br.ufpi.automatos.algoritmos.conversor;

import java.util.ArrayList;
import java.util.List;

public class FechoTransitivo {
	private String labelEstado;
	private String labelAlfabeto;
	private List<String> estadosAtingidos;
	
	public FechoTransitivo() {
		this.estadosAtingidos = new ArrayList<String>();
	}
	
	public String getLabelEstado() {
		return labelEstado;
	}
	
	public String getLabelAlfabeto() {
		return labelAlfabeto;
	}
	
	public List<String> getEstadosAtingidos() {
		return estadosAtingidos;
	}
	
	public void setLabelEstado(String labelEstado) {
		this.labelEstado = labelEstado;
	}
	
	public void setLabelAlfabeto(String labelAlfabeto) {
		this.labelAlfabeto = labelAlfabeto;
	}
	
	public void setEstadosAtingidos(List<String> estadosAtingidos) {
		this.estadosAtingidos = estadosAtingidos;
	}
	
}
