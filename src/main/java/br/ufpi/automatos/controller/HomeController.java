/**
 * 
 */
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
import br.ufpi.automatos.modelo.InfoEstado;
import br.ufpi.automatos.util.FileUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author rony
 *
 */
@Named
@ViewScoped
public class HomeController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1461117771138572702L;
	
	private List<Automato<InfoEstado, String>> automatos;
	
	private List<File> arquivosEntrada;
	
	private List<Tab> tabs;
	
	public HomeController() {
	}
	
	@PostConstruct
	private void init(){
		this.arquivosEntrada = new ArrayList<File>();
		this.automatos = new ArrayList<Automato<InfoEstado, String>>();
		this.tabs = new ArrayList<Tab>();

	}
	
	public void addTab(String titulo){
		Tab tab = new Tab();
		tab.setTitle(titulo);
		this.tabs.add(tab);
	}
	
	public void uploadArquivo(FileUploadEvent event){
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        addTab(event.getFile().getFileName());
        try {
        	File arquivo = FileUtil.uploadedFileToFile(event.getFile());
			this.arquivosEntrada.add(arquivo);
			this.automatos.add(FileUtil.File2Automato(arquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getNodesJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(automatos);
	}

	public List<Automato<InfoEstado, String>> getAutomatos() {
		return automatos;
	}

	public void setAutomatos(List<Automato<InfoEstado, String>> automatos) {
		this.automatos = automatos;
	}

	public List<File> getArquivosEntrada() {
		return arquivosEntrada;
	}

	public void setArquivosEntrada(List<File> arquivosEntrada) {
		this.arquivosEntrada = arquivosEntrada;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

}
