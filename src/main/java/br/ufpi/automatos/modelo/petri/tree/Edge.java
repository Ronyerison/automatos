/**
 * 
 */
package br.ufpi.automatos.modelo.petri.tree;

/**
 * @author Ronyerison
 *
 */
public class Edge<E, V> {
	private E data;
	private Node<V> parent;
	private Node<V> child;
	
	/**
	 * @param data
	 * @param parent
	 * @param child
	 */
	public Edge(E data, Node<V> parent, Node<V> child) {
		this.data = data;
		this.parent = parent;
		this.child = child;
	}

	/**
	 * @return the data
	 */
	public E getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * @return the parent
	 */
	public Node<V> getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Node<V> parent) {
		this.parent = parent;
	}

	/**
	 * @return the child
	 */
	public Node<V> getChild() {
		return child;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(Node<V> child) {
		this.child = child;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return data + "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Edge<E, V> other = (Edge<E, V>) obj;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	
}
