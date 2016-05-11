package br.ufpi.automatos.modelo;

import java.util.ArrayList;
import java.util.List;

public class Automato<E, T> {
	private List<Estado<E>> estados;
	private List<Transicao<T, E>> transicoes;
	private List<Estado<E>> estadosMarcados;
	private Estado<E> estadoInicial;
	
	public Automato() {
		this.estados = new ArrayList<Estado<E>>();
		this.transicoes = new ArrayList<Transicao<T, E>>();
		this.estadosMarcados = new ArrayList<Estado<E>>();
		this.estadoInicial = null;
	}

	public Automato(List<Estado<E>> estados, List<Transicao<T, E>> transicoes, List<Estado<E>> estadosMarcados,
			Estado<E> estadoInicial) {
		this.estados = estados;
		this.transicoes = transicoes;
		this.estadosMarcados = estadosMarcados;
		this.estadoInicial = estadoInicial;
	}
	
	public void addEstado(Estado<E> estado){
		this.estados.add(estado);
		if(estado.isInicial()){
			this.estadoInicial = estado;
		}
		if(estado.isMarcado()){
			this.estadosMarcados.add(estado);
		}
	}
	
	public void addTransicao(Transicao<T, E> transicao){
		if(!this.transicoes.contains(transicao)){
			this.transicoes.add(transicao);
		}
		if(!this.estados.contains(transicao.getOrigem())){
			addEstado(transicao.getOrigem());
		}
		if(!this.estados.contains(transicao.getDestino())){
			addEstado(transicao.getDestino());
		}
	}

	public List<Estado<E>> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado<E>> estados) {
		this.estados = estados;
	}

	public List<Estado<E>> getEstadosMarcados() {
		return estadosMarcados;
	}

	public void setEstadosMarcados(List<Estado<E>> estadosMarcados) {
		this.estadosMarcados = estadosMarcados;
	}

	public Estado<E> getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(Estado<E> estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public List<Transicao<T, E>> getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(List<Transicao<T, E>> transicoes) {
		this.transicoes = transicoes;
	}

	@Override
	public String toString() {
		return "Automato [estados=" + estados + ", transicoes=" + transicoes + ", estadoInicial=" + estadoInicial + "]";
	}
	
}