package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import controllers.Controller;
import controllers.impl.ControllerConsole;
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
	 * Attribut contenant le score cumul� de tous les niveaux. 
	 * Attribut utilis� pour la sauvegarde (voir m�thode saveCurrentGame et loadLastGame)
	 */
	private int scoreCumulated = 0;
	
	/**
	 * Attribut contenant l'id du niveau actuel. 
	 * Attribut utilis� pour la sauvegarde (voir m�thode saveCurrentGame et loadLastGame)
	 */
	private int currentMapId = 1;
	
	public Game() {
		System.out.println("Bienvenue dans Club Penguin Thin Ice \n");
		this.displayMenu();
	}
	
	/**
	 * Affiche le menu et g�re les diff�rents choix en appelant les autres m�thodes.
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
	        		System.out.println("\nChargement de la partie sauvegardée...\n");
	        		play();
	            break;
	        case 3:
	        	System.out.println("Voici les regles du jeu :" + "\n");
	        	System.out.println("Prise en main du jeu : \r\n\n"
	        			+ "Les caractères : \r\n"
	        			+ "- ECEMAN -> 'P'\r\n"
	        			+ "- Vide -> '.'\r\n"
	        			+ "- Murs -> '#'\r\n"
	        			+ "- Eau -> 'O'\r\n"
	        			+ "- Glace fine -> 'x'\r\n"
	        			+ "- Glace épaisse -> 'X'\r\n"
	        			+ "- Porte -> 'W'\r\n"
	        			+ "- Tondeuse -> 'M'\r\n"
	        			+ "- Brique de mur -> 'I'\r\n"
	        			+ "- Arrivée brique de mur -> 'i' \r\n"
	        			+ "- Potion de légèreté -> 'L'\r\n"
	        			+ "- Potion lourde -> 'H'\r\n"
	        			+ "- Tunnel -> 'T'\r\n"
	        			+ "- Ennemi déplacement horizontale -> 'E'\r\n"
	        			+ "- Ennemi déplacement verticale -> 'e' \n");
	        	
	        	System.out.println("Pour déplacer ECEMAN, veuillez utiliser les boutons directionels à l'aide de la popup qui s'affiche");
	        	System.out.println("Pour gagner une partie, il vous faut parcourir toutes les cases");
	        	System.out.println("La sauvegarde est automatique a chaque fin de partie");
	        	System.out.println("Voila ! Vous avez toutes les cartes en mains pour réussir");
	        	System.out.println("Bonne chance ! \n\n");
	        	this.displayMenu();
	        	break;
	        case 4:
	        	System.out.println("A bientot !");
	        	break;
        }
	}
	
	/*
	 * M�thode qui permet de sauvegarder la partie en cours. 
	 * Cette m�thode est appel� � chaque fois qu'un niveau est termin� et r�ussis.
	 */
	public void saveCurrentGame() {
		// si toutes les cartes ont �t� finies,
		// on r�initialise les valeurs pour la sauvegarde
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
	 * M�thode qui permet de charger une partie enregistr�e dans les 
	 * attributs currentMapId et scoreCummulated (voir plus haut). 
	 * Cette m�thode est appel� si le joueur souhaite charger une partie.
	 * 
	 * Le fichier doit �tre cr�e (mais peut �tre vide).
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
	 * G�re le cycle total du jeu : changement des maps, calcul des scores,
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
		System.out.println("Jeu termin� !");
		this.enterToContinue();
		this.displayMenu();
	}
	
	/**
	 * M�thode qui g�re une map et qui finie quand le joueur a perdu (bloqu�) ou gagn�.
	 * Cette m�thode choisis le mod�le, la vue et le controlleur utilis�.
	 * Cette m�thode calcule le score en fonction du temps.
	 * @param mapId Id de la map souhait�e.
	 * @return VRAI si la partie a �t� gagn�e (toute la glace cass�e), FALSE sinon.
	 */
	public boolean playOneTime(int mapId) {
		
		this.model = new Map(mapId); //on g�n�re la map
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
		
		// affiche si partie gagn�e ou perdue
		String status = "perdu";
		if (this.model.isMapCompleted()){
			status = "gagn�";
			this.scoreCumulated += this.model.getPoints((int) diff); 
		} 
		
		this.view.displayTotalPoint(this.scoreCumulated);
		
		this.controller.destroy();
		System.out.println("Niveau " + mapId  + " " + status + " en " + diff + " secondes ! \n");
		this.enterToContinue();
		
		return status == "gagn�" ;
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
	 * M�thode de saisie c�ntrol�e pour le menu, choix entre 1 et 4 inclus.
	 * @param choix r�alis� par l'utilisateur
	 * @return TRUE si le choix est inclus dans la selection, FALSE sinon.
	 */
	public static boolean saisieControle(int choix) {
        return (choix >= 1 && choix <= 4);
    }
}
