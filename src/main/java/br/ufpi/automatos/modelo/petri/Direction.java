package br.ufpi.automatos.modelo.petri;


public enum Direction {
	PLACE_TO_TRANSITION {
		@Override
		public boolean canFire(Place p, int weight) {
			return p.hasAtLeastTokens(weight);
		}

		@Override
		public void fire(Place p, int weight) {
			p.removeTokens(weight);
		}
	}, TRANSITION_TO_PLACE {
		@Override
		public boolean canFire(Place p, int weight) {
			return !p.maxTokensReached(weight);
		}

		@Override
		public void fire(Place p, int weight) {
			p.addTokens(weight);
//			if(p.getBundle() != null && p.getManager() != null){
//				p.getManager().prepareExecuteExtractAndPropagateResultsOfBundle(p.getBundle());
//			}
		}
	};

	
	/**
	 * Can fire?
	 * @param p
	 * @param weight
	 * @return
	 */
	public abstract boolean canFire(Place p, int weight);
	/**
	 * Fire!
	 * @param p
	 * @param weight
	 */
	public abstract void fire(Place p, int weight);
}