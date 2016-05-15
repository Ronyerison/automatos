package br.ufpi.automatos.algoritmos;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;

public class Algoritmo<E, T> {

	public Automato<E, T> acessibilidade(Automato<E, T> automato){
		List<Estado<E>> estadosVisitados = new ArrayList<Estado<E>>();
		Automato<E, T> result = new Automato<E, T>();
		
		result.addEstado(automato.getEstadoInicial());
		estadosVisitados.add(automato.getEstadoInicial());
		List<Estado<E>> estadosAdj = addEstadosAdj(automato, result, automato.getEstadoInicial(), estadosVisitados);
		
		while(!estadosAdj.isEmpty() || !result.equals(automato)){
			estadosAdj.removeAll(estadosVisitados);
			estadosAdj.addAll(addEstadosAdj(automato, result, estadosAdj.get(0), estadosVisitados));
		}
		return result;
	}

	/**
	 * @param automato
	 * @param result
	 * @param estado 
	 * @param estadosVisitados 
	 */
	private List<Estado<E>> addEstadosAdj(Automato<E, T> automato, Automato<E, T> result, Estado<E> estado, List<Estado<E>> estadosVisitados) {
		List<Transicao<T, E>> transicoes = automato.getTransicoesByEstado(estado);
		result.addTransicoes(transicoes);
		List<Estado<E>> estados = new ArrayList<Estado<E>>();
		for (Transicao<T, E> t : transicoes) {
			result.addEstado(t.getDestino());
			if(!t.getDestino().equals(t.getOrigem()) && !estadosVisitados.contains(t.getDestino()))
				estados.add(t.getDestino());
		}
		return estados;
	}
}
