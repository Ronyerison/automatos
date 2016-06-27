package br.ufpi.automatos.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.component.tabview.Tab;
import org.primefaces.event.FileUploadEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;
import br.ufpi.automatos.modelo.petri.NodeInfo;
import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.util.FileUtil;

@Named
@SessionScoped
public class PetriController implements Serializable{

	private static final long serialVersionUID = 4330415809833989926L;

	private List<Tab> tabs;
	
	private PetriNet redeDePetri;

	private List<File> arquivosEntrada;
	
	private Automato<NodeInfo, String> arvore;
	
	private String jsonRedeDePetri;
	
	private String jsonArvore;
	
	private Gson gson;
	
	private boolean simulating;
	
	@PostConstruct
	private void init() {
		this.arquivosEntrada = new ArrayList<File>();
		this.redeDePetri = new PetriNet("PetriNet");
		this.tabs = new ArrayList<Tab>();
		this.arvore = new Automato<NodeInfo, String>();
		Estado<NodeInfo> e1 = new Estado<NodeInfo>(new NodeInfo("[0,1,0]", null), true, false);
		Estado<NodeInfo> e2 = new Estado<NodeInfo>(new NodeInfo("[1,1,1]", "[0,1,0]"), false, false);
		Estado<NodeInfo> e3 = new Estado<NodeInfo>(new NodeInfo("[2,1,0]", "[1,1,1]"), false, false);
		e3.getInfo().setTerminal(true);
		Estado<NodeInfo> e4 = new Estado<NodeInfo>(new NodeInfo("[2,2,0]", "[1,1,1]"), false, false);
		e4.getInfo().setTerminal(true);
		Estado<NodeInfo> e5 = new Estado<NodeInfo>(new NodeInfo("[1,0,0]", "[0,1,0]"), false, false);
		e5.getInfo().setDuplicated(true);
		this.arvore.addTransicao(new Transicao<String, NodeInfo>("t0", e1, e2));
		this.arvore.addTransicao(new Transicao<String, NodeInfo>("t1", e2, e3));
		this.arvore.addTransicao(new Transicao<String, NodeInfo>("t2", e2, e4));
		this.arvore.addTransicao(new Transicao<String, NodeInfo>("t3", e1, e5));
		
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.jsonArvore = gson.toJson(this.arvore);
		this.simulating = false;
	}
	
	public void simulate(){
		this.simulating = true;
	}
	
	public void stopSimulation(){
		this.simulating = false;
	}
	
	public boolean isSimulating() {
		return simulating;
	}

	public void setSimulating(boolean simulating) {
		this.simulating = simulating;
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
			PetriNet petri = FileUtil.file2Petri(arquivo);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			this.jsonRedeDePetri = gson.toJson(petri); 
			this.redeDePetri = petri;
			addTab(petri.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Automato<NodeInfo, String> getArvore() {
		return arvore;
	}

	public void setArvore(Automato<NodeInfo, String> arvore) {
		this.arvore = arvore;
	}

	public String getJsonRedeDePetri() {
		return jsonRedeDePetri;
	}

	public void setJsonRedeDePetri(String jsonRedeDePetri) {
		this.jsonRedeDePetri = jsonRedeDePetri;
	}

	public String getJsonArvore() {
		return jsonArvore;
	}

	public void setJsonArvore(String jsonArvore) {
		this.jsonArvore = jsonArvore;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public PetriNet getRedeDePetri() {
		return redeDePetri;
	}

	public void setRedeDePetri(PetriNet redeDePetri) {
		this.redeDePetri = redeDePetri;
	}

	public List<File> getArquivosEntrada() {
		return arquivosEntrada;
	}

	public void setArquivosEntrada(List<File> arquivosEntrada) {
		this.arquivosEntrada = arquivosEntrada;
	}
	
	
	public void teste(){
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(jsonRedeDePetri).getAsJsonObject();
		JsonArray elements = object.get("elements").getAsJsonArray();
		
		for (JsonElement jsonElement : elements) {
			String id = jsonElement.getAsJsonObject().get("id").getAsString();
			String type = jsonElement.getAsJsonObject().get("type").getAsString();
			if(type.toLowerCase().contains("place")){
				String tokens = jsonElement.getAsJsonObject().get("tokens").getAsString();
				this.redeDePetri.place(id, Integer.parseInt(tokens));
			}else{
				redeDePetri.transition(id);
			}
		}
		
		JsonArray links = object.get("links").getAsJsonArray();
		for (JsonElement jsonElement : links) {
			String idOrigin = jsonElement.getAsJsonObject().get("source").getAsJsonObject().get("id").getAsString();
			String idDestiny = jsonElement.getAsJsonObject().get("target").getAsJsonObject().get("id").getAsString();
			String peso = jsonElement.getAsJsonObject().get("labels").getAsJsonArray().get(0).getAsJsonObject().get("attrs").getAsJsonObject().get("text").getAsJsonObject().get("text").getAsString();;
			if(redeDePetri.getPlaces().containsKey(idOrigin) && redeDePetri.getTransitions().containsKey(idDestiny)){
				redeDePetri.arc(peso, redeDePetri.getPlaces().get(idOrigin), redeDePetri.getTransitions().get(idDestiny));
			}else if(redeDePetri.getTransitions().containsKey(idOrigin) && redeDePetri.getPlaces().containsKey(idDestiny)){
				redeDePetri.arc(peso, redeDePetri.getTransitions().get(idOrigin), redeDePetri.getPlaces().get(idDestiny));
			}
		}
			
		System.out.println(jsonRedeDePetri);
	}
	
}
