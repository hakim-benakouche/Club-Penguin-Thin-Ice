package models.impl.items;

import models.Player;

public class Tunnel extends ItemImpl {
	private Tunnel pair;
	private boolean reacheable = true;
	
	public Tunnel(int x, int y) {
		super(x, y, 'T', "Tunnel", 3, true, false);

	}
	public Tunnel(Tunnel pair, int x, int y) {
		this(x, y);
		this.pair = pair;
	}
	
	public void setPair(Tunnel pair) {
		this.pair = pair;
	}
	
	@Override
	public boolean isReacheable() {
		return reacheable;
	}
	
	public void setReacheable(boolean status) {
		this.reacheable = status;
	}

	@Override
	/**
	 * Une fois qu'un tunnel a été emprunté, celui-ci et sa pair sont définis
	 * comme non Reacheable (ceci empêche pour le joueur de reprendre le tunnel).
	 */
	public void onEnter(Player player) {
		if (this.pair == null)
			throw new IllegalStateException("Le tunnel n'a pas de pair");
		System.out.println("Tunnel emprunté");
		player.setX(this.pair.getX());
		player.setY(this.pair.getY());
		this.pair.setReacheable(false);
		this.setReacheable(false);
	}
}
