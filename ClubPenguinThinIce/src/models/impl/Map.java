package models.impl;

import java.net.URISyntaxException;
import java.util.ArrayList;

import models.Item;
import models.Model;
import models.Player;

public class Map implements Model {
	public enum DIRECTION {
		   UP, LEFT, DOWN, RIGHT;
		}
	
	public final static int WIDTH  = 19;
	public final static int HEIGHT = 15;
	
	//private Item[][] map;
	private ArrayList<Item> items;
	private Player player; 
	
	
	public Map(int mapId) {
		//					ligne		colonne
		//this.map = new Item[Map.HEIGHT][Map.WIDTH]; 
		this.items = new ArrayList<Item>();
		
		String ressources = "/ressources/";
		
		switch (mapId) {
		
		case 1 :
			ressources+="map1.txt";
			break;
		case 2 :
			ressources+="map2.txt";
			break;
		case 3: 
			ressources+="map3.txt";
			break;
		case 4:
			ressources+="map4.txt";
		}
		
		try {
			this.player = MapFromFile.generateMapFromFile(this.items, 
					this.getClass().getResource(ressources).toURI());
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
		//this.generateEmptyMap();
		//this.generate_map(mapId);
	}
	
	public boolean isMapCompleted() {
		for (Item p : this.items) {
			if (p instanceof Path && ((Path) p).getPV() > 0 && ! (p instanceof PathMapEnd))
				return false;
			
		}
		return true;
	}
	
	public boolean playerCanMove() {
		for (DIRECTION d : DIRECTION.values()) {
			if (this.getItem(d).isReacheable())
				return true;
		}
		return false;
	}
	
	public ArrayList<Item> getMap() {
		return this.items;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int getNbPaths() {
		int i = 0;
		for (Item p : this.items) {
			if (p instanceof Path)
				i++;
			if (p instanceof PathMapEnd)
				i--;
		}
		return i;	
	}
	
	public int getNbPathsBreak() {
		int i = 0;
		for (Item p : this.items) {
			if (p instanceof Path && ((Path) p).getPV() == 0)
				i++;
		}
		return i;
	}
	
	public int getPoints(int mapId) {
			switch (mapId) {
			case 1 :
				return 14 + 10; //24
			case 2 :
				return getPoints(mapId-1) + 26 + 20; //70
			case 3 : 
				return getPoints(mapId-1) + 24 + 30; //122
			}
		return 0;
	}

	@Override
	public Item getItem(int posLine, int posColumn) {
		if (posLine < 0 || posColumn < 0 ||  
				posLine >= Map.HEIGHT || posColumn >= Map.WIDTH ) {
			return null;
		}
		
		for (Item p : this.items) {
			if (p.getX() == posLine && p.getY() == posColumn)
				return p;
		}
		return null;
	}

	//retourne l'item a cote du joueur dans la direction 'direction'
	@Override
	public Item getItem(DIRECTION direction) {
		if (direction == DIRECTION.UP) {
			return this.getItem(this.player.getX() - 1, this.player.getY());
		} else if (direction == DIRECTION.LEFT) {
			return this.getItem(this.player.getX(), this.player.getY() - 1);
		} else if (direction == DIRECTION.DOWN) {
			return this.getItem(this.player.getX() + 1, this.player.getY());
		} else if (direction == DIRECTION.RIGHT) {
			return this.getItem(this.player.getX(), this.player.getY() + 1);
		}
		return null;
	}
	

	@Override
	public void movePlayer(DIRECTION direction) {
		
		// notifier l'item sous le joueur qu'il le quitte
		int playerX = this.player.getX();
		int playerY = this.player.getY();
		this.getItem(playerX, playerY).onLeave();
		
		// on d�place le joueur dans la direction
		this.player.move(direction);
		
		//on r�cupere l'item sous les pieds du joueur et on le notifier
		this.getItem(this.player.getX(), this.player.getY()).onEnter();
		
	}


}