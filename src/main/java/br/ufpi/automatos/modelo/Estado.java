package br.ufpi.automatos.modelo;

import java.util.ArrayList;
import java.util.List;

public class Estado<T> {
	private Estado<T> estadoPai = null;
	private T info = null;
	private List<Estado<T>> estadosAdj = new ArrayList<Estado<T>>();
	private List<Transicao<String, T>> transicoes = new ArrayList<Transicao<String,T>>();

	public Estado(T info) {
		this.info = info;
	}

	public Estado(T info, Estado<T> estadoPai, String valorTransicao) {
		this.info = info;
		this.estadoPai = estadoPai;
		this.estadoPai.addTransicao(new Transicao<String, T>(valorTransicao, estadoPai, this));
	}

	public List<Estado<T>> getEstadosAdjacentes() {
		return estadosAdj;
	}

	public void setEstadoPai(Estado<T> estadoPai, String valorTransicao) {
		estadoPai.addTransicao(this, valorTransicao);
		this.estadoPai = estadoPai;
	}

	public void addTransicao(T info, String valorTransicao) {
		Estado<T> child = new Estado<T>(info);
		this.estadosAdj.add(child);
		this.addTransicao(new Transicao<String, T>(valorTransicao, this, child));
	}

	public void addTransicao(Estado<T> destino, String valorTransicao) {
		this.estadosAdj.add(destino);
		this.transicoes.add(new Transicao<String, T>(valorTransicao, this, destino));
	}

	public T getInfo() {
		return this.info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	public boolean isRoot() {
		return (this.estadoPai == null);
	}

	public boolean isLeaf() {
		if (this.estadosAdj.size() == 0)
			return true;
		else
			return false;
	}

	public void removeEstadoPai() {
		this.estadoPai = null;
	}

	/**
	 * @return the transicoes
	 */
	public List<Transicao<String, T>> getTransicoes() {
		return transicoes;
	}

	/**
	 * @param transicoes the transicoes to set
	 */
	public void setTransicoes(List<Transicao<String, T>> transicoes) {
		this.transicoes = transicoes;
	}
	
	public void addTransicao(Transicao<String, T> transicao){
		this.transicoes.add(transicao);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return info+"";
	}
}
