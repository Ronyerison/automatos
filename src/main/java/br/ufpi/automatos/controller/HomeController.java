/**
 * 
 */
package br.ufpi.automatos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufpi.automatos.modelo.Data;
import br.ufpi.automatos.modelo.Node;

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
	
	private List<Node> nodes;
	
	public HomeController() {
	}
	
	@PostConstruct
	private void init(){
		this.nodes = new ArrayList<Node>();
		this.nodes.add(new Node(new Data("code", 180, 390)));
		this.nodes.add(new Node(new Data("slash", 340, 220)));
		this.nodes.add(new Node(new Data("star", 600, 400)));
		this.nodes.add(new Node(new Data("line", 190, 100)));
		this.nodes.add(new Node(new Data("block", 560, 140)));
	}
	
	public String getNodesJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(nodes);
	}

	public List<Node> getNodes() {
		return nodes;
	}
	
	
}
