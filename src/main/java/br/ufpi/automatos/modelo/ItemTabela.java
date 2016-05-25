package br.ufpi.automatos.modelo;

import java.util.ArrayList;
import java.util.List;

public class ItemTabela {
	private Estado<InfoEstado> e1, e2;
	private boolean marcado;
	private List<ItemTabela> lista;
	
	public ItemTabela(Estado<InfoEstado> e1, Estado<InfoEstado> e2, boolean marcado, List<ItemTabela> lista) {
		super();
		this.e1 = e1;
		this.e2 = e2;
		this.marcado = marcado;
		this.lista = lista;
	}
	
	public ItemTabela(Estado<InfoEstado> e1, Estado<InfoEstado> e2) {
		super();
		this.e1 = e1;
		this.e2 = e2;
		if (e1.isMarcado() && !e2.isMarcado() || !e1.isMarcado() && e2.isMarcado())
			this.marcado = true;
		else
			this.marcado = false;
		this.lista = new ArrayList<ItemTabela>();
	}
	
	public void adicionarNaLista(ItemTabela item) {
		lista.add(item);
	}
	
	public Estado<InfoEstado> getE1() {
		return e1;
	}
	
	public Estado<InfoEstado> getE2() {
		return e2;
	}
	
	public List<ItemTabela> getLista() {
		return this.lista;
	}
	
	public void setMarcado(boolean marcado){
		this.marcado = marcado;
	}
	
	public boolean isMarcado() {
		return this.marcado;
	}
	
	@Override
	public String toString() {
		String representacao = "[" + e1.getInfo().getLabel() + ", " + e2.getInfo().getLabel() + "]:";
		return (isMarcado()) ? representacao + "X" : representacao + "_";
	}
	
}
