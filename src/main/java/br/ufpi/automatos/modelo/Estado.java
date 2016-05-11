package br.ufpi.automatos.modelo;

import java.util.ArrayList;
import java.util.List;

public class Estado<E> {
	private E info = null;
	private List<Estado<E>> estadosAdj = new ArrayList<Estado<E>>();
	private List<Transicao<String, E>> transicoes = new ArrayList<Transicao<String,E>>();
	private boolean inicial;
	private boolean marcado;

	public Estado(E info) {
		this.info = info;
	}
	
	public Estado(E info, boolean inicial, boolean marcado) {
		this.info = info;
		this.inicial = inicial;
		this.marcado = marcado;
	}



	public List<Estado<E>> getEstadosAdjacentes() {
		return estadosAdj;
	}

	public void addTransicao(E info, String valorTransicao) {
		Estado<E> destino = new Estado<E>(info);
		this.estadosAdj.add(destino);
		this.addTransicao(new Transicao<String, E>(valorTransicao, this, destino));
	}

	public void addTransicao(Estado<E> destino, String valorTransicao) {
		this.estadosAdj.add(destino);
		this.transicoes.add(new Transicao<String, E>(valorTransicao, this, destino));
	}

	public E getInfo() {
		return this.info;
	}

	public void setInfo(E info) {
		this.info = info;
	}

	public List<Transicao<String, E>> getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(List<Transicao<String, E>> transicoes) {
		this.transicoes = transicoes;
	}
	
	public void addTransicao(Transicao<String, E> transicao){
		this.transicoes.add(transicao);
	}

	public boolean isInicial() {
		return inicial;
	}

	public void setInicial(boolean inicial) {
		this.inicial = inicial;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	@Override
	public String toString() {
		return info+"";
	}
}
