package br.ufpi.automatos.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.component.tabview.Tab;
import org.primefaces.event.FileUploadEvent;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;
import br.ufpi.automatos.modelo.petri.PetriNetObject;
import br.ufpi.automatos.util.FileUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Named
@ViewScoped
public class PetriController implements Serializable{

	private static final long serialVersionUID = 4330415809833989926L;

	private List<Tab> tabs;
	
	private List<PetriNetObject> redesDePetri;

	private List<File> arquivosEntrada;
	
	private Automato<String, String> arvore;
	
	@PostConstruct
	private void init() {
		this.arquivosEntrada = new ArrayList<File>();
		this.redesDePetri = new ArrayList<PetriNetObject>();
		this.tabs = new ArrayList<Tab>();
		this.arvore = new Automato<String, String>();
		Estado<String> e1 = new Estado<String>("[0,1,0]", true, false);
		Estado<String> e2 = new Estado<String>("[1,1,1]", false, false);
		Estado<String> e3 = new Estado<String>("[2,1,0]", false, false);
		Estado<String> e4 = new Estado<String>("[2,2,0]", false, false);
		Estado<String> e5 = new Estado<String>("[1,0,0]", false, false);
		this.arvore.addTransicao(new Transicao<String, String>("t0", e1, e2));
		this.arvore.addTransicao(new Transicao<String, String>("t1", e2, e3));
		this.arvore.addTransicao(new Transicao<String, String>("t2", e2, e4));
		this.arvore.addTransicao(new Transicao<String, String>("t3", e1, e5));
		
	}
	
	public void addTab(String titulo) {
		Tab tab = new Tab();
		tab.setTitle(titulo);
		this.tabs.add(tab);
	}
	
	public void uploadArquivo(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		try {
			File arquivo = FileUtil.uploadedFileToFile(event.getFile());
			this.arquivosEntrada.add(arquivo);
			PetriNetObject petri = FileUtil.file2Petri(arquivo);
			this.redesDePetri.add(petri);
			addTab(petri.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPetriJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this.redesDePetri);
	}
	
	public String getArvoreJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this.arvore);
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public List<PetriNetObject> getRedesDePetri() {
		return redesDePetri;
	}

	public void setRedesDePetri(List<PetriNetObject> redesDePetri) {
		this.redesDePetri = redesDePetri;
	}

	public List<File> getArquivosEntrada() {
		return arquivosEntrada;
	}

	public void setArquivosEntrada(List<File> arquivosEntrada) {
		this.arquivosEntrada = arquivosEntrada;
	}
	
}
