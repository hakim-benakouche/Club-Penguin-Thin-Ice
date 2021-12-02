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
	public static Player generateMapFromFile(ArrayList<Item> items, URI filePath) {
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
			
			
			Player tmp_player = MapFromFile.addLine(items, i, currentLine);
			if (tmp_player != null)
				p = tmp_player;
		}
		
		if (reader.hasNextLine())
			throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (trop de lignes)");
		reader.close();
		return p;
	}
	
	private static Player addLine(ArrayList<Item> items, int line, String string) {
		Item toAdd = null;
		Player p = null;
		
		char c;
		for (int j = 0 ; j < Map.WIDTH ; j++) {
			c = string.charAt(j);
			if (c >= '1' && c <= '9') {
				toAdd = new Path(Character.getNumericValue(c), line, j);
			} else if (c == '#')
				toAdd = new Wall(line, j);
			else if (c == '.')
				toAdd = new Empty(line, j);
			else if (c == 'P') {
				p = new Penguin(line, j);
				toAdd = new Path(1, line, j);
			} else if (c == 'W')
				toAdd = new PathMapEnd(line, j);
			else if (c == 'M')
				toAdd = new Mower(line, j);
			else
				throw new IllegalArgumentException("Le fichier ne respecte pas le format attendu (caract�re non reconnu)");
			
			if (toAdd != null)
				items.add(toAdd);
		}
		
		return p;
	}
}







