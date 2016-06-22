package br.ufpi.automatos.algoritmos.petri;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.Automato;
import br.ufpi.automatos.modelo.Estado;
import br.ufpi.automatos.modelo.petri.NodeInfo;
import br.ufpi.automatos.modelo.petri.PetriNet;


/**
 * @author Vanderson Moura
 *
 */
public class CoverageTree {
	Matrix matrix; 
	
	public CoverageTree() {
		this.matrix = new Matrix();
	}
	
	public Automato<NodeInfo, String> coverageTreeBuide(PetriNet petriNet){
		int[] stateMatrix = matrix.stateMatrixBuilde(petriNet);
		int[] activeTransitions = matrix.activeTransitionsMatrixBuilde(petriNet);
		int[][] incidenceMatrix = matrix.incidenceMatrixBuilde(petriNet);
		
		NodeInfo info = new NodeInfo(stateMatrix);
		Estado<NodeInfo> initialNode;
		List<Estado<NodeInfo>> front = new ArrayList<>();	
		List<Estado<NodeInfo>> visitedList = new ArrayList<>();	
		List<int[]> activeTransitionsList = activeTransitionsPartition(activeTransitions);
		
		initialNode = new Estado<NodeInfo>(info);
		visitedList.add(initialNode);
		
		while(true){// enquanto fronteira não for vazia
			for (Estado<NodeInfo> child : generateChilds(initialNode, activeTransitionsList, incidenceMatrix)) {
				
			}
		}
		
		
	}
	
	public List<Estado<NodeInfo>> generateChilds(Estado<NodeInfo> node, List<int[]> activeTransitions, int[][] incidenceMatrix){
		List<Estado<NodeInfo>> childs = new ArrayList<>();
		Estado<NodeInfo> child;
		
		for (int[] t : activeTransitions) {
			child = nextState(node.getInfo().getStateMatrix(), t, incidenceMatrix);
			childs.add(child);
		}
		return childs;
	}
	
	private Estado<NodeInfo> nextState(int[] stateMatrix, int[] activeTransitionsMatrix, int[][] incidenceMatrix){
		int numLines = incidenceMatrix.length;
		int numColumns = incidenceMatrix[0].length;
		int sum = 0;
		int a = 0;
		int[] resultMult = new int[numColumns];
		int[] resultSum = new int[numColumns];
		
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
			}
		}
		return activeTransitionsList;
	}
}
