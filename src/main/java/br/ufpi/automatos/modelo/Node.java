package br.ufpi.automatos.modelo;

public class Node {
	private Data data;

	public Node() {
	}
	
	public Node(Data data) {
		super();
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Node [data=" + data + "]";
	}
	
}
