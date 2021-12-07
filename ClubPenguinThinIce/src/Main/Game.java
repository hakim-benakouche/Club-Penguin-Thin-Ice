package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import controllers.Controller;
import controllers.impl.ControllerSwing;
import models.Model;
import models.impl.Map;
import views.View;
import views.impl.ViewConsole;

public class Game {
	public static final int MAP_MAP_ID = 5;
	/**
	 * Stoque le controlleur selon le modele MVC.
	 */
	private Controller controller;
	
	/**
	 * Stoque le modele selon le modele MVC.
	 */
	private Model model;
	
	/**
	 * Stoque la vue selon le modele MVC.
	 */
	private View view;
	
	/**
	 * Attribut contenant le score cumulé de tous les niveaux. 
	 * Attribut utilisé pour la sauvegarde (voir méthode saveCurrentGame et loadLastGame)
	 */
	private int scoreCumulated = 0;
	
	/**
	 * Attribut contenant l'id du niveau actuel. 
	 * Attribut utilisé pour la sauvegarde (voir méthode saveCurrentGame et loadLastGame)
	 */
	private int currentMapId = 1;
	
	public Game() {
		System.out.println("Bienvenue dans Club Penguin Thin Ice \n");
		this.displayMenu();
	}
	
	/**
	 * Affiche le menu et gère les différents choix en appelant les autres méthodes.
	 */
	public void displayMenu() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		int choix = 0;
        System.out.println("Vous voulez : \n1 -> Jouer  \n2 -> Chargez une partie   \n3 -> Aide ? \n4 -> Quitter la partie");
        choix = sc.nextInt();
        
        while (saisieControle(choix) == false) {
            System.out.println("Saisie incorrect. Veuillez choisir un chiffre entre : [1-2-3-4]");
            System.out.println("Vous voulez : \n1 -> Jouer ? \n2 -> Chargez une partie ?  \n3 -> Aide ? \n4 -> Quitter la partie  ?");
            choix = sc.nextInt();
        }
        
        switch (choix) {
	        case 1:
	        	play();
	            break;
	        case 2:
	        	if (this.loadLastGame() == -1) {
	        		System.out.println("Aucune sauvegarde");
	        		this.enterToContinue();
	        		this.displayMenu();
	        	} else 
	        		play();
	            break;
	        case 3:
	        	System.out.println("Voici les regles du jeu : ");
	        	break;
	        case 4:
	        	System.out.println("A bientot !");
	        	break;
        }
	}
	
	/*
	 * Méthode qui permet de sauvegarder la partie en cours. 
	 * Cette méthode est appelé à chaque fois qu'un niveau est terminé et réussis.
	 */
	public void saveCurrentGame() {
		// si toutes les cartes ont été finies,
		// on réinitialise les valeurs pour la sauvegarde
		if (this.currentMapId == Game.MAP_MAP_ID) {
			this.currentMapId = 0;
			this.scoreCumulated = 0;
		}
		URI path;
		try {
			path = this.getClass().getResource("/ressources/saves/save.csv").toURI();
		} catch (Exception e2) {
			return;
		}
		
		FileWriter fw = null;
		try {
			File f = new File(path);
			fw = new FileWriter(f, false);
			fw.write(this.currentMapId + ";" + this.scoreCumulated);
			fw.close();
			
		} catch (IOException ex) {
		    System.err.println("Impossible de sauvegarder la partie");
		    ex.printStackTrace();
		}
	}
	
	/*
	 * Méthode qui permet de charger une partie enregistrée dans les 
	 * attributs currentMapId et scoreCummulated (voir plus haut). 
	 * Cette méthode est appelé si le joueur souhaite charger une partie.
	 * 
	 * Le fichier doit être crée (mais peut être vide).
	 */
	public int loadLastGame() {
		URI path;
		try {
			path = this.getClass().getResource("/ressources/saves/save.csv").toURI();
		} catch (Exception e2) {
			return -1;
		}
		
		File file = new File(path);
		if (!file.exists())
			return -1;
		
		Scanner reader;
		String[] game;
		try {
			reader = new Scanner(file);
			game = reader.nextLine().split(";");
			reader.close();
		} catch (Exception e) {
			return -1;
		}
		
		
		if (game.length != 2) return -1;
		
		
		this.currentMapId = Integer.valueOf(game[0])+1;
		this.scoreCumulated = Integer.valueOf(game[1]);
		return 1;
	}
	
	/**
	 * Gère le cycle total du jeu : changement des maps, calcul des scores,
	 * sauvegardes, affichage...
	 */
	public void play() {
		while (this.currentMapId <= Game.MAP_MAP_ID) {
			while (!playOneTime(this.currentMapId)) { // tant que le niveau est perdu
				this.currentMapId -= 1; // on doit refaire le niveau
				System.out.println("Veuillez recommencer le niveau !");
				break;
			}
			this.saveCurrentGame();
			System.out.println("Chargement du niveau :");
			this.currentMapId += 1;
		}	
		System.out.println("Jeu terminé !");
		this.enterToContinue();
		this.displayMenu();
	}
	
	/**
	 * Méthode qui gère une map et qui finie quand le joueur a perdu (bloqué) ou gagné.
	 * Cette méthode choisis le modèle, la vue et le controlleur utilisé.
	 * Cette méthode calcule le score en fonction du temps.
	 * @param mapId Id de la map souhaitée.
	 * @return VRAI si la partie a été gagnée (toute la glace cassée), FALSE sinon.
	 */
	public boolean playOneTime(int mapId) {
		
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
			this.view.displayCurrentMapScore();
			game_finished = this.controller.playUserMove();
		}
		
		System.out.println("\n\n\n");
		// Affiche la map finale
		this.view.displayMap();
		long fin = System.currentTimeMillis();
		float diff = (fin - debut) /1000.0f; 
		
		// affiche si partie gagnée ou perdue
		String status = "perdu";
		if (this.model.isMapCompleted()){
			status = "gagné";
			this.scoreCumulated += this.model.getPoints((int) diff); 
		} 
		
		this.view.displayTotalPoint(this.scoreCumulated);
		
		this.controller.destroy();
		System.out.println("Niveau " + mapId  + " " + status + " en " + diff + " secondes ! \n");
		this.enterToContinue();
		
		return status == "gagné" ;
	}
	/**
	 * Permet de marquer une pose dans l'affichage pour laisser le joueur lire les messages.
	 * L'utilisateur doit appuyer sur "entrer" pour continuer.
	 */
	@SuppressWarnings("resource")
	public void enterToContinue() {
		System.out.println("Appuyez pour continuer");
		new Scanner(System.in).nextLine();
	}
	
	/**
	 * Méthode de saisie côntrolée pour le menu, choix entre 1 et 4 inclus.
	 * @param choix réalisé par l'utilisateur
	 * @return TRUE si le choix est inclus dans la selection, FALSE sinon.
	 */
	public static boolean saisieControle(int choix) {
        return (choix >= 1 && choix <= 4);
    }
}
