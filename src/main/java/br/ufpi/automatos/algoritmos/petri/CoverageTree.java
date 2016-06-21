package br.ufpi.automatos.algoritmos.petri;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.automatos.modelo.petri.PetriNet;
import br.ufpi.automatos.modelo.petri.tree.Node;


/**
 * @author Vanderson Moura
 *
 */
public class CoverageTree {
	Matrix matrix; 
	
	public CoverageTree() {
		this.matrix = new Matrix();
	}
	
	public Node<int[]> coverageTreeBuide(PetriNet petriNet){
		Node<int[]> nodeInitial;
		int[] stateMatrix = matrix.stateMatrixBuilde(petriNet);
		int[] activeTransitions = matrix.activeTransitionsMatrixBuilde(petriNet);
		List<int[]> activeTransitionsList = activeTransitionsPartition(activeTransitions);
		
		nodeInitial = new Node<int[]>(stateMatrix);
		
		for (int[] activeTrans : activeTransitionsList) {
			
		}
		return null;
	}
	
	private int[] nextState(int[] stateMatrix, int[] activeTransitionsMatrix, int[][] incidenceMatrix){
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
		
		return resultSum;
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
