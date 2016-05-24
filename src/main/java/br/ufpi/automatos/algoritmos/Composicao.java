package br.ufpi.automatos.algoritmos;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.modelo.Transicao;

public class Composicao {
	
	public static Automato<InfoEstado, String> produto (Automato<InfoEstado, String> automato1, Automato<InfoEstado, String> automato2){
		
		Automato<InfoEstado, String> automatoComposto = new Automato<InfoEstado, String>();
		
		for (int i = 0; i < automato1.getEstados().size(); i++) {
			for (int j = 0; j < automato2.getEstados().size(); j++) {
				
				for (Transicao<String, InfoEstado> transicao1 : automato1.getTransicoesByEstado(automato1.getEstados().get(i))) {
					for (Transicao<String, InfoEstado> transicao2 : automato2.getTransicoesByEstado(automato2.getEstados().get(j))) {
						
						if (transicao1.getInfo().equals(transicao2.getInfo()))
							automatoComposto.addTransicao(transicaoSincrona(
									automatoComposto, transicao1.getInfo(),
									transicao1.getOrigem(), transicao1.getDestino(),
									transicao2.getOrigem(), transicao2.getDestino()));
							
					}
				}
			}
		}
		
		return automatoComposto;
	}
	
	public static Automato<InfoEstado, String> paralela (Automato<InfoEstado, String> automato1, Automato<InfoEstado, String> automato2){
		
		Automato<InfoEstado, String> automatoComposto = new Automato<InfoEstado, String>();
		
		for (int i = 0; i < automato1.getEstados().size(); i++) {
			for (int j = 0; j < automato2.getEstados().size(); j++) {
				
				for (Transicao<String, InfoEstado> transicao1 : automato1.getTransicoesByEstado(automato1.getEstados().get(i))) {
					for (Transicao<String, InfoEstado> transicao2 : automato2.getTransicoesByEstado(automato2.getEstados().get(j))) {
						
						if (transicao1.getInfo().equals(transicao2.getInfo()))
							automatoComposto.addTransicao(transicaoSincrona(
									automatoComposto, transicao1.getInfo(),
									transicao1.getOrigem(), transicao1.getDestino(),
									transicao2.getOrigem(), transicao2.getDestino()));
							
						else if (!automato2.getAlfabeto().contains(transicao1.getInfo()))
							automatoComposto.addTransicao(transicaoSincrona(
									automatoComposto, transicao1.getInfo(),
									transicao1.getOrigem(), transicao1.getDestino(),
									transicao2.getOrigem(), transicao2.getOrigem()));
						
						else if(!automato1.getAlfabeto().contains(transicao2.getInfo()))
							automatoComposto.addTransicao(transicaoSincrona(
									automatoComposto, transicao2.getInfo(),
									transicao1.getOrigem(), transicao1.getOrigem(),
									transicao2.getOrigem(), transicao2.getDestino()));
					
					}
				}
			}
		}
		
		return automatoComposto;
	}
	
	private static Transicao<String, InfoEstado> transicaoSincrona (
			Automato<InfoEstado, String> automato, String label,
			Estado<InfoEstado> t1Origem, Estado<InfoEstado> t1Destino,
			Estado<InfoEstado> t2Origem, Estado<InfoEstado> t2Destino){
		
		boolean isFinalOrigem, isInicialOrigem, isFinalDestino, isInicialDestino;
		if (t1Origem.isInicial() && t2Origem.isInicial())
			isInicialOrigem = true;
		else
			isInicialOrigem = false;
		if (t1Origem.isMarcado() && t2Origem.isMarcado())
			isFinalOrigem = true;
		else
			isFinalOrigem = false;
		if (t1Destino.isInicial() && t2Destino.isInicial())
			isInicialDestino = true;
		else
			isInicialDestino = false;
		if (t1Destino.isMarcado() && t2Destino.isMarcado())
			isFinalDestino = true;
		else
			isFinalDestino = false;
		
		Estado<InfoEstado> origem, destino;
		
		String lblOrigem = t1Origem.getInfo().getLabel() + "," +
							t2Origem.getInfo().getLabel();
		String lblDestino = t1Destino.getInfo().getLabel() + "," +
							t2Destino.getInfo().getLabel();
		
		if(automato.getEstadoByLabel(lblOrigem) == null)
			origem = new Estado<InfoEstado>(
						new InfoEstado(lblOrigem), isInicialOrigem, isFinalOrigem);
		else
			origem = automato.getEstadoByLabel(lblOrigem);
		
		if (automato.getEstadoByLabel(lblDestino) == null)
			destino = new Estado<InfoEstado>(
						new InfoEstado(lblDestino), isInicialDestino, isFinalDestino);
		else
			destino = automato.getEstadoByLabel(lblDestino);
		
		return new Transicao<String, InfoEstado>(label, origem, destino);
	}
}
