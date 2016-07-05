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

import org.primefaces.event.FileUploadEvent;

import br.ufpi.automatos.algoritmos.petri.CoverageTree;
import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.petri.NodeInfo;
import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.modelo.vo.PetriNetVO;
import br.ufpi.automatos.util.FileUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Named
@ViewScoped
public class PetriController implements Serializable{

	private static final long serialVersionUID = 4330415809833989926L;

	private PetriNet redeDePetri;

	private List<File> arquivosEntrada;
	
	private Automato<NodeInfo, String> arvore;
	
	private String jsonRedeDePetri;
	
	private String jsonArvore;
	
	private Gson gson;
	
	private boolean simulating;
	
	private CoverageTree coverageTree;
	
	private String gamaString;
	
	private String estadoString;
	
	@PostConstruct
	private void init() {
		this.arquivosEntrada = new ArrayList<File>();
		this.redeDePetri = new PetriNet("PetriNet");
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.simulating = false;
	}
	
	public void simulate(){
		this.simulating = true;
	}
	
	public void stopSimulation(){
		getPetriNetFromJson();
		this.simulating = false;
	}
	
	public boolean isSimulating() {
		return simulating;
	}

	public void setSimulating(boolean simulating) {
		this.simulating = simulating;
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
			this.jsonRedeDePetri = gson.toJson(new PetriNetVO(petri)); 
			this.redeDePetri = petri;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void verificaConservacao(){
		String[] gama = gamaString.split(",");
		int[] y  = new int[gama.length];
		for (int i = 0; i < y.length; i++) {
			y[i] = Integer.parseInt(gama[i]);
			
		}
		if(this.coverageTree.checkConservation(arvore, y)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "A rede é conservativa!"));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "A rede NÂO é conservativa!"));
		}
	}
	
	public void verificaAlcancabilidade(){
		String[] estadoInput = estadoString.split(",");
		int[] estado  = new int[estadoInput.length];
		for (int i = 0; i < estado.length; i++) {
			estado[i] = Integer.parseInt(estadoInput[i]);
		}
		if(this.coverageTree.checkAccessibility(estado, arvore)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "O Estado é alcançavél!"));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "O estado NÃO é alcançavél!"));
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

	public PetriNet getRedeDePetri() {
		return redeDePetri;
	}

	public void setRedeDePetri(PetriNet redeDePetri) {
		this.redeDePetri = redeDePetri;
	}
	
	public String getGamaString() {
		return gamaString;
	}

	public void setGamaString(String gamaString) {
		this.gamaString = gamaString;
	}

	public String getEstadoString() {
		return estadoString;
	}

	public void setEstadoString(String estadoString) {
		this.estadoString = estadoString;
	}

	public List<File> getArquivosEntrada() {
		return arquivosEntrada;
	}

	public void setArquivosEntrada(List<File> arquivosEntrada) {
		this.arquivosEntrada = arquivosEntrada;
	}
	
	
	public void getPetriNetFromJson(){
		this.redeDePetri = new PetriNet("PetriNet");
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
		
		coverageTree = new CoverageTree(redeDePetri);
		this.arvore = coverageTree.coverageTreeBuide();
//		System.out.println(jsonRedeDePetri);
		this.jsonArvore = gson.toJson(this.arvore);
	}
	
}
