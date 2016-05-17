package br.ufpi.automatos.algoritmos.conversor;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.modelo.Transicao;

public class AFN2AFDConversor {
	
	private static final String ELEMENTO_VAZIO = "$";
	
	public AFN2AFDConversor() {
	}
//	
//	@SuppressWarnings("unused")
//	public Automato<InfoEstado, String> converter(Automato<InfoEstado, String> automatoAFN){
//		Automato<InfoEstado, String> automatoAFD = new Automato<>();
//		List<Estado<InfoEstado>> estadosAFD = new ArrayList<>();
//		List<Estado<InfoEstado>> estados = automatoAFN.getEstados();
//		List<Transicao<String, InfoEstado>> transicoesAFN = automatoAFN.getTransicoes();
//		List<String> alfabeto = obterAlfabeto(automatoAFN.getTransicoes());
//		
//		FechoTransitivo ft;
//		List<String> estadosAtingidos; 
//		List<FechoTransitivo> fechos = new ArrayList<>();
//		
//		for (Estado<InfoEstado> estado : estados) {
//			for (String label : alfabeto) {
//				estadosAtingidos = new ArrayList<>();
//				ft = new FechoTransitivo();
//				ft.setLabelEstado(estado.getInfo().getLabel());
//				ft.setLabelAlfabeto(label);
//				for (Transicao<String, InfoEstado> transicao : transicoesAFN) {
//					if(transicao.getInfo().equals(label)){
//						if(estado.getInfo().getLabel().equals(transicao.getOrigem().getInfo().getLabel())){
//							estadosAtingidos.add(transicao.getDestino().getInfo().getLabel());
//						}
//					}
//				}
//				ft.setEstadosAtingidos(estadosAtingidos);
//				fechos.add(ft);
//			}
//		}
//		return null; 
//	}
//	
//	private List<String> obterAlfabeto (List<Transicao<String, InfoEstado>> transicoes){
//		List<String> alfabeto = new ArrayList<>();
//		
//		for (Transicao<String, InfoEstado> t : transicoes) {
//			String label = t.getInfo();
//			if(!label.equals(ELEMENTO_VAZIO) && !existeLabel(alfabeto, label)){
//				alfabeto.add(label);
//			}
//		} 
//		return null;
//	}
//	
//	private boolean existeLabel(List<String> lista, String label){
//		for (String l : lista) {
//			if(label.equals(l)){
//				return true;
//			}
//		}
//		return false;
//	}	
	
	
	@SuppressWarnings("unchecked")
	public <E, T> Automato<E, T> converter(Automato<E, T> automatoAFN){
		
		return null;
		
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
				if(!listaEstados.contains(t.getOrigem())){
					listaEstados.add(t.getOrigem());
				}
			}
		}
	}
}
