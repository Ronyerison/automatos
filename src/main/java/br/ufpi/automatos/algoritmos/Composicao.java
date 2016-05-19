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
						if (transicao1.getInfo().equals(transicao2.getInfo())) {
							
							boolean isFinalOrigem, isInicialOrigem, isFinalDestino, isInicialDestino;
							if (transicao1.getOrigem().isInicial() && transicao2.getOrigem().isInicial())
								isInicialOrigem = true;
							else
								isInicialOrigem = false;
							if (transicao1.getOrigem().isMarcado() && transicao2.getOrigem().isMarcado())
								isFinalOrigem = true;
							else
								isFinalOrigem = false;
							if (transicao1.getDestino().isInicial() && transicao2.getDestino().isInicial())
								isInicialDestino = true;
							else
								isInicialDestino = false;
							if (transicao1.getDestino().isMarcado() && transicao2.getDestino().isMarcado())
								isFinalDestino = true;
							else
								isFinalDestino = false;
							
							Estado<InfoEstado> origem, destino;
							
							String lblOrigem = transicao1.getOrigem().getInfo().getLabel() + "," +
												transicao2.getOrigem().getInfo().getLabel();
							String lblDestino = transicao1.getDestino().getInfo().getLabel() + "," +
												transicao2.getDestino().getInfo().getLabel();
							
							if(automatoComposto.getEstadoByLabel(lblOrigem) == null)
								origem = new Estado<InfoEstado>(
											new InfoEstado(lblOrigem), isInicialOrigem, isFinalOrigem);
							else
								origem = automatoComposto.getEstadoByLabel(lblOrigem);
							
							if (automatoComposto.getEstadoByLabel(lblDestino) == null)
								destino = new Estado<InfoEstado>(
											new InfoEstado(lblDestino), isInicialDestino, isFinalDestino);
							else
								destino = automatoComposto.getEstadoByLabel(lblDestino);
							
							automatoComposto.addTransicao(new Transicao<String, InfoEstado>(
									transicao1.getInfo(), origem, destino));
							
						}
					}
				}
			}
		}
		
		return automatoComposto;
	}
}
