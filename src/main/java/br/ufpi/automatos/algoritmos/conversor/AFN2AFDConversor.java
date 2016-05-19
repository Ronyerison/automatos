package br.ufpi.automatos.algoritmos.conversor;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;
import br.ufpi.automatos.util.Constante;

public class AFN2AFDConversor<E, T> {
	
	public AFN2AFDConversor() {
	}
	
	public Automato<E, T> converter(Automato<E, T> automatoAFN){
		List<FechoTransitivo<E, T>> fechos = new ArrayList<>();
		fechos = obterFechos(automatoAFN);
		
		for (FechoTransitivo<E, T> f : fechos) {
			if(f.getFecho().size() > 1){
				
			}
		}
		return null;
	}
	
	private boolean verificarFechoEstado(List<FechoTransitivo<E, T>> fechos, List<Estado<E>> fecho){
		return false;
	}

	public List<FechoTransitivo<E, T>> obterFechos(Automato<E, T> automato) {
		List<T> alfa = new ArrayList<>();
		alfa = obterAlfabeto(automato);
		List<FechoTransitivo<E, T>> fechos = new ArrayList<>();
		FechoTransitivo<E, T> fecho = new FechoTransitivo<>();
		List<Estado<E>> estadoUnificado;
		
		for (Estado<E> e : automato.getEstados()) {
			for (T a : alfa) {
				estadoUnificado = new ArrayList<Estado<E>>();
				estadoUnificado.add(e);
				fecho.setEstadoUnificado(estadoUnificado);
				fecho.setFecho(construirFecho(automato, e, a));
				fecho.setInfoTransicao(a);
				fechos.add(fecho);
				fecho = new FechoTransitivo<>();
			}
		}
		return fechos;
	}
	
	public List<Estado<E>> construirFecho(Automato<E, T> automato, Estado<E> estado, T letra) {
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
	
	private List<Estado<E>> construirFechoPorLetra(List<Estado<E>> listaEstados, List<Transicao<T, E>> listaTransicoes, T letra){
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
	
	private List<Estado<E>> construirFechoVazio(Automato<E, T> automato, Estado<E> estadoRef){
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
	
	private void construirFechoVazioPorEstado(List<Estado<E>> listaEstados, List<Transicao<T, E>> listaTransicoes, int indice){
		Estado<E> estadoReferencia = listaEstados.get(indice);
		for (Transicao<T, E> t : listaTransicoes) {
			if(t.getOrigem().getInfo().equals(estadoReferencia.getInfo()) && t.getInfo().equals(Constante.getElementoVazio())){
				if(!listaEstados.contains(t.getDestino())){
					listaEstados.add(t.getDestino());
				}
			}
		}
	}
	
	public List<T> obterAlfabeto(Automato<E, T> automato){
		List<T> alfabeto = new ArrayList<>();
		for (Transicao<T, E> t : automato.getTransicoes()) {
			if(!alfabeto.contains(t.getInfo()) && !t.getInfo().equals(Constante.getElementoVazio())){
				alfabeto.add(t.getInfo());
			}
		}
		return alfabeto;
	}
}
