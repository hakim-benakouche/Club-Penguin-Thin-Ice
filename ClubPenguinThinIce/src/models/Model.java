package models;

import models.impl.Map.DIRECTION;

public interface Model {
	public Item[][] getMap();
	public Item getItem(int posLine, int posColumn);
	public Item getItem(DIRECTION direction);
	public void movePlayer(DIRECTION direction);
	public Player getPlayer();
}
