package br.ufpi.automatos.algoritmos.conversor;

import java.util.List;

import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.InfoEstado;

public class FechoTransitivo<E, T> {
	private List<Estado<E>> estadoUnificado;
	private T infoTransicao;
	private List<Estado<E>> fecho;
	
	public T getInfoTransicao() {
		return infoTransicao;
	}

	public List<Estado<E>> getEstadoUnificado() {
		return estadoUnificado;
	}

	public void setEstadoUnificado(List<Estado<E>> estadoUnificado) {
		this.estadoUnificado = estadoUnificado;
	}

	public List<Estado<E>> getFecho() {
		return fecho;
	}
	
	public void setInfoTransicao(T infoTransicao) {
		this.infoTransicao = infoTransicao;
	}
	
	public void setFecho(List<Estado<E>> fecho) {
		this.fecho = fecho;
	}
	
	public String infoEstadoUnificado(){
		StringBuffer info = new StringBuffer("");
		for (int i = 0; i < estadoUnificado.size(); i++) {
			if(i < estadoUnificado.size()-1){
				info.append(estadoUnificado.get(i).getInfo().toString().concat(","));
			}else{
				info.append(((InfoEstado)estadoUnificado.get(i).getInfo()).getLabel());
			}
		}
		return info.toString();
	}
}
