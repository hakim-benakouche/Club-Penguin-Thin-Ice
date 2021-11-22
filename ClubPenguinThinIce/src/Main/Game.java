package Main;

import controllers.Controller;
import controllers.impl.ControllerConsole;
import controllers.impl.ControllerSwing;
import models.Model;
import models.impl.Map;
import views.View;
import views.impl.ViewConsole;

public class Game {
	private Controller controller;
	private Model model;
	private View view;
	
	public Game() {
		this.model = new Map();
		// choix entre le controlleur console ou swing
		this.controller = new ControllerConsole(this.model);
		//this.controller = new ControllerSwing(this.model);
		this.view = new ViewConsole(this.model, this.controller);
		
		boolean game_finished = false;
		while (!game_finished) {
			System.out.println("\n\n\n\n");
			this.view.displayMap();
			game_finished = this.controller.playUserMove();
		}
		// Affiche la map finale
		this.view.displayMap();
		System.out.println("Partie finie !");
	}
}
