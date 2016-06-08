package br.ufpi.automatos.modelo.petri;

/**
 * @author PedroAlmir
 */
public class PetriNetObject {
	/** Name */
	private String name;

	/**
	 * @param name
	 */
	public PetriNetObject(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
