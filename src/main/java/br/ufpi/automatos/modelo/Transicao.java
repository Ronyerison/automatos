package br.ufpi.automatos.modelo;

public class Transicao<E, V> {
	private E info;
	private Estado<V> origem;
	private Estado<V> destino;
	
	/**
	 * @param info
	 * @param origem
	 * @param destino
	 */
	public Transicao(E info, Estado<V> origem, Estado<V> destino) {
		this.info = info;
		this.origem = origem;
		this.destino = destino;
	}

	/**
	 * @return the info
	 */
	public E getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(E info) {
		this.info = info;
	}

	/**
	 * @return the origem
	 */
	public Estado<V> getOrigem() {
		return origem;
	}

	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(Estado<V> origem) {
		this.origem = origem;
	}

	/**
	 * @return the destino
	 */
	public Estado<V> getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(Estado<V> destino) {
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
		Transicao<E, V> other = (Transicao<E, V>) obj;
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
