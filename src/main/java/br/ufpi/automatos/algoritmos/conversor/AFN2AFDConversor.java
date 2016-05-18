package br.ufpi.automatos.algoritmos.conversor;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;

public class AFN2AFDConversor {
	
	private static final String ELEMENTO_VAZIO = "$";
	
	public AFN2AFDConversor() {
	}
	
	public <E, T> Automato<E, T> converter(Automato<E, T> automatoAFN){
		return null;
	}
	
	public <E, T> List<Estado<E>> construirFecho(Automato<E, T> automato, Estado<E> estado, T letra) {
		List<Estado<E>> fecho = new ArrayList<>();
		List<Estado<E>> f1 = new ArrayList<>();
		f1.addAll(construirFechoVazio(automato, estado));
		List<Estado<E>> f2 = new ArrayList<>(construirFechoPorLetra(f1, automato.getTransicoes(), letra));
		List<Estado<E>> f3 = new ArrayList<>();
		
		for (Estado<E> e : f2) {
			f3.addAll(construirFechoVazio(automato, e));
		}
		
		fecho.addAll(f3);
		return fecho;
	}
	
	private <E, T, L> List<Estado<E>> construirFechoPorLetra(List<Estado<E>> listaEstados, List<Transicao<T, E>> listaTransicoes, L letra){
		List<Estado<E>> estadosAux = new ArrayList<>();
		for (Estado<E> e : listaEstados) {
			for (Transicao<T, E> t : listaTransicoes) {
				if(t.getOrigem().getInfo().equals(e.getInfo()) && t.getInfo().equals(letra)){
					if(!estadosAux.contains(t.getDestino())){
						estadosAux.add(t.getDestino());
					}
				}
			}
		}
		return estadosAux;
	}
	
	private <E, T> List<Estado<E>> construirFechoVazio(Automato<E, T> automato, Estado<E> estadoRef){
		List<Estado<E>> fechoVazio = new ArrayList<>();
		fechoVazio.add(estadoRef);
		
		int indiceEstado = 0;
		int tamFechoVazio;
		boolean flag = true;
		
		while(flag){
			tamFechoVazio = fechoVazio.size();
			if(indiceEstado < tamFechoVazio){
				construirFechoVazioPorEstado(fechoVazio, automato.getTransicoes(), indiceEstado);
				indiceEstado++;
			}else{
				flag = false;
			}
		}
		return fechoVazio;
	}
	
	private <E, T> void construirFechoVazioPorEstado(List<Estado<E>> listaEstados, List<Transicao<T, E>> listaTransicoes, int indice){
		Estado<E> estadoReferencia = listaEstados.get(indice);
		for (Transicao<T, E> t : listaTransicoes) {
			if(t.getOrigem().getInfo().equals(estadoReferencia.getInfo()) && t.getInfo().equals(ELEMENTO_VAZIO)){
				if(!listaEstados.contains(t.getDestino())){
					listaEstados.add(t.getDestino());
				}
			}
		}
	}
}
