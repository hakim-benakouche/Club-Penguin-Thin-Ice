package models;

import java.util.ArrayList;

import models.impl.Map.DIRECTION;

public interface Model {
	/**
	 * Permet d'obtenir l'arraylist contenant tous Items. 
	 * Méthode utilisée par les vues pour l'affichage.
	 * @return ArrayList Liste contenant tous les items.
	 */
	public ArrayList<Item> getMap();
	
	/**
	 * Permet d'obtenir un item sur un emplacement donné. Si plusieurs éléments au même emplacement,
	 * celui ayant la plus haute priorité sera retourné.
	 * @param x numéro de ligne, entre 0 et Map.HEIGHT-1 inclus.
	 * @param y numéro de colonne, entre 0 et Map.WIDTH-1 inclus.
	 * @return l'item ayant la plus haute priorité au (x,y) indiqué. Null si aucun élement présent.
	 */
	public Item getItem(int x, int y);
	
	/**
	 * Permet d'obtenir un item sur un emplacement donné. Si plusieurs éléments au même emplacement,
	 * celui ayant la plus haute priorité sera retourné.
	 * @param x numéro de ligne, entre 0 et Map.HEIGHT-1 inclus.
	 * @param y numéro de colonne, entre 0 et Map.WIDTH-1 inclus.
	 * @param excludePlayer si ce paramètre est à TRUE, le player sera ignoré.
	 * @return l'item ayant la plus haute priorité au (x,y) indiqué. Null si aucun élement présent.
	 */
	public Item getItem(int x, int y, boolean excludePlayer);
	
	/**
	 * Retourne l'item à côté du joueur dans la direction en paramètre.
	 * Permet d'acceder plus simplement aux items à côté du joueur pour le déplacer.
	 * @param direction dans laquelle nous souhaitons l'item, par rapport au joueur.
	 * @return l'item à côté du joueur dans la direction en paramètre.
	 */
	public Item getItem(DIRECTION direction);
	
	/**
	 * Retourne l'item à côté de l'item en paramètre dans la direction en paramètre.
	 * @param item selon lequel on souhaite avoir l'item autour de lui.
 	 * @param direction dans laquelle on souhaite avoir l'item, par rapport à l'item en paramètre.
	 * @return l'item trouvé ayant la plus haute priorité.
	 */
	public Item getItem(Item item, DIRECTION direction);
	
	/**
	 * Enlève un item de la map. Sert par exemple pour renlever la tondeuse une fois poussée
	 * @param item que l'on souhaite enlever de la map.
	 */
	public void removeItem(Item item);
	
	/**
	 * Retourne une liste contenant l'item ayant la plus haute propriété dans les 4 directions
	 * autour de l'item en paramètre.
	 * @param i l'item auquel on souhaites avoir les (au plus) 4 items autour de lui.
	 * @return une liste contenant les items. 
	 */
	public ArrayList<Item> getItemsAround(Item i);
	
	/**
	 * Compte le nombre de Path (glace) contenus dans la map. Sert à la fonction qui calcule
	 * le score.
	 * @return le nombre de chemins (en exluant PatgMapEnd).
	 */
	public int getNbPaths();
	
	/**
	 * Compte le nombre de Path (glace) contenus dans la map étant cassés (vie =0). Sert à la fonction qui calcule
	 * le score.
	 * @return le nombre de chemins ayant 0 de vie (en exluant PatgMapEnd).
	 */
	public int getNbPathsBreak();
	
	/**
	 * Calcule le nombre de points. La formule est : 
	 * Nombre_de_chemin + bonus_carte* + bonus_temps*.
	 * *bonus_carte : formule : 10 * map_id
	 * *bonus_temps : chaque map a un temps défini pour être complété, chaque seconde au
	 * dessus ou en dessous vaut respecticement à un point de moins ou de plus sur le score total.
	 * 
	 * Par exemple si la map 3 contenant 52 chemins a été finie en 50s et qu'elle était enregistrée avec 60s,
	 * le score sera de : 
	 * 52 + (10*3) + (60-50) = 92.
	 * @param timeToComplete temps réalisé pour completer la map (marcher sur toutes la glace
	 *  puis rejoindre le "PathMapEnd").
	 * @return le nombre de points selon la formule.
	 */
	public int getPoints(int timeToComplete);
	
	/**
	 * Déplace le joueur dans la direction souhaitée.
	 * @param direction souhaitée pour le déplacement du joueur.
	 */
	public void movePlayer(DIRECTION direction);
	
	/**
	 * Pour chaque ennemi contenu dans la map, nous appelons leur methode "moveDefault"
	 * pour qu'il se déplacent dans la direction qui leur est donne (haut/bas ou droite/gauche). 
	 */
	public void moveEnnemies();
	
	/**
	 * Permet de récupérer le joueur.
	 * @return le joueur de la map.
	 */
	public Player getPlayer();
	
	/**
	 * Permet de tuer le joueur et de le retirer de la liste des objets.
	 * Permet aux ennemies de tuer le joueur s'ils le rencontrent.
	 */
	public void killPlayer();
	
	/**
	 * Détermine si le joueur a un mouvement possible dans l'une des 4
	 * directions. Est utilisé pour déclencher la fin d'une map.
	 * @return TRUE si le joueur peut encore bouger, false sinon.
	 */
	public boolean playerCanMove();
	
	/**
	 * Permet de savoir si la map a été finie : toute la glace a été cassée et le joueur
	 * a rejoint la fin ("PathMapEnd").
	 * @return TRUE si la map est complétée, FALSE sinon
	 */
	public boolean isMapCompleted();


	
}
