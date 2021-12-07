package models.impl.items;

import models.Item;
import models.Player;
import models.impl.Map.DIRECTION;

/**
 * Cette classe abstraite permet de définir le comportement par défaut
 * de tous les items. 
 * Si un item a un comportement différent (par exemple les PATH ont l'attribut 
 * "isReacheable" dynamique), il peuvent redéfinir la méthode à l'aide d'un @Override  
 *
 */
public abstract class ItemImpl implements Item {
	private int x;
	private int y;
	private char car;
	private String name;
	private int priority;
	private boolean isReacheable;
	private boolean isPushable;
	
	public ItemImpl(int x, int y, char car, String name, 
			int priority, boolean isReacheable, boolean isPushable) {
		this.x = x;
		this.y = y;
		this.car = car;
		this.name = name;
		this.priority = priority;
		this.isReacheable = isReacheable;
		this.isPushable = isPushable;
	}
	
	@Override
	public int getX() {
		return this.x;
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}
	
	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public char getChar() {
		return car;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public int getPriority() {
		return priority;
	}
	
	@Override
	public boolean isReacheable() {
		return this.isReacheable;
	}
	
	@Override
	public void onEnter(Player player) {
	}

	@Override
	public void onLeave(Player player) {
	}
	
	
	@Override
	public void onPush(DIRECTION to) {		
	}
	
	@Override
	public boolean isPushable() {
		return this.isPushable;
	}
	
	@Override
	public String toString() {
		return this.getName() + " " + this.getChar() + " (" + this.getX() +", " + this.getY() +")";
	}
	
	

}
