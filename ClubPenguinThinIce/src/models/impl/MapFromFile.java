package models.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

import models.Item;
import models.Model;
import models.Player;
import models.impl.Map.DIRECTION;
import models.impl.items.Empty;
import models.impl.items.Ennemy;
import models.impl.items.IceBlock;
import models.impl.items.Mower;
import models.impl.items.Path;
import models.impl.items.PathMapEnd;
import models.impl.items.PathUnbreakable;
import models.impl.items.Penguin;
import models.impl.items.PotionLightness;
import models.impl.items.Tunnel;
import models.impl.items.Wall;

public class MapFromFile {
	
	/**
	 * Permet de lire le fichier spécifié en paramètre et de générer tous les items 
	 * contenus dans ce fichier texte. 
	 * Chaque item est représenté dans le fichier texte par son "char" sauf pour la glace.
	 * Chaque numéro (de 0 à 9) dans le fichier correspond à de la glace ayant de 
	 * 0 à 9 de vie.
	 * 
	 * Tout le fichier est vérifié : le bon nombre de lignes, colonnes, la présence du
	 * joueur, un nombre pair de tunnels.
	 * 
	 * Les fichiers doivent être placés dans le dossier /bin/ressources/maps/mapX.txt 
	 * avec X le numéro de map.
	 *  
	 * @param model Modele afin de le donner en paramètre de certains constructeurs.
	 * @param items ArrayList connue du modele qui contient tous les items.
	 * @param ennemies ArrayList connue du modele qui contient tous les ennemis.
	 * @param filePath Chemin d'accès au fichier contenant la map.
	 * @return une instance du Joueur. 
	 */
	public static Player generateMapFromFile(Model model, ArrayList<Item> items, ArrayList<Ennemy> ennemies, URI filePath) {
		File file = new File(filePath);
		if (! file.exists())
			return null;
		Scanner reader;
		try {
			reader = new Scanner(file, "utf-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
		
		if (!reader.hasNextLine())
			throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (Fichier vide)");
		
		Player p = null;
		reader.nextLine();
		String currentLine = null;
		for (int i = 0 ; i < Map.HEIGHT ; i++) {
			if (!reader.hasNextLine())
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (pas assez de lignes) : " + i);
			
			currentLine = reader.nextLine();
			if (currentLine.length() > Map.WIDTH)
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (trop de colonnes sur la ligne "
						+ ""+i+" : "+ currentLine +")");
			else if (currentLine.length() < Map.WIDTH)
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu "
						+ "(pas assez de colonnes sur la ligne "+i+" : "+ currentLine +")");
			
			
			Player tmp_player = MapFromFile.addLine(model, items, ennemies, i, currentLine);
			if (tmp_player != null)
				p = tmp_player;
		}
		
		if (reader.hasNextLine())
			throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (trop de lignes)");
		 reader.close();
		if (MapFromFile.tempTunnel != null) {
			throw new IllegalArgumentException("Il manque une pair d'un tunnel");
		}
		return p;
	}
	
	/**
	 * Permet de lire le temps estimé pour réalisé une map. Ce temps est écrit tout 
	 * en haut de chaque fonction. Cette méthode est utilisée pour calculer le score
	 * une fois le tableau complété.
	 * @param filePath le chemin du fichier contenant la carte.
	 * @return l'entier écrit correspondant au temps en secondes estimé pour finir 
	 * la carte.
	 */
	public static int getTimeToComplete(URI filePath) {
		File file = new File(filePath);
		if (! file.exists())
			return 0;
		Scanner reader;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
			return 0;
		}
		
		int time = reader.nextInt();;
		 reader.close();
		return time;
	}
	
	private static Tunnel tempTunnel = null;
	/**
	 * 
	 * @param model Modele afin de le donner en paramètre de certains constructeurs.
	 * @param items ArrayList connue du modele qui contient tous les items.
	 * @param ennemies ArrayList connue du modele qui contient tous les ennemis.
	 * @param line numéro de ligne de la map. Permet de définir la coordonné X des items.
	 * @param string Chaine de caractère contenant une ligne de la carte.
	 * @return une instance du Joueur. 
	 */
	private static Player addLine(Model model, ArrayList<Item> items, ArrayList<Ennemy> ennemies, int line, String string) {
		Player p = null;
		
		char c;
		for (int j = 0 ; j < Map.WIDTH ; j++) {
			c = string.charAt(j);
			if (c >= '0' && c <= '9') {
				items.add(new Path(Character.getNumericValue(c), line, j));
			} else if (c == '#')
				items.add(new Wall(line, j));
			else if (c == '.')
				items.add(new Empty(line, j));
			else if (c == 'P') {
				p = new Penguin(line, j);
				items.add(new Path(1, line, j));
			} else if (c == 'W') {
				items.add(new PathMapEnd(line, j));
			} else if (c == 'M') {
				items.add(new Path(1, line, j));
				items.add(new Mower(model, line, j));
			} else if (c == 'I') {
				items.add(new Path(1, line, j));
				items.add(new IceBlock(model, line, j));
			} else if (c == 'i') {
				items.add(new PathUnbreakable(line, j));
			} else if (c == 'L') {
				items.add(new Path(1, line, j));
				items.add(new PotionLightness(model, line, j));
			} else if (c == 'E' || c == 'e') {
				items.add(new Path(1, line, j));
				Ennemy e = new Ennemy(model, line, j, (c == 'E') ? DIRECTION.UP : DIRECTION.LEFT);
				items.add(e);
				ennemies.add(e);
			} else if (c == 'T') {
				
				if (MapFromFile.tempTunnel != null) {
					Tunnel pair = new Tunnel(MapFromFile.tempTunnel, line, j);
					items.add(pair);
					MapFromFile.tempTunnel.setPair(pair);
					MapFromFile.tempTunnel = null;
				} else {
					MapFromFile.tempTunnel = new Tunnel(line, j);
					items.add(MapFromFile.tempTunnel);
				}
				
			} else
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (caractère non reconnu)");
	
		}
		
		return p;
	}

}







