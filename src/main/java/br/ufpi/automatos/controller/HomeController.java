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
import br.ufpi.automatos.algoritmos.Composicao;
import br.ufpi.automatos.algoritmos.Minimizacao;
import br.ufpi.automatos.algoritmos.conversor.AFN2AFDConversor;
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

	private String autSelTrim;

	private String autSelAFD;
	
	private String autSelMin;
	
	private String[] autSelProdutos;
	
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
		try {
			File arquivo = FileUtil.uploadedFileToFile(event.getFile());
			this.arquivosEntrada.add(arquivo);
			Automato<InfoEstado,String> automato = FileUtil.File2Automato(arquivo);
			this.automatos.add(automato);
			addTab(automato.getLabel());
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
		if(autSelTrim != null){
			Automato<InfoEstado, String> trim = algoritmo.trim(getAutomatoByLabel(autSelTrim));
			if(!this.automatos.contains(trim)){
				this.automatos.add(trim);
				addTab(trim.getLabel());
			}
		}
	}
	
	public void produto(){
		Automato<InfoEstado, String> produto ;
		if(autSelProdutos.length > 2){
			produto = getAutomatoByLabel(autSelProdutos[0]).clone();
			for (int i = 0; i < autSelProdutos.length-1; i++) {
				produto = Composicao.produto(produto, getAutomatoByLabel(autSelProdutos[i+1]).clone());
			}
			if(!this.automatos.contains(produto)){
				this.automatos.add(produto);
				addTab(produto.getLabel());
			}
		}else if(autSelProdutos.length == 2){
			produto = Composicao.produto(getAutomatoByLabel(autSelProdutos[0]).clone(), getAutomatoByLabel(autSelProdutos[1]).clone());
			if(!this.automatos.contains(produto)){
				this.automatos.add(produto);
				addTab(produto.getLabel());
			}
		}else{
			
		}
	}
	
	public void paralela(){
		Automato<InfoEstado, String> paralela ;
		if(autSelProdutos.length > 2){
			paralela = getAutomatoByLabel(autSelProdutos[0]).clone();
			for (int i = 0; i < autSelProdutos.length-1; i++) {
				paralela = Composicao.paralela(paralela, getAutomatoByLabel(autSelProdutos[i+1]).clone());
			}
			if(!this.automatos.contains(paralela)){
				this.automatos.add(paralela);
				addTab(paralela.getLabel());
			}
		}else if(autSelProdutos.length == 2){
			paralela = Composicao.paralela(getAutomatoByLabel(autSelProdutos[0]).clone(), getAutomatoByLabel(autSelProdutos[1]).clone());
			if(!this.automatos.contains(paralela)){
				this.automatos.add(paralela);
				addTab(paralela.getLabel());
			}
		}else{
			
		}
	}
	
	public void afn2afd(){
		AFN2AFDConversor<InfoEstado, String> conversor = new AFN2AFDConversor<InfoEstado, String>();
		if(autSelAFD != null){
			Automato<InfoEstado, String> afd = conversor.converter(getAutomatoByLabel(autSelAFD));
			if(!this.automatos.contains(afd)){
				this.automatos.add(afd);
				addTab(afd.getLabel());
			}
		}
	}
	
	public void minimizacao(){
		if(autSelMin != null){
			Automato<InfoEstado, String> min = Minimizacao.automatoMinimo(getAutomatoByLabel(autSelMin));
			if(!this.automatos.contains(min)){
				this.automatos.add(min);
				addTab(min.getLabel());
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

	public String getAutSelTrim() {
		return autSelTrim;
	}

	public void setAutSelTrim(String autSelTrim) {
		this.autSelTrim = autSelTrim;
	}

	public String getAutSelAFD() {
		return autSelAFD;
	}

	public void setAutSelAFD(String autSelAFD) {
		this.autSelAFD = autSelAFD;
	}

	public String getAutSelMin() {
		return autSelMin;
	}

	public void setAutSelMin(String autSelMin) {
		this.autSelMin = autSelMin;
	}

	public String[] getAutSelProdutos() {
		return autSelProdutos;
	}

	public void setAutSelProdutos(String[] autSelProdutos) {
		this.autSelProdutos = autSelProdutos;
	}

	
	
}
