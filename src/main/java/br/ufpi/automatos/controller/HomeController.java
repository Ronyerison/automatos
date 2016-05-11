/**
 * 
 */
package br.ufpi.automatos.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.modelo.Transicao;

/**
 * @author rony
 *
 */
@Named
@SessionScoped
public class HomeController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1461117771138572702L;
	
	private Automato<InfoEstado, String> automato;
	
	public HomeController() {
	}
	
	@PostConstruct
	private void init(){
		this.automato = new Automato<InfoEstado, String>();
		Estado<InfoEstado> code = new Estado<InfoEstado>(new InfoEstado(1L, "code", 180, 390));
		code.setInicial(true);
		Estado<InfoEstado> slash = new Estado<InfoEstado>(new InfoEstado(2L, "slash", 340, 220));
		Estado<InfoEstado> star = new Estado<InfoEstado>(new InfoEstado(3L, "star", 600, 400));
		Estado<InfoEstado> line = new Estado<InfoEstado>(new InfoEstado(4L, "line", 190, 100));
		Estado<InfoEstado> block = new Estado<InfoEstado>(new InfoEstado(5L, "block", 560, 140));
		
		Transicao<String, InfoEstado> t1 = new Transicao<String, InfoEstado>("/", code, slash);
		Transicao<String, InfoEstado> t2 = new Transicao<String, InfoEstado>("other", slash, code);
		Transicao<String, InfoEstado> t3 = new Transicao<String, InfoEstado>("/", slash, line);
		Transicao<String, InfoEstado> t4 = new Transicao<String, InfoEstado>("new\n Line", line, code);
		Transicao<String, InfoEstado> t5 = new Transicao<String, InfoEstado>("*", slash, block);
		Transicao<String, InfoEstado> t6 = new Transicao<String, InfoEstado>("*", block, star);
		Transicao<String, InfoEstado> t7 = new Transicao<String, InfoEstado>("/", code, slash);
				
		this.automato.addEstado(code);
		this.automato.addEstado(slash);
		this.automato.addEstado(star);
		this.automato.addEstado(line);
		this.automato.addEstado(block);
		this.automato.addTransicao(t1);
		this.automato.addTransicao(t2);
		this.automato.addTransicao(t3);
		this.automato.addTransicao(t4);
		this.automato.addTransicao(t5);
		this.automato.addTransicao(t6);
		this.automato.addTransicao(t7);
		
	}
	
	public String getNodesJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(automato);
	}

	public Automato<InfoEstado, String> getAutomato() {
		return automato;
	}

	public void setAutomato(Automato<InfoEstado, String> automato) {
		this.automato = automato;
	}

}
