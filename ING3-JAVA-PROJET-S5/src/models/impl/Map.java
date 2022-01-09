package models.impl;

import java.net.URISyntaxException;
import java.util.ArrayList;

import models.Item;
import models.Model;
import models.Player;
import models.impl.items.Ennemy;
import models.impl.items.Path;
import models.impl.items.PathMapEnd;

public class Map implements Model {
	public enum DIRECTION {
		   UP, LEFT, DOWN, RIGHT;
		}
	
	public final static int WIDTH  = 19;
	public final static int HEIGHT = 15;
	
	//private Item[][] map;
	private ArrayList<Item> items;
	private ArrayList<Ennemy> ennemies;
	private Player player; 
	private int mapId;
	private int normalTimeToComplete;
	
	/**
	 * Utilise la classe statique MapFromFile pour générer la map à partir d'un fichier
	 * texte.
	 * @param mapId de la map que l'on souhaite charger.
	 */
	public Map(int mapId) {
		this.items = new ArrayList<Item>();
		this.ennemies = new ArrayList<Ennemy>();
		this.mapId = mapId;
		
		if (mapId < 0 || mapId > 6)
			throw new IllegalArgumentException("La map demandée n'existe pas");
		
		String ressources = "/ressources/maps/map" + mapId + ".txt";
		
		try {
			this.player = MapFromFile.generateMapFromFile(this, this.items, this.ennemies,
					this.getClass().getResource(ressources).toURI());
			
			this.normalTimeToComplete =  MapFromFile.getTimeToComplete(this.getClass().getResource(ressources).toURI());
					
			this.items.add(this.player);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * On regarde retourne FALSE si l'un des chemin a encore de la vie. TRUE sinon
	 */
	public boolean isMapCompleted() {
		for (Item p : this.items) {
			if (p instanceof Path && ((Path) p).getPV() > 0 && ! (p instanceof PathMapEnd))
				return false;
		}
		return true;
	}
	
	/**
	 * On regarde si l'un des items dans les 4 directions et "reacheable" ou "pushable".
	 */
	public boolean playerCanMove() {
		if (!this.player.isAlive()) return false;
		for (DIRECTION d : DIRECTION.values()) {
			if (this.getItem(d) != null && (this.getItem(d).isReacheable() || this.getItem(d).isPushable()))
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
	
	public void killPlayer() {
		this.items.remove(this.player);
		this.player = null;
	}
	
	/**
	 * Compte le nombre de chemins en inclus dans les items de la map
	 * excluant le chemin de fin (PathMapEnd).
	 */
	@Override
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
	
	/**
	 * Compte le nombre de chemins en inclus dans les items de la map 
	 * ayant plus de vie et excluant le chemin de fin (PathMapEnd).
	 */
	@Override
	public int getNbPathsBreak() {
		int i = 0;
		for (Item p : this.items) {
			if (p instanceof Path && ((Path) p).getPV() == 0)
				i++;
		}
		return i;
	}
	
	@Override
	public int getPoints(int timeToComplete) {
		return this.getNbPathsBreak() +10 * this.mapId + 
			(this.normalTimeToComplete - timeToComplete);
	}
	
	@Override
	public Item getItem(int posLine, int posColumn) {
		return this.getItem(posLine, posColumn, false);
	}
	
	@Override
	public Item getItem(int posLine, int posColumn, boolean excludePlayer) {
		if (posLine < 0 || posColumn < 0 ||  
				posLine >= Map.HEIGHT || posColumn >= Map.WIDTH ) {
			return null;
		}
		Item m = null;		
		for (Item p : this.items) {
			if (p.getX() == posLine && p.getY() == posColumn && 
					(!(p instanceof Player)) &&
					(m == null || p.getPriority() > m.getPriority()) )
				m = p;
		}
		return m;
	}
	
	@Override
	public Item getItem(Item item, DIRECTION direction) {
		if (direction == DIRECTION.UP) {
			return this.getItem(item.getX() - 1, item.getY());
		} else if (direction == DIRECTION.LEFT) {
			return this.getItem(item.getX(), item.getY() - 1);
		} else if (direction == DIRECTION.DOWN) {
			return this.getItem(item.getX() + 1, item.getY());
		} else if (direction == DIRECTION.RIGHT) {
			return this.getItem(item.getX(), item.getY() + 1);
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
	
	public ArrayList<Item> getItemsAround(Item i) {
		ArrayList<Item> items = new ArrayList<Item>();
		for (DIRECTION d : DIRECTION.values()) {
			Item current = this.getItem(i, d);
			if (current != null) 
				items.add(current);
		}
		return items;
	}
	
	@Override
	public void removeItem(Item item) {
		this.items.remove(item);
	}
	
	@Override
	/**
	 * Avant chaque mouvement du joueur : notifier l'Item avec la méthode "onLeave",
	 * Après chaque mouvement du joueur : notifier l'Item avec la méthode "onEnter",
	 * Une fois l'action du joueur terminée : déplacement des ennemis.
	 */
	public void movePlayer(DIRECTION direction) {
		
		// notifier l'item sous le joueur qu'il le quitte
		int playerX = this.player.getX();
		int playerY = this.player.getY();
		
		this.getItem(playerX, playerY, true).onLeave(this.player);
		
		// on déplace le joueur dans la direction
		this.player.move(direction);
		
		//on récupere l'item sous les pieds du joueur et on le notifier
		this.getItem(this.player.getX(), this.player.getY()).onEnter(this.player);
		
		// on déplace les mobs 
		this.moveEnnemies();
	}
	
	public void moveEnnemies() {
		for (Ennemy e : this.ennemies) {
			e.moveDefault();
		}
	}


}