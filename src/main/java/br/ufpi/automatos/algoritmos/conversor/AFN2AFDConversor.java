package br.ufpi.automatos.algoritmos.conversor;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.facelets.FaceletException;
import javax.persistence.metamodel.ListAttribute;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.modelo.Transicao;
import br.ufpi.automatos.util.Constante;

public class AFN2AFDConversor<E, T> {
	
	public AFN2AFDConversor() {
	}
	
	public Automato<E, T> converter(Automato<E, T> automatoAFN){
		List<FechoTransitivo<E, T>> fechos = new ArrayList<>();
		fechos = obterFechos(automatoAFN);
//		List<T> alfabeto = obterAlfabeto(automatoAFN);
		
		return null;
	}

	/** MÉTODOS PARA OBTER A LISTA DE FECHOS TRANSITIVOS DO AUTOMATO**/
	
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
	
	/** MÉTODOS PARA ADICIONAR OS POSSÍVEIS NOVOS ESTADOS À LISTA DE FECHOS DO AUTOMATO **/
	
	public void criarEstadosCompostos(List<FechoTransitivo<E, T>> fechos, List<T> alfabeto){
		List<List<Estado<E>>> listaFechosCompostos = new ArrayList<>();
		List<List<Estado<E>>> listaFechosCompostosAux = new ArrayList<>();
		int tamFecho = 0;
		
		while(tamFecho != fechos.size()){
			tamFecho = fechos.size();
			for (FechoTransitivo<E, T> f : fechos) {
				if(f.getFecho().size() > 1){		
					if(!existeFecho(listaFechosCompostos, f.getFecho())){
						listaFechosCompostosAux.add(f.getFecho());
					}
				}
			}
			adicionaFechos(fechos, listaFechosCompostosAux, alfabeto);
			listaFechosCompostos.addAll(new ArrayList<List<Estado<E>>>(listaFechosCompostosAux));
			listaFechosCompostosAux = new ArrayList<>();
		}
	}
	
	private void adicionaFechos(List<FechoTransitivo<E, T>> fechos, List<List<Estado<E>>> novosEstados, List<T> alfabeto){
		List<FechoTransitivo<E, T>> fechosAux = new ArrayList<>();
		List<FechoTransitivo<E, T>> fechosAux2 = new ArrayList<>(fechos);
		FechoTransitivo<E, T> fecho = new FechoTransitivo<>();
				
		for (List<Estado<E>> estadoUni : novosEstados) {
			fecho = new FechoTransitivo<>();
			fecho.setEstadoUnificado(estadoUni);
			fechosAux.add(fecho);
		}	
		
		List<FechoTransitivo<E, T>> fechosAux3 = new ArrayList<>();
		for (FechoTransitivo<E, T> fechoTransitivo : fechosAux) {
			for (T letra : alfabeto) {
				fecho = new FechoTransitivo<>();
				fecho.setEstadoUnificado(new ArrayList<>(fechoTransitivo.getEstadoUnificado()));
				fecho.setInfoTransicao(letra);
				fechosAux3.add(fecho);
			}
		}
		
		for (FechoTransitivo<E, T> f2 : fechosAux3) {
			for (Estado<E> ef2 : f2.getEstadoUnificado()) {
				for (FechoTransitivo<E, T> f : fechosAux2) { //Lista de fechos aulixar para percorrer todos os fechos da lista original, pois será necessário modificá-la. 
					if(f.getEstadoUnificado().size() == 1){
						if(((InfoEstado)ef2.getInfo()).getLabel().equals(((InfoEstado)f.getEstadoUnificado().get(0).getInfo()).getLabel())){
							if(f.getInfoTransicao().equals(f2.getInfoTransicao())){
								if(f2.getFecho() == null){
									f2.setFecho(new ArrayList<>(f.getFecho()));
								}else{
									f2.setFechoDiff(new ArrayList<>(f.getFecho()));	
								}
							}
						}
					}
				}
			}
		}
		fechos.addAll(fechosAux3);
	}
	
	private boolean existeFecho(List<List<Estado<E>>> fechos, List<Estado<E>> fecho){
		for (List<Estado<E>> f : fechos) {
			if(compararFechos(f, fecho)){
				return true;
			}
		}
		return false;
	}
	
	private boolean compararFechos(List<Estado<E>> fecho1, List<Estado<E>> fecho2){
		List<Estado<E>> estados = new ArrayList<>();
		if(fecho1.size() == fecho2.size()){
			for (Estado<E> eFecho1 : fecho1) {
				for (Estado<E> eFecho2 : fecho2) {
					if(((InfoEstado)eFecho1.getInfo()).getLabel().equals(((InfoEstado)eFecho2.getInfo()).getLabel())){
						estados.add(eFecho1);
					}
				}
			}
			return estados.size() == fecho1.size() ? true : false;
		}
		return false;
	}
	
	/** MÉTODOS PARA CRIAR O AUTOMATO AFD A PARTIR DA LISTA FINAL DE FECHOS TRANSITIVOS **/
	
	public Automato<E, T> criarAutomatoAFD(List<FechoTransitivo<E, T>> fechos){
		List<Estado<E>> estados = new ArrayList<>();
		List<Transicao<T, E>> transicoes = new ArrayList<>();
		List<FechoTransitivo<E, T>> fechosAux = new ArrayList<>(fechos);
		
		for (FechoTransitivo<E, T> f1 : fechos) {
			for (FechoTransitivo<E, T> f2 : fechosAux) {
				if(compararFechos(f1.getEstadoUnificado(), f2.getFecho())){
					
				}
			}
		}
		
		
 		return null;
	}
	
	
}
