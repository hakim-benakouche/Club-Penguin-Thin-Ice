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
        	System.out.println("Voici les regles du jeu : ");
        	break;
        case 4:
        }
	}
	
	public void play() {
		
		int id = 1; //on stock l'id de la map
		while (id < 6) {
			while (playOneTime(id) == "perdue") { //si un niveau est perdue
				id-=1; // on doit refaire le niveau
				System.out.println("Veuillez recommencer le niveau !");
				break;
			}
			System.out.println("Chargement du niveau :");
			id+=1;
		}	
	}
	
	public String playOneTime(int mapId) {
		
		this.model = new Map(mapId); //on génère la map
		// choix entre le controlleur console ou swing
		//this.controller = new ControllerConsole(this.model);
		this.controller = new ControllerSwing(this.model);
		this.view = new ViewConsole(this.model, this.controller);
		
		long debut = System.currentTimeMillis();
		
		boolean game_finished = false;
		while ((!game_finished || !this.model.isMapCompleted()) && this.model.playerCanMove()) {
			
			System.out.println("\n\n\n");
			this.view.displayMap(); 
			System.out.println("\n");
			this.view.displayScore();
			game_finished = this.controller.playUserMove();
		}
		
		System.out.println("\n\n\n");
		// Affiche la map finale
		this.view.displayMap();
		long fin = System.currentTimeMillis();
		float diff = (fin - debut) /1000.0f; 
		
		// affiche si partie gagnée ou perdue
		String status = "gagn�e";
		if (!this.model.isMapCompleted()){
			status = "perdue";
			this.view.displayPoint(mapId-1); //on affiche le score précédent
		} else {
			this.view.displayPoint(mapId);
		}
		
		this.controller.destroy();
		System.out.println("Niveau " + mapId  + " " + status + " en " + diff + " secondes ! \n");
		return status;
	}
	
	public static boolean saisieControle(int choix) {
        return (choix >= 1 && choix <= 4);
    }
}
