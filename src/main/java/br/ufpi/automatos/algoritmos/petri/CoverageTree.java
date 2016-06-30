package br.ufpi.automatos.algoritmos.petri;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.Transicao;
import br.ufpi.automatos.modelo.petri.NodeInfo;
import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.modelo.petri.Place;
import br.ufpi.automatos.modelo.petri.Transition;


/**
 * @author Vanderson Moura
 *
 */
public class CoverageTree {
	private PetriNet petriNet;
	private Matrix matrix;
	
	public CoverageTree(PetriNet petriNet) {	
		this.matrix = new Matrix();
		this.petriNet = petriNet;
	}
	
	public Automato<NodeInfo, String> coverageTreeBuide(){
		int[] stateMatrix = matrix.stateMatrixBuilde(this.petriNet);
		int[] activeTransitions = matrix.activeTransitionsMatrixBuilde(this.petriNet);
		int[][] incidenceMatrix = matrix.incidenceMatrixBuilde(petriNet);
		
		NodeInfo info = new NodeInfo(stateMatrix);
		Estado<NodeInfo> initialNode;
		Estado<NodeInfo> actualNode;
		List<Estado<NodeInfo>> front = new ArrayList<>();	
		List<Estado<NodeInfo>> visitedList = new ArrayList<>();	
		List<int[]> activeTransitionsList = activeTransitionsPartition(activeTransitions);
		
		initialNode = new Estado<NodeInfo>(info);
		visitedList.add(initialNode);
		actualNode = initialNode.clone();
		actualNode.setInicial(true);
		
		front.add(actualNode);
		
		List<Estado<NodeInfo>> childs;
		Automato<NodeInfo, String> automato = new Automato<>();
		
		PetriNet petriNetAux = new PetriNet(this.petriNet);
		int[] activeTransitionsAux = activeTransitions;
		List<int[]> activeTransitionsListAux = activeTransitionsPartition(activeTransitionsAux);
		
		while(front.size() > 0){// enquanto fronteira não for vazia
			childs = generateChilds(actualNode, activeTransitionsListAux, incidenceMatrix);
			if(childs.size() > 0){
				for (Estado<NodeInfo> child : childs) {
					child.getInfo().setParentLabel(actualNode.getInfo().getLabel());
					automato.addTransicao(new Transicao<String, NodeInfo>(transitionLabelByIndex(child.getInfo().getGeneratorTransitionMatrix()), actualNode, child));
					checkDominance(child, automato);
					if(visitedList.contains(child)){
						child.getInfo().setDuplicated(true);
					}else{
						front.add(child);
					}
				}
			}else{
				actualNode.getInfo().setTerminal(true);
			}
			front.remove(0);
			actualNode = front.size() > 0 ? front.get(0).clone() : actualNode;
			visitedList.add(actualNode);
			updateStateRdP(petriNetAux, actualNode.getInfo().getStateMatrix());
			activeTransitionsAux = matrix.activeTransitionsMatrixBuilde(petriNetAux);
			activeTransitionsListAux = activeTransitionsPartition(activeTransitionsAux);
		}

		return automato;
	}
	
	private void checkDominance(Estado<NodeInfo> node, Automato<NodeInfo, String> automato){
		Estado<NodeInfo> nodeParent = null; 
		boolean dominate = true;
		
		if(node.getInfo().getParentLabel() != null){
			nodeParent = automato.getEstadoNoDuplicateByLabel(node.getInfo().getParentLabel());
		}
		
		while(nodeParent != null){
			for (int i = 0; i < nodeParent.getInfo().getStateMatrix().length; i++) {
				if (nodeParent.getInfo().getW()[i]) {
					node.getInfo().getW()[i] = true;
				}
				if(nodeParent.getInfo().getStateMatrix()[i] > node.getInfo().getStateMatrix()[i]){
					dominate = false;
//					break;
				}
			}
			if(dominate){
				if(!compare(node, nodeParent) && nodeParent.getInfo().getParentLabel() != null){
					nodeParent = automato.getEstadoNoDuplicateByLabel(nodeParent.getInfo().getParentLabel());
				}else{
					nodeParent = null;
				}
			} else if (nodeParent.getInfo().getParentLabel() != null){
				nodeParent = automato.getEstadoNoDuplicateByLabel(nodeParent.getInfo().getParentLabel());
				dominate = true;
			} else
				nodeParent = null;
		}
	}
	
	private boolean compare(Estado<NodeInfo> node, Estado<NodeInfo> nodeParent){
		boolean dominate = false;
		for (int i = 0; i < node.getInfo().getStateMatrix().length; i++) {
			if(node.getInfo().getStateMatrix()[i] > nodeParent.getInfo().getStateMatrix()[i]){
				node.getInfo().getW()[i] = true;
				dominate = true;
			}
		}
		return dominate;
	}
	
	private void updateStateRdP(PetriNet petriNet, int[] state){
		int i = 0;
		for (Place place : petriNet.getPlaces().values()) {
			place.setTokens(state[i]);
			i++;
		}
	}
	
	
	public List<Estado<NodeInfo>> generateChilds(Estado<NodeInfo> node, List<int[]> activeTransitions, int[][] incidenceMatrix){
		List<Estado<NodeInfo>> childs = new ArrayList<>();
		Estado<NodeInfo> child;
		
		for (int[] t : activeTransitions) {
			child = nextState(node.getInfo().getStateMatrix(), node.getInfo().getW(), t, incidenceMatrix);
			child.getInfo().setGeneratorTransitionMatrix(t);
			childs.add(child);
		}
		return childs;
	}
	
	private Estado<NodeInfo> nextState(int[] stateMatrix, boolean[] w, int[] activeTransitionsMatrix, int[][] incidenceMatrix){
		int numLines = incidenceMatrix.length;
		int numColumns = incidenceMatrix[0].length;
		int sum = 0;
		int a = 0;
		int[] resultMult = new int[numColumns];
		int[] resultSum = new int[numColumns];
//		boolean[] resultW = new boolean[numColumns];
		
		/** Multiplicando a matriz de transições ativas pela matriz de incidência **/
		for (int j = 0; j < numColumns; j++) {
			for (int i = 0; i < numLines; i++) {
				sum += activeTransitionsMatrix[i] * incidenceMatrix[i][j];
			}
			resultMult[a] = sum;
			sum=0;
			a++;
		}
		
		/** Somando o resultado da multiplicação anterior pelo estado atual da rede (stateMatrix)**/
		for (int i = 0; i < resultMult.length; i++) {
//			if(w[i]){
//				resultW[i] = true;
//			}
			resultSum[i] = resultMult[i] + stateMatrix[i]; 
		}
		
		return new Estado<NodeInfo>(new NodeInfo(resultSum));
	}
	
	/** Retona uma lista de vetores, onde cada vetor representa uma transição ativa**/ 
	private List<int[]> activeTransitionsPartition (int[] transitionsMatrix){
		List<int[]> activeTransitionsList = new ArrayList<int[]>();
		int[] transitionsMatrixAux = new int[transitionsMatrix.length];
		
		for (int i = 0; i < transitionsMatrix.length; i++) {
			if(transitionsMatrix[i] == 1){
				for (int j = 0; j < transitionsMatrix.length; j++) {
					transitionsMatrixAux[j] = (j == i ? 1 : 0);   
				}
				activeTransitionsList.add(transitionsMatrixAux);
				transitionsMatrixAux = new int[transitionsMatrix.length];
			}
		}
		return activeTransitionsList;
	}
	
	/** Retorna o nome da transição pela seu indice correspondente na matrix que representa a transição ativa**/ 
	private String transitionLabelByIndex(int[] matrixActiviteTransition){
		int i = 0;
		int index = 0;
		
		for (int j = 0; j < matrixActiviteTransition.length; j++) {
			if(matrixActiviteTransition[j] == 1){
				index = j;
				break;
			}
		}
		
		for (Transition t : this.petriNet.getTransitions().values()) {
			if(i == index){
				return t.getName();
			}
			i++;
		}
		return "";
	}
}
