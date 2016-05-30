package br.ufpi.automatos.algoritmos;

import java.util.HashMap;
import java.util.Map;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.modelo.ItemTabela;
import br.ufpi.automatos.modelo.Transicao;

public class Minimizacao {
	
	private static Automato<InfoEstado, String> automatoTotal (Automato<InfoEstado, String> automato){
		Automato<InfoEstado, String> total = automato.clone();
		Estado<InfoEstado> estadoTotal = new Estado<InfoEstado>(new InfoEstado("eT"));
		for (int i = 0; i < automato.getEstados().size(); i++) {
			for (String simbolo : automato.getAlfabeto()) {
				boolean existe = false;
				for (Transicao<String, InfoEstado> transicao : automato.getTransicoesByEstado(automato.getEstados().get(i))) {
					if (simbolo.equals(transicao.getInfo())) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					total.addTransicao(new Transicao<String, InfoEstado>(simbolo, automato.getEstados().get(i), estadoTotal));
				}
			}
		}
		if (total.getEstadoByLabel("eT") != null) {
			for (String simbolo : automato.getAlfabeto()) {
				total.addTransicao(new Transicao<String, InfoEstado>(simbolo, estadoTotal, estadoTotal));
			}
		}
		return total;
	}
	
	public static Automato<InfoEstado, String> automatoMinimo (Automato<InfoEstado, String> automato){
		
		Automato<InfoEstado, String> total = automatoTotal(automato);
		
		//Preenche a tabela
		Map<String, Map<String, ItemTabela>> tabela = new HashMap<String, Map<String, ItemTabela>>();
		for (int i = 0; i < total.getEstados().size()-1; i++) {
			tabela.put(total.getEstados().get(i).getInfo().getLabel(), new HashMap<String, ItemTabela>());
			for (int j = i+1; j < total.getEstados().size(); j++) {
				tabela.get(total.getEstados().get(i).getInfo().getLabel())
					.put(total.getEstados().get(j).getInfo().getLabel(),
						new ItemTabela(total.getEstados().get(i), total.getEstados().get(j)));
			}
		}
		
		//Percorre a tabela
		for (String chave : tabela.keySet()) {
			for (ItemTabela itemTabela : tabela.get(chave).values()) {
				
				if (!itemTabela.isMarcado()) {
					for (Transicao<String, InfoEstado> transicao1 : total.getTransicoesByEstado(itemTabela.getE1())) {
						for (Transicao<String, InfoEstado> transicao2 : total.getTransicoesByEstado(itemTabela.getE2())) {
							if (transicao1.getInfo().equals(transicao2.getInfo())) {
								if (transicao1.getDestino() != transicao2.getDestino()) {
									
									ItemTabela item;
									if(tabela.get(transicao1.getDestino().getInfo().getLabel()) == null)
										item = tabela.get(transicao2.getDestino().getInfo().getLabel())
											.get(transicao1.getDestino().getInfo().getLabel());
									else{
										item = tabela.get(transicao1.getDestino().getInfo().getLabel())
											.get(transicao2.getDestino().getInfo().getLabel());
										if (item == null)
											item = tabela.get(transicao2.getDestino().getInfo().getLabel())
												.get(transicao1.getDestino().getInfo().getLabel());
									}
									
									if (item.isMarcado()) {
										marcarRecursivamente(itemTabela);
									} else {
										item.adicionarNaLista(itemTabela);
									}
									
									break;
								}
							}
						}
					}					
				}
				
			}
		}
		
		//Transforma o resultado da tabela em um novo automato
		Automato<InfoEstado, String> minimo = new Automato<InfoEstado, String>();
		for (String chave : tabela.keySet()) {
			for (ItemTabela itemTabela : tabela.get(chave).values()) {
				if (!itemTabela.isMarcado()) {
					if (minimo.getEstadoCompostoByLabel(itemTabela.getE1().getInfo().getLabel()) != null) {
						Estado<InfoEstado> estadoComposto = minimo.getEstadoCompostoByLabel(itemTabela.getE1().getInfo().getLabel());
						estadoComposto.getInfo().setLabel(estadoComposto.getInfo().getLabel() + "," + itemTabela.getE2().getInfo().getLabel());
					}
					else if (minimo.getEstadoCompostoByLabel(itemTabela.getE2().getInfo().getLabel()) != null) {
						Estado<InfoEstado> estadoComposto = minimo.getEstadoCompostoByLabel(itemTabela.getE2().getInfo().getLabel());
						estadoComposto.getInfo().setLabel(estadoComposto.getInfo().getLabel() + "," + itemTabela.getE1().getInfo().getLabel());
					} else {
						Estado<InfoEstado> estadoEquivalente = new Estado<InfoEstado>(
								new InfoEstado(itemTabela.getE1() + "," + itemTabela.getE2()),
								itemTabela.getE1().isInicial() || itemTabela.getE2().isInicial(),
								itemTabela.getE1().isMarcado());
						minimo.addEstado(estadoEquivalente);
					}
				}
			}
		}
		
		for (Transicao<String, InfoEstado> transicao : total.getTransicoes()) {
			Estado<InfoEstado> origem;
			if (minimo.getEstadoCompostoByLabel(transicao.getOrigem().getInfo().getLabel()) != null)
				origem = minimo.getEstadoCompostoByLabel(transicao.getOrigem().getInfo().getLabel());
			else
				origem = transicao.getOrigem(); //TODO trocar por clone quando estiver implementado
			Estado<InfoEstado> destino;
			if (minimo.getEstadoCompostoByLabel(transicao.getDestino().getInfo().getLabel()) != null)
				destino = minimo.getEstadoCompostoByLabel(transicao.getDestino().getInfo().getLabel());
			else
				destino = transicao.getDestino(); //TODO trocar por clone quando estiver implementado
			minimo.addTransicao(new Transicao<String, InfoEstado>(transicao.getInfo(), origem, destino));
		}
		
		return new Algoritmo<InfoEstado, String>().coacessibilidade(minimo);
	}
	
	private static void marcarRecursivamente(ItemTabela item) {
		if (!item.isMarcado()) {
			item.setMarcado(true);
			for (ItemTabela itemLista : item.getLista()) {
				marcarRecursivamente(itemLista);
			}
		}
	}
	
}
