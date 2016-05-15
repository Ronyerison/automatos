package br.ufpi.automatos.algoritmos;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;

public class AFN2AFDConversor {
	
	private static final String ELEMENTO_VAZIO = "$";
	
	public AFN2AFDConversor() {
	}
	
	@SuppressWarnings({"rawtypes"})
	public Automato<String, String> excluirTrasicoesVazias(Automato<String, String> automato) throws CloneNotSupportedException{
		Automato<String, String> automatoClone = (Automato<String, String>) automato.clone();
		
		for (Transicao<String, String> t : automato.getTransicoes()) {
			if(t.getInfo().equals(ELEMENTO_VAZIO)){
				Estado origem = t.getOrigem();
				Estado destino = t.getDestino();
				automatoClone.excluirTransicao(automatoClone.getTransicoes().get(automato.getTransicoes().indexOf(t)));
				
//				List<Transicao<String, String>> novasTransicoes = new ArrayList<Transicao<String,String>>();
				
				for (Transicao<String, String> t2 : automato.getTransicoes()) {
					if(t2.getOrigem().getInfo().equals(destino.getInfo())){
						if(!t2.getInfo().equals(ELEMENTO_VAZIO)){
							automatoClone.addTransicao(new Transicao<String, String>(t2.getInfo(), automatoClone.getTransicoes().get(automato.getTransicoes().indexOf(t2)).getOrigem(), automatoClone.getTransicoes().get(automato.getTransicoes().indexOf(t2)).getDestino()));
						}
					}
				}
				
				for (Transicao<String, String> t3 : automato.getTransicoes()) {
					if(t3.getDestino().getInfo().equals(origem.getInfo())){
						if(!t3.getInfo().equals(ELEMENTO_VAZIO)){
							automatoClone.addTransicao(new Transicao<String, String>(t3.getInfo(), automatoClone.getTransicoes().get(automato.getTransicoes().indexOf(t3)).getOrigem(), automatoClone.getTransicoes().get(automato.getTransicoes().indexOf(t3)).getDestino()));
						}
					}
				}
			}
		}
		return automatoClone;
	}
}
