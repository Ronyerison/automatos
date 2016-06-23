package br.ufpi.automatos.modelo.petri;

import java.util.Arrays;

public class NodeInfo {
	private int[] stateMatrix;
	private boolean duplicated;
	private boolean terminal;
	private String parentLabel;
	private int[] generatorTransitionMatrix;
	
	public NodeInfo() {
	}
	
	public NodeInfo(String label){
		String[] tokens = label.substring(label.indexOf("[")+1, label.indexOf("]")).split(",");
		stateMatrix = new int[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			stateMatrix[i] = Integer.parseInt(tokens[i].trim());
		}
	}
	
	public NodeInfo(String label, String parentLabel){
		String[] tokens = label.substring(label.indexOf("[")+1, label.indexOf("]")).split(",");
		stateMatrix = new int[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			stateMatrix[i] = Integer.parseInt(tokens[i].trim());
		}
		this.parentLabel = parentLabel;
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
	
	public String getParentLabel() {
		return parentLabel;
	}

	public void setParentLabel(String parentLabel) {
		this.parentLabel = parentLabel;
	}

	public int[] getGeneratorTransitionMatrix() {
		return generatorTransitionMatrix;
	}

	public void setGeneratorTransitionMatrix(int[] generatorTransitionMatrix) {
		this.generatorTransitionMatrix = generatorTransitionMatrix;
	}

	public String getLabel(){
		return Arrays.toString(stateMatrix);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (duplicated ? 1231 : 1237);
		result = prime * result
				+ ((parentLabel == null) ? 0 : parentLabel.hashCode());
		result = prime * result + Arrays.hashCode(stateMatrix);
		result = prime * result + (terminal ? 1231 : 1237);
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
		if (duplicated != other.duplicated)
			return false;
		if (parentLabel == null) {
			if (other.parentLabel != null)
				return false;
		} else if (!parentLabel.equals(other.parentLabel))
			return false;
		if (!Arrays.equals(stateMatrix, other.stateMatrix))
			return false;
		if (terminal != other.terminal)
			return false;
		return true;
	}
	
}
