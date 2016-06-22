/**
 * 
 */
package br.ufpi.automatos.modelo.petri.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ronyerison
 *
 */
public class Node<T> {
	private Node<T> parent = null;
	private T data = null;
	private List<Node<T>> children = new ArrayList<Node<T>>();
	private List<Edge<String, T>> edges = new ArrayList<Edge<String,T>>();

	public Node(T data) {
		this.data = data;
	}

	public Node(T data, Node<T> parent, String edgeValue) {
		this.data = data;
		this.parent = parent;
		this.parent.addEdge(new Edge<String, T>(edgeValue, parent, this));
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setParent(Node<T> parent, String edgeValue) {
		parent.addChild(this, edgeValue);
		this.parent = parent;
	}

	public void addChild(T data, String edgeValue) {
		Node<T> child = new Node<T>(data);
		this.children.add(child);
		this.addEdge(new Edge<String, T>(edgeValue, this, child));
	}

	public void addChild(Node<T> child, String edgeValue) {
		this.children.add(child);
		this.edges.add(new Edge<String, T>(edgeValue, this, child));
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isRoot() {
		return (this.parent == null);
	}

	public boolean isLeaf() {
		if (this.children.size() == 0)
			return true;
		else
			return false;
	}

	public void removeParent() {
		this.parent = null;
	}

	/**
	 * @return the edges
	 */
	public List<Edge<String, T>> getEdges() {
		return edges;
	}

	/**
	 * @param edges the edges to set
	 */
	public void setEdges(List<Edge<String, T>> edges) {
		this.edges = edges;
	}
	
	public void addEdge(Edge<String, T> edge){
		this.edges.add(edge);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return data+"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<NodeInfo> other = (Node<NodeInfo>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
	
	
	
}
