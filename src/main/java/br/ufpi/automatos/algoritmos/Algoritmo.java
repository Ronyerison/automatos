package br.ufpi.automatos.algoritmos;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;

public class Algoritmo<E, T> {

	public Automato<E, T> trim(Automato<E, T> automato){
		Automato<E, T> result = acessibilidade(automato);
		result = coacessibilidade(result);
		result.setLabel("TRIM_" + automato.getLabel());
		return result;
	}
	
	public Automato<E, T> acessibilidade(Automato<E, T> automato) {
		List<Estado<E>> estadosVisitados = new ArrayList<Estado<E>>();
		Automato<E, T> result = new Automato<E, T>();

		result.addEstado(automato.getEstadoInicial());
		estadosVisitados.add(automato.getEstadoInicial());
		List<Estado<E>> estadosAdj = addEstadosAdj(automato, result,
				automato.getEstadoInicial(), estadosVisitados);

		while (!estadosAdj.isEmpty() && !result.equals(automato)) {
			for (Estado<E> est : estadosVisitados) {
				if (estadosAdj.contains(est)) {
					estadosAdj.remove(est);
				}
			}
			if (!estadosAdj.isEmpty()) {
				List<Estado<E>> adj = addEstadosAdj(automato, result,
						estadosAdj.get(0), estadosVisitados);
				for (Estado<E> estado : adj) {
					estadosAdj.add(estado);
				}
				estadosVisitados.add(estadosAdj.get(0));
			}
		}
		return result;
	}

	
	
	public Automato<E, T> coacessibilidade(Automato<E, T> automato) {
		Automato<E, T> resultado = new Automato<E, T>();
		for (Estado<E> estado : automato.getEstadosMarcados()) {
			if(!estado.isInicial() || !automato.getTransicoesByDestino(estado).isEmpty()){
				buscarCaminho(automato, estado, resultado);
			}else{
				resultado.addEstado(estado);
			}
		}
		
		return resultado;
	}

	private void buscarCaminho(Automato<E, T> automato, Estado<E> estado, Automato<E, T> resultado){
		List<Transicao<T, E>> t = automato.getTransicoesByDestino(estado);
		for (Transicao<T, E> transicao : t) {
			if(!resultado.getTransicoes().contains(transicao)){
				resultado.addTransicao(transicao);
				buscarCaminho(automato, transicao.getOrigem(), resultado);
			}
		}
	}
	

	/**
	 * @param automato
	 * @param result
	 * @param estado
	 * @param estadosVisitados
	 */
	private List<Estado<E>> addEstadosAdj(Automato<E, T> automato,
			Automato<E, T> result, Estado<E> estado,
			List<Estado<E>> estadosVisitados) {
		List<Transicao<T, E>> transicoes = automato
				.getTransicoesByEstado(estado);
		result.addTransicoes(transicoes);
		List<Estado<E>> estados = new ArrayList<Estado<E>>();
		for (Transicao<T, E> t : transicoes) {
			result.addEstado(t.getDestino());
			if (!t.getDestino().equals(t.getOrigem())
					&& !estadosVisitados.contains(t.getDestino()))
				estados.add(t.getDestino());
		}
		return estados;
	}
}
