package br.ufpi.automatos.modelo;

public class Transicao<T, E> {
	private T info;
	private Estado<E> origem;
	private Estado<E> destino;
	
	/**
	 * @param info
	 * @param origem
	 * @param destino
	 */
	public Transicao(T info, Estado<E> origem, Estado<E> destino) {
		this.info = info;
		this.origem = origem;
		this.destino = destino;
	}

	/**
	 * @return the info
	 */
	public T getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(T info) {
		this.info = info;
	}

	/**
	 * @return the origem
	 */
	public Estado<E> getOrigem() {
		return origem;
	}

	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(Estado<E> origem) {
		this.origem = origem;
	}

	/**
	 * @return the destino
	 */
	public Estado<E> getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(Estado<E> destino) {
		this.destino = destino;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return info + "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transicao<T, E> other = (Transicao<T, E>) obj;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		return true;
	}
}
