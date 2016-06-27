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

import org.primefaces.event.FileUploadEvent;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;
import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.util.FileUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Named
@SessionScoped
public class PetriController implements Serializable{

	private static final long serialVersionUID = 4330415809833989926L;

	private PetriNet redeDePetri;

	private List<File> arquivosEntrada;
	
	private Automato<String, String> arvore;
	
	private String jsonRedeDePetri;
	
	private String jsonArvore;
	
	private Gson gson;
	
	@PostConstruct
	public void init() {
		this.arquivosEntrada = new ArrayList<File>();
		this.redeDePetri = new PetriNet("PetriNet");
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
		
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.jsonArvore = gson.toJson(this.arvore);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Automato<String, String> getArvore() {
		return arvore;
	}

	public void setArvore(Automato<String, String> arvore) {
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
