package br.ufpi.automatos.modelo;

public class InfoEstado {
	private String label;
	private int x;
	private int y;

	public InfoEstado() {
	}
	
	public InfoEstado(String label) {
		super();
		this.label = label;
		this.x = 100 + (int)(Math.random() * 700);
		this.y = 100 + (int)(Math.random() * 500);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		InfoEstado other = (InfoEstado) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
//		return "InfoEstado [label=" + label + ", x=" + x + ", y=" + y + "]";
		return label;
	}

	
}
