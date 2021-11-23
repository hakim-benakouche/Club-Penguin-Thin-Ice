package models.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

import models.Item;
import models.Player;

public class MapFromFile {
	
	@SuppressWarnings("resource")
	public static Player generateMapFromFile(Item map[][], URI filePath, ArrayList<Path> paths) {
		//= new Item[Map.HEIGHT][Map.WIDTH]; 
		
		File file = new File(filePath);
		if (! file.exists())
			return null;
		Scanner reader;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
		Player p = null;
		String currentLine;
		for (int i = 0 ; i < Map.HEIGHT ; i++) {
			if (!reader.hasNextLine())
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (pas assez de lignes) : " + i);
			
			currentLine = reader.nextLine();
			if (currentLine.length() != Map.WIDTH)
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (pas assez/trop de colonnes)");
			
			
			Player tmp_player = MapFromFile.addLine(map, i, currentLine, paths);
			if (tmp_player != null)
				p = tmp_player;
		}
		
		if (reader.hasNextLine())
			throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (trop de lignes)");
		reader.close();
		return p;
	}
	
	private static Player addLine(Item[][] map, int line, String string, ArrayList<Path> paths) {
		Item toAdd;
		Player p = null;
		
		char c;
		for (int j = 0 ; j < Map.WIDTH ; j++) {
			c = string.charAt(j);
			if (c >= '1' && c <= '9') {
				toAdd = new Path(Character.getNumericValue(c));
				paths.add((Path) toAdd);
			} else if (c == '#')
				toAdd = new Wall();
			else if (c == '.')
				toAdd = new Empty();
			else if (c == 'P') {
				toAdd = new Penguin(line, j);
				p = (Player) toAdd;
			} else if (c == 'W')
				toAdd = new PathMapEnd();
			else
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (caractère non reconnu)");
		
			map[line][j] = toAdd;
		}
		
		return p;
	}
}







