package br.ufpi.automatos.algoritmos.conversor;

import java.util.ArrayList;
import java.util.List;

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
		List<T> alfabeto = new ArrayList<>();
		
		if(verificarAutomatoAFN(automatoAFN)){
			fechos = obterFechos(automatoAFN);
			alfabeto = obterAlfabeto(automatoAFN);
			criarEstadosCompostos(fechos, alfabeto);
			String label = new String ("AFD_" + automatoAFN.getLabel());
			return criarAutomatoAFD(fechos, label);
		}else{
			Automato<E, T> automatoResultado = automatoAFN.clone();
			automatoResultado.setLabel("AFD_" + automatoAFN.getLabel());
			return automatoResultado;
		}
	}

	/** MÉTODOS PARA OBTER A LISTA DE FECHOS TRANSITIVOS DO AUTOMATO**/
	
	private List<FechoTransitivo<E, T>> obterFechos(Automato<E, T> automato) {
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
	
	private List<Estado<E>> construirFecho(Automato<E, T> automato, Estado<E> estado, T letra) {
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
	
	private List<T> obterAlfabeto(Automato<E, T> automato){
		List<T> alfabeto = new ArrayList<>();
		for (Transicao<T, E> t : automato.getTransicoes()) {
			if(!alfabeto.contains(t.getInfo()) && !t.getInfo().equals(Constante.getElementoVazio())){
				alfabeto.add(t.getInfo());
			}
		}
		return alfabeto;
	}
	
	/** MÉTODOS PARA ADICIONAR OS POSSÍVEIS NOVOS ESTADOS À LISTA DE FECHOS DO AUTOMATO **/
	
	private void criarEstadosCompostos(List<FechoTransitivo<E, T>> fechos, List<T> alfabeto){
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
	
	@SuppressWarnings("unchecked")
	private Automato<E, T> criarAutomatoAFD(List<FechoTransitivo<E, T>> fechos, String label){
		Automato<E, T> automatoAFD = new Automato<>();
		List<List<Estado<E>>> estadosAtingidos = new ArrayList<>();
		List<Estado<E>> novosEstados = new ArrayList<>();
		List<Transicao<T, E>> novasTransicoes = new ArrayList<>();
		
		Estado<E> novoEstado;
		Transicao<T, E> novaTransicao;
		
		for (FechoTransitivo<E, T> f : fechos) {
			if(f.getFecho().size() > 0){
				if(!existeNaListaFechosUnicos(f.getFecho(), estadosAtingidos)){
					estadosAtingidos.add(new ArrayList<>(f.getFecho()));
				}
			}
		}
		
		for (FechoTransitivo<E, T> f : fechos) 	{
			for (List<Estado<E>> e : estadosAtingidos) {
				if(compararFechos(f.getEstadoUnificado(), e)){
					InfoEstado info = new InfoEstado(f.infoEstadoUnificado());
					novoEstado = (Estado<E>) new Estado<InfoEstado>(info);
					if(verificarEstadoMarcado(e)){
						novoEstado.setMarcado(true);
					}
					if(verificarEstadoInicial(e)){
						novoEstado.setInicial(true);
					}
					if(!existeEstadoLista(novosEstados, novoEstado)){
						novosEstados.add(novoEstado);
					}
				}
			}
		}
		
		List<Estado<E>> l1, l2;
		
		for (FechoTransitivo<E, T> f : fechos) {
			l1 = buscarEstados(f.getEstadoUnificado(), estadosAtingidos);
			l2 = buscarEstados(f.getFecho(), estadosAtingidos);
			if(l1 != null && l2 != null){
				Estado<E> e1 = buscarNovoEstadoPorEstadoUnif(l1, novosEstados);
				Estado<E> e2 = buscarNovoEstadoPorEstadoUnif(l2, novosEstados);
				novaTransicao = new Transicao<T, E>(f.getInfoTransicao(), e1, e2);
				novasTransicoes.add(novaTransicao);
			}
		}
		
		automatoAFD.setLabel(label);
		automatoAFD.setEstados(novosEstados);
		automatoAFD.setTransicoes(novasTransicoes);
		discriminarInicialMarcado(automatoAFD);
 		return automatoAFD;
	}
	
	private boolean existeNaListaFechosUnicos(List<Estado<E>> fecho, List<List<Estado<E>>> fechosUnicos){
		for (List<Estado<E>> f : fechosUnicos) {
			if(compararFechos(fecho, f)){
				return true;
			}
		}
		return false;
	}
	
	private boolean verificarEstadoMarcado(List<Estado<E>> estados){
		for (Estado<E> estado : estados) {
			if(estado.isMarcado()){
				return true;
			}
		}
		return false;
	}
	
	private boolean verificarEstadoInicial(List<Estado<E>> estados){
		if(estados.size() == 1){
			if(estados.get(0).isInicial()){
				return true;
			}
		}
		return false;
	}
	
	private List<Estado<E>> buscarEstados(List<Estado<E>> estadoUnificado, List<List<Estado<E>>> listaEstadosUnificados){
		for (List<Estado<E>> l : listaEstadosUnificados) {
			if(compararFechos(l, estadoUnificado)){
				return l;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Estado<E> buscarNovoEstadoPorEstadoUnif(List<Estado<E>> estadoUnif, List<Estado<E>> novosEstados){
		String[] strs;
		List<Estado<E>> estadosAux;
		
		for (Estado<E> estado : novosEstados) {
			strs =((String)(((InfoEstado)estado.getInfo()).getLabel())).split(",");
			estadosAux = new ArrayList<>();
			
			for (String string : strs) {
				estadosAux.add(new Estado<E>((E)new InfoEstado(string)));
			}
			if(compararFechos(estadosAux, estadoUnif)){
				return estado;
			}
		}
		return null;
	}
	
	private boolean existeEstadoLista(List<Estado<E>> estados, Estado<E> estado){
		for (Estado<E> e : estados) {
			if(((InfoEstado)e.getInfo()).getLabel().equals(((InfoEstado)estado.getInfo()).getLabel())){
				return true;
			}
		}
		return false;
	}
	
	private void discriminarInicialMarcado(Automato<E, T> automato){
		Estado<E> estadoInicial = null;
		List<Estado<E>> estadosMarcados = new ArrayList<>();
		for (Estado<E> e : automato.getEstados()) {
			if(e.isInicial()){
				estadoInicial = e;
			}
			if(e.isMarcado()){
				estadosMarcados.add(e);
			}
		}
		automato.setEstadoInicial(estadoInicial);
		automato.setEstadosMarcados(estadosMarcados);
	}
	
	private boolean verificarAutomatoAFN (Automato<E, T> automato){
		boolean flag = false;
		
		for (Transicao<T, E> t : automato.getTransicoes()) {
			if(t.getInfo().equals(Constante.getElementoVazio())){
				return true; 
			}
		}
		
		String labelTransicao, labelEstadoOrigem, labelEstadoDestino;
		
		for (Transicao<T, E> t : automato.getTransicoes()) {
			labelTransicao = (String)t.getInfo();
			labelEstadoOrigem = ((InfoEstado)t.getOrigem().getInfo()).getLabel(); 
			labelEstadoDestino = ((InfoEstado)t.getDestino().getInfo()).getLabel(); 
			
			for(Transicao<T, E> t2 : automato.getTransicoes()){
				if(((InfoEstado)t.getOrigem().getInfo()).getLabel().equals(((InfoEstado)t.getOrigem().getInfo()).getLabel())){
					if(t2.getInfo().equals(labelTransicao) && labelEstadoOrigem.equals(((InfoEstado)t2.getOrigem().getInfo()).getLabel()) && !labelEstadoDestino.equals(((InfoEstado)t2.getDestino().getInfo()).getLabel())){
						return true;
					}
				}
			}
		}
		
		return flag;
	}
}
