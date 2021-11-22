package views.impl;

import controllers.Controller;
import models.Item;
import models.Model;
import models.Player;
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
		Item[][] map = this.model.getMap();

		
		for (int i = 0 ; i < Map.HEIGHT ; i++) {
			for (int j = 0 ; j < Map.WIDTH ; j++) {
				System.out.print(map[i][j].getChar());
			}
			System.out.println("");
		}
	}
	
}
