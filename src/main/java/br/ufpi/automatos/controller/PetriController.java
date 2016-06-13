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

import br.ufpi.automatos.modelo.petri.PetriNetObject;
import br.ufpi.automatos.util.FileUtil;

@Named
@ViewScoped
public class PetriController implements Serializable{

	private static final long serialVersionUID = 4330415809833989926L;

	private List<Tab> tabs;
	
	private List<PetriNetObject> redesDePetri;

	private List<File> arquivosEntrada;
	
	@PostConstruct
	private void init() {
		this.arquivosEntrada = new ArrayList<File>();
		this.redesDePetri = new ArrayList<PetriNetObject>();
		this.tabs = new ArrayList<Tab>();

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
