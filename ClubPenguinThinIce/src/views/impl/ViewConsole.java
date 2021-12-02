package views.impl;

import java.util.ArrayList;

import controllers.Controller;
import models.Item;
import models.Model;
import models.Player;
import models.impl.Empty;
import models.impl.Map;
import views.View;

public class ViewConsole implements View {
	private Model model;
	private Controller controller;
	
	public ViewConsole(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
	}
	
	@Override
	public void displayMap() {
		Empty e = new Empty(-1, -1);
		char[][] mapChar = new char[Map.HEIGHT][Map.WIDTH];
		
		ArrayList<Item> map = this.model.getMap();
		
		
		for (int i = 0 ; i < Map.HEIGHT ; i++) {
			for (int j = 0 ; j < Map.WIDTH ; j++) {
				mapChar[i][j] = e.getChar(); 
			}
		}
		
		// ajout des items 1 a 1
		for (Item i : map) {
			mapChar[i.getX()][i.getY()] = i.getChar(); 
		}
		// ajout du joueura la toute fin
		Player p = this.model.getPlayer();
		mapChar[p.getX()][p.getY()] = p.getChar();
		
		for (int i = 0 ; i < Map.HEIGHT ; i++) {
			for (int j = 0 ; j < Map.WIDTH ; j++) {
				System.out.print(mapChar[i][j]);
			}
			System.out.println("");
		}
	}

	public void displayScore() {
		System.out.println("Score : " + model.getNbPathsBreak()+ "/" + model.getNbPaths());
	}
	
	public void displayPoint(int mapId) {
		System.out.println("Points : " + model.getPoints(mapId));
	}
	
}
