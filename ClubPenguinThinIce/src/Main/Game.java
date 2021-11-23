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
		//this.controller = new ControllerConsole(this.model);
		this.controller = new ControllerSwing(this.model);
		this.view = new ViewConsole(this.model, this.controller);
		
		long debut = System.currentTimeMillis();
		
		boolean game_finished = false;
		while ((!game_finished || !this.model.isMapFinised()) && this.model.playerCanMove()) {
			System.out.println("\n\n\n\n");
			this.view.displayMap();
			game_finished = this.controller.playUserMove();
		}
		System.out.println("\n\n\n\n");
		// Affiche la map finale
		this.view.displayMap();
		
		long fin = System.currentTimeMillis();
		float diff = (fin - debut) /1000.0f; 
		
		// affiche si partie gagnée ou perdue
		String status = "gagnée";
		if (!this.model.isMapFinised()) 
			status = "perdue";
		
		System.out.println("Partie " + status + " en " + diff + " secondes !");
	}
}
