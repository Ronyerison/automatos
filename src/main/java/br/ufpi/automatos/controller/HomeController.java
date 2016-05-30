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
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import br.ufpi.automatos.algoritmos.Algoritmo;
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

	private int indexActiveTab;

	private String automatoSelecionado;

	public HomeController() {
	}

	@PostConstruct
	private void init() {
		this.arquivosEntrada = new ArrayList<File>();
		this.automatos = new ArrayList<Automato<InfoEstado, String>>();
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
		addTab(event.getFile().getFileName());
		try {
			File arquivo = FileUtil.uploadedFileToFile(event.getFile());
			this.arquivosEntrada.add(arquivo);
			this.automatos.add(FileUtil.File2Automato(arquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTabChange(TabChangeEvent event) {
		TabView tabView = (TabView) event.getComponent();
		indexActiveTab = tabView.getActiveIndex();
		System.out.println("Tab " + indexActiveTab);
	}

	public void acessibilidade() {
		Algoritmo<InfoEstado, String> algoritmo = new Algoritmo<InfoEstado, String>();
		this.automatos.add(algoritmo.acessibilidade(this.automatos
				.get(indexActiveTab)));
		addTab("Acessibilidade Automato " + indexActiveTab);
	}

	public void trim() {
		Algoritmo<InfoEstado, String> algoritmo = new Algoritmo<InfoEstado, String>();
		if(automatoSelecionado != null){
			Automato<InfoEstado, String> trim = algoritmo.trim(getAutomatoByLabel(automatoSelecionado));
			if(!this.automatos.contains(trim)){
				this.automatos.add(trim);
				addTab("TRIM Automato " + automatoSelecionado);
			}
		}
	}
	
	private Automato<InfoEstado, String> getAutomatoByLabel(String label){
		for (Automato<InfoEstado, String> a : automatos) {
			if(a.getLabel().equalsIgnoreCase(label)){
				return a;
			}
		}
		return null;
	}

	public String getNodesJson() {
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

	public String getAutomatoSelecionado() {
		return automatoSelecionado;
	}

	public void setAutomatoSelecionado(String automatoSelecionado) {
		this.automatoSelecionado = automatoSelecionado;
	}

}
