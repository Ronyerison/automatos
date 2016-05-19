package br.ufpi.automatos.modelo;

import java.util.ArrayList;
import java.util.List;

public class Automato<E, T>{
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

	public Automato(Automato<E, T> automato) {
		this.estadoInicial = automato.estadoInicial;
		this.estados = automato.estados;
		this.estadosMarcados = automato.estadosMarcados;
		this.transicoes = automato.transicoes;
	}

	public Automato<E, T> clone() {
		return new Automato<E, T>(this);
	}

	public Automato(List<Estado<E>> estados, List<Transicao<T, E>> transicoes,
			List<Estado<E>> estadosMarcados, Estado<E> estadoInicial) {
		this.estados = estados;
		this.transicoes = transicoes;
		this.estadosMarcados = estadosMarcados;
		this.estadoInicial = estadoInicial;
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

	public void excluirTransicao(Transicao<T, E> transicao) {
		this.transicoes.remove(transicao);
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