package br.ufpi.automatos.modelo;

public class Estado<E> {
	private E info = null;
	private boolean inicial;
	private boolean marcado;

	public Estado(E info) {
		this.info = info;
	}
	
	public Estado(E info, boolean inicial, boolean marcado) {
		this.info = info;
		this.inicial = inicial;
		this.marcado = marcado;
	}

	public Estado(Estado<E> estado) {
		this.info = estado.info;
		this.inicial = estado.inicial;
		this.marcado = estado.marcado;
	}

	public E getInfo() {
		return this.info;
	}

	public void setInfo(E info) {
		this.info = info;
	}

	public boolean isInicial() {
		return inicial;
	}

	public void setInicial(boolean inicial) {
		this.inicial = inicial;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return info+"";
	}
	
	public Estado<E> clone() {
		return new Estado<E>(this);
	}
}
