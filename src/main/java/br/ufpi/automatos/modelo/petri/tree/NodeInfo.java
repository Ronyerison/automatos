package br.ufpi.automatos.modelo.petri.tree;

import java.util.Arrays;

public class NodeInfo {
	private int[] stateMatrix;
	private boolean duplicated;
	private boolean terminal;
	
	public NodeInfo() {
	}
	
	public NodeInfo(String label){
		String[] tokens = label.substring(label.indexOf("[")+1, label.indexOf("]")).split(",");
		stateMatrix = new int[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			stateMatrix[i] = Integer.parseInt(tokens[i].trim());
		}
	}
	
	public NodeInfo(int[] stateMatrix) {
		super();
		this.stateMatrix = stateMatrix;
	}
	
	public int[] getStateMatrix() {
		return stateMatrix;
	}
	
	public boolean isDuplicated() {
		return duplicated;
	}
	
	public boolean isTerminal() {
		return terminal;
	}
	
	public void setStateMatrix(int[] stateMatrix) {
		this.stateMatrix = stateMatrix;
	}
	
	public void setDuplicated(boolean duplicated) {
		this.duplicated = duplicated;
	}
	
	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}
	
	public String getLabel(){
		return Arrays.toString(stateMatrix);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(stateMatrix);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeInfo other = (NodeInfo) obj;
		if (!Arrays.equals(stateMatrix, other.stateMatrix))
			return false;
		return true;
	}
	
	
	
}
