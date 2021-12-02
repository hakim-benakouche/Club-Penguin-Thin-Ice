package models;

import java.util.ArrayList;

import models.impl.Map.DIRECTION;

public interface Model {
	public ArrayList<Item> getMap();
	public Item getItem(int posLine, int posColumn);
	public Item getItem(DIRECTION direction);
	public int getNbPaths();
	public int getNbPathsBreak();
	public int getPoints(int i);
	public void movePlayer(DIRECTION direction);
	public Player getPlayer();
	public boolean playerCanMove();
	public boolean isMapCompleted();


	
}
