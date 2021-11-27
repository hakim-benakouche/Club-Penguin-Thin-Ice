package Main;

import java.util.Scanner;

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
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenue dans Club Penguin Thin Ice \n");
		int choix = 0;
        System.out.println("Vous voulez : \n1 -> Jouer  \n2 -> Chargez une partie   \n3 -> Aide ? \n4 -> Quitter la partie");
        choix = sc.nextInt();
        while (saisieControle(choix) == false)//saisie controlé
        {
            System.out.println("Saisie incorrect. Veuillez choisir un chiffre entre : [1-2-3-4]");
            System.out.println("Vous voulez : \n1 -> Jouer ? \n2 -> Chargez une partie ?  \n3 -> Aide ? \n4 -> Quitter la partie  ?");
            choix = sc.nextInt();
        }
        switch (choix) {
        case 1:
        	play();
            break;
        case 2:
        	System.out.println("Chargement de la partie...");
            break;
        case 3:
        	System.out.println("Voici les règles du jeu : ");
        	break;
        case 4:
        		
        }
	}
	
	public void play() {
		int id = 0; //on stock l'id de la map
		while (id < 5) {
			while (playOneTime(id) == "perdue") { //si un niveau est perdue
				id-=1; // on doit refaire le niveau
				System.out.println("Veuillez recommencer le niveau!");
				break;
			}
			id+=1;
		}	
	}
	
	public String playOneTime(int mapId) {
		
		this.model = new Map(mapId); //on génère la map
		// choix entre le controlleur console ou swing
		this.controller = new ControllerConsole(this.model);
		//this.controller = new ControllerSwing(this.model);
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
		return status;
	}
	
	public static boolean saisieControle(int choix) {
        return (choix >= 1 && choix <= 4);
    }
}
