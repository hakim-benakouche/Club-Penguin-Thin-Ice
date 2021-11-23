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
	
	private Item[][] map;
	private Player player; 
	private Item behindPlayer=null;
	private ArrayList<Path> paths;
	
	public Map() {
		//					ligne		colonne
		this.map = new Item[Map.HEIGHT][Map.WIDTH]; 
		this.player = new Penguin(Map.HEIGHT -4, 2);
		this.paths = new ArrayList<Path>();
/*		try {
			this.player = MapFromFile.generateMapFromFile(this.map, 
					this.getClass().getResource("/ressources/map2.txt").toURI(),
					this.paths);
			this.behindPlayer = new Path(1);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.exit(0);
		}
		*/
		this.generateEmptyMap();
		this.generate_map(0);
	}
	
	
	
	private void generateEmptyMap() {
		for (int i = 0 ; i < Map.HEIGHT ; i++) {
			for (int j = 0 ; j < Map.WIDTH ; j++) {
				this.map[i][j] = new Empty();
			}
		}
	}
	
	private void generate_map(int mapId) {
		if ( mapId < 0 || mapId > 0) 
			throw new IllegalArgumentException("Map Id NYI");
		
		if (mapId == 0) {
			Path p;
			for (int i = 1 ; i < Map.WIDTH - 2 ; i++) {
				this.map[Map.HEIGHT -3][i] = new Wall();
				p = new Path(1);
				this.map[Map.HEIGHT -4][i] = p;
				this.paths.add(p);
				
				this.map[Map.HEIGHT -5][i] = new Wall();
			}
			//on enleve le chemin pv 1 généré avant
			this.paths.remove(this.map[Map.HEIGHT -4][Map.WIDTH - 7]);
			p = new Path(2);
			this.map[Map.HEIGHT -4][Map.WIDTH - 7] = p;
			this.paths.add(p);
			
			this.paths.remove(this.map[Map.HEIGHT -4][Map.WIDTH - 6]);
			p = new Path(2);
			this.map[Map.HEIGHT -4][Map.WIDTH - 6] = p;
			this.paths.add(p);
			
			this.paths.remove(this.map[Map.HEIGHT -4][Map.WIDTH - 4]);
			this.map[Map.HEIGHT -4][Map.WIDTH - 4] = new PathMapEnd();
			
			this.paths.remove(this.map[Map.HEIGHT -4][1]);
			this.map[Map.HEIGHT -4][1] = new Wall();
			this.paths.remove(this.map[Map.HEIGHT -4][Map.WIDTH - 3]);
			this.map[Map.HEIGHT -4][Map.WIDTH - 3] = new Wall();
			
			this.behindPlayer = this.map[Map.HEIGHT -4][2];
			this.map[Map.HEIGHT -4][2] = this.player;
		}
	}
	
	public boolean isMapFinised() {
		for (Path p : this.paths) {
			if (p.getPV() > 0)
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
	
	public Item[][] getMap() {
		return this.map;
	}
	
	public Player getPlayer() {
		return this.player;
	}



	@Override
	public Item getItem(int posLine, int posColumn) {
		if (posLine < 0 || posColumn < 0 ||  
				posLine >= Map.HEIGHT || posColumn >= Map.WIDTH ) {
			return null;
		}
		return this.map[posLine][posColumn];
	}



	@Override
	public Item getItem(DIRECTION direction) {
		if (direction == DIRECTION.UP) {
			return this.getItem(this.player.getPosLine() - 1, this.player.getPosColumn());
		} else if (direction == DIRECTION.LEFT) {
			return this.getItem(this.player.getPosLine(), this.player.getPosColumn() - 1);
		} else if (direction == DIRECTION.DOWN) {
			return this.getItem(this.player.getPosLine() + 1, this.player.getPosColumn());
		} else if (direction == DIRECTION.RIGHT) {
			return this.getItem(this.player.getPosLine(), this.player.getPosColumn() + 1);
		}
		return null;
	}



	@Override
	public void movePlayer(DIRECTION direction) {
		// notifier l'item sous le joueur qu'il le quitte
		this.behindPlayer.onLeave();
		
		// replacer l'item a la place de joueur
		this.map[this.player.getPosLine()][this.player.getPosColumn()] = this.behindPlayer;
		
		
		if (direction == DIRECTION.UP) {
			this.player.addPosLine(-1);	
		} else if (direction == DIRECTION.LEFT) {
			this.player.addPosColumn(-1);	
		} else if (direction == DIRECTION.DOWN) {
			this.player.addPosLine(1);	
		} else if (direction == DIRECTION.RIGHT) {
			this.player.addPosColumn(1);	
		}
		
		//on récupere l'item sous les pieds du joueur
		Item i = this.getItem(this.player.getPosLine(), this.player.getPosColumn());
		
		// on notifie l'item que le joueur a marché sur lui
		i.onEnter();
		
		//on save l'item sous le joueur
		this.behindPlayer = i;
		
		// on ajoute le joueur sur la map (avec sa nouvelle pos)
		this.map[this.player.getPosLine()][this.player.getPosColumn()] = this.player;
	}
}










