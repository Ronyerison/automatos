package br.ufpi.automatos.modelo;

public class Data {
	private String id;
	private int x;
	private int y;

	public Data() {
	}
	
	public Data(String id, int x, int y) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Data [id=" + id + "]";
	}
	
	
}
