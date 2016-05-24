package br.ufpi.automatos.modelo;

import java.util.ArrayList;
import java.util.List;

public class Automato<E, T>{

	private String label;
	private List<Estado<E>> estados;
	private List<Transicao<T, E>> transicoes;
	private List<Estado<E>> estadosMarcados;
	private Estado<E> estadoInicial;
	private List<T> alfabeto;

	public Automato() {
		this.label = "";
		this.estados = new ArrayList<Estado<E>>();
		this.transicoes = new ArrayList<Transicao<T, E>>();
		this.estadosMarcados = new ArrayList<Estado<E>>();
		this.estadoInicial = null;
		this.alfabeto = new ArrayList<T>();
	}

	public Automato(Automato<E, T> automato) {
		this.label = automato.label;
		this.estadoInicial = automato.estadoInicial;
		this.estados = automato.estados;
		this.estadosMarcados = automato.estadosMarcados;
		this.transicoes = automato.transicoes;
		this.alfabeto = automato.alfabeto;
	}

	public Automato<E, T> clone() {
		return new Automato<E, T>(this);
	}

	public Automato(String label, List<Estado<E>> estados, List<Transicao<T, E>> transicoes,
			List<Estado<E>> estadosMarcados, Estado<E> estadoInicial, List<T> alfabeto) {
		this.label = label;
		this.estados = estados;
		this.transicoes = transicoes;
		this.estadosMarcados = estadosMarcados;
		this.estadoInicial = estadoInicial;
		this.alfabeto = alfabeto;
	}

	public void addEstado(Estado<E> estado) {
		if (!this.estados.contains(estado)) {
			this.estados.add(estado);
			if (estado.isInicial()) {
				this.estadoInicial = estado;
			}
			if (estado.isMarcado()) {
				this.estadosMarcados.add(estado);
			}
		}
	}

	public void addTransicao(Transicao<T, E> transicao) {
		if (!this.transicoes.contains(transicao)) {
			this.transicoes.add(transicao);
			if (!alfabeto.contains(transicao.getInfo())) {
				alfabeto.add(transicao.getInfo());
			}
		}
		if (!this.estados.contains(transicao.getOrigem())) {
			addEstado(transicao.getOrigem());
		}
		if (!this.estados.contains(transicao.getDestino())) {
			addEstado(transicao.getDestino());
		}
	}

	public void addTransicoes(List<Transicao<T, E>> transicoes) {
		for (Transicao<T, E> transicao : transicoes) {
			if (!this.transicoes.contains(transicao)) {
				this.transicoes.add(transicao);
				if (!alfabeto.contains(transicao.getInfo())) {
					alfabeto.add(transicao.getInfo());
				}
			}
			if (!this.estados.contains(transicao.getOrigem())) {
				addEstado(transicao.getOrigem());
			}
			if (!this.estados.contains(transicao.getDestino())) {
				addEstado(transicao.getDestino());
			}
		}
	}

	public List<Transicao<T, E>> getTransicoesByEstado(Estado<E> estado) {
		List<Transicao<T, E>> transicoes = new ArrayList<Transicao<T, E>>();
		for (Transicao<T, E> transicao : this.transicoes) {
			if (transicao.getOrigem().equals(estado)) {
				transicoes.add(transicao);
			}
		}
		return transicoes;
	}
	
	//TODO remover do alfabeto quando todas as transicoes com determinada label forem excluidas
	public void excluirTransicao(Transicao<T, E> transicao) {
		this.transicoes.remove(transicao);
	}

	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
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
	
	//TODO montar o alfabeto quando as transicoes forem setadas manualmente por este metodo
	public void setTransicoes(List<Transicao<T, E>> transicoes) {
		this.transicoes = transicoes;
		for (Transicao<T, E> transicao : transicoes) {
			if (!alfabeto.contains(transicao.getInfo())) {
				alfabeto.add(transicao.getInfo());
			}
		}
	}
	
	public List<T> getAlfabeto() {
		return alfabeto;
	}
	
	public void setAlfabeto(List<T> alfabeto) {
		this.alfabeto = alfabeto;
	}

	public Estado<E> getEstadoByLabel(String label) {
		for (int i = 0; i < estados.size(); i++) {
			if(((InfoEstado)estados.get(i).getInfo()).getLabel().equals(label))
				return estados.get(i);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Automato [estados=" + estados + ", transicoes=" + transicoes
				+ ", estadoInicial=" + estadoInicial + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((estadoInicial == null) ? 0 : estadoInicial.hashCode());
		result = prime * result + ((estados == null) ? 0 : estados.hashCode());
		result = prime * result
				+ ((estadosMarcados == null) ? 0 : estadosMarcados.hashCode());
		result = prime * result
				+ ((transicoes == null) ? 0 : transicoes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Automato<?, ?> other = (Automato<?, ?>) obj;
		if (estadoInicial == null) {
			if (other.estadoInicial != null)
				return false;
		} else if (!estadoInicial.equals(other.estadoInicial))
			return false;
		if (estados == null) {
			if (other.estados != null)
				return false;
		} else if (!estados.equals(other.estados))
			return false;
		if (estadosMarcados == null) {
			if (other.estadosMarcados != null)
				return false;
		} else if (!estadosMarcados.equals(other.estadosMarcados))
			return false;
		if (transicoes == null) {
			if (other.transicoes != null)
				return false;
		} else if (!transicoes.equals(other.transicoes))
			return false;
		return true;
	}
	
}