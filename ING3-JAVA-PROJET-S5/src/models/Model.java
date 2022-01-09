package models;

import java.util.ArrayList;

import models.impl.Map.DIRECTION;

public interface Model {
	/**
	 * Permet d'obtenir l'arraylist contenant tous Items. 
	 * M�thode utilisee par les vues pour l'affichage.
	 * @return ArrayList Liste contenant tous les items.
	 */
	public ArrayList<Item> getMap();
	
	/**
	 * Permet d'obtenir un item sur un emplacement donn�. Si plusieurs �l�ments au m�me emplacement,
	 * celui ayant la plus haute priorit� sera retourn�.
	 * @param x numero de ligne, entre 0 et Map.HEIGHT-1 inclus.
	 * @param y num�ro de colonne, entre 0 et Map.WIDTH-1 inclus.
	 * @return l'item ayant la plus haute priorit� au (x,y) indiqu�. Null si aucun �lement pr�sent.
	 */
	public Item getItem(int x, int y);
	
	/**
	 * Permet d'obtenir un item sur un emplacement donn�. Si plusieurs �l�ments au m�me emplacement,
	 * celui ayant la plus haute priorit� sera retourn�.
	 * @param x num�ro de ligne, entre 0 et Map.HEIGHT-1 inclus.
	 * @param y num�ro de colonne, entre 0 et Map.WIDTH-1 inclus.
	 * @param excludePlayer si ce param�tre est � TRUE, le player sera ignor�.
	 * @return l'item ayant la plus haute priorit� au (x,y) indiqu�. Null si aucun �lement pr�sent.
	 */
	public Item getItem(int x, int y, boolean excludePlayer);
	
	/**
	 * Retourne l'item � c�t� du joueur dans la direction en param�tre.
	 * Permet d'acceder plus simplement aux items � c�t� du joueur pour le d�placer.
	 * @param direction dans laquelle nous souhaitons l'item, par rapport au joueur.
	 * @return l'item � c�t� du joueur dans la direction en param�tre.
	 */
	public Item getItem(DIRECTION direction);
	
	/**
	 * Retourne l'item � c�t� de l'item en param�tre dans la direction en param�tre.
	 * @param item selon lequel on souhaite avoir l'item autour de lui.
 	 * @param direction dans laquelle on souhaite avoir l'item, par rapport � l'item en param�tre.
	 * @return l'item trouv� ayant la plus haute priorit�.
	 */
	public Item getItem(Item item, DIRECTION direction);
	
	/**
	 * Enl�ve un item de la map. Sert par exemple pour renlever la tondeuse une fois pouss�e
	 * @param item que l'on souhaite enlever de la map.
	 */
	public void removeItem(Item item);
	
	/**
	 * Retourne une liste contenant l'item ayant la plus haute propri�t� dans les 4 directions
	 * autour de l'item en param�tre.
	 * @param i l'item auquel on souhaites avoir les (au plus) 4 items autour de lui.
	 * @return une liste contenant les items. 
	 */
	public ArrayList<Item> getItemsAround(Item i);
	
	/**
	 * Compte le nombre de Path (glace) contenus dans la map. Sert � la fonction qui calcule
	 * le score.
	 * @return le nombre de chemins (en exluant PatgMapEnd).
	 */
	public int getNbPaths();
	
	/**
	 * Compte le nombre de Path (glace) contenus dans la map �tant cass�s (vie =0). Sert � la fonction qui calcule
	 * le score.
	 * @return le nombre de chemins ayant 0 de vie (en exluant PatgMapEnd).
	 */
	public int getNbPathsBreak();
	
	/**
	 * Calcule le nombre de points. La formule est : 
	 * Nombre_de_chemin + bonus_carte* + bonus_temps*.
	 * *bonus_carte : formule : 10 * map_id
	 * *bonus_temps : chaque map a un temps d�fini pour �tre compl�t�, chaque seconde au
	 * dessus ou en dessous vaut respecticement � un point de moins ou de plus sur le score total.
	 * 
	 * Par exemple si la map 3 contenant 52 chemins a �t� finie en 50s et qu'elle �tait enregistr�e avec 60s,
	 * le score sera de : 
	 * 52 + (10*3) + (60-50) = 92.
	 * @param timeToComplete temps r�alis� pour completer la map (marcher sur toutes la glace
	 *  puis rejoindre le "PathMapEnd").
	 * @return le nombre de points selon la formule.
	 */
	public int getPoints(int timeToComplete);
	
	/**
	 * D�place le joueur dans la direction souhait�e.
	 * @param direction souhait�e pour le d�placement du joueur.
	 */
	public void movePlayer(DIRECTION direction);
	
	/**
	 * Pour chaque ennemi contenu dans la map, nous appelons leur methode "moveDefault"
	 * pour qu'il se d�placent dans la direction qui leur est donne (haut/bas ou droite/gauche). 
	 */
	public void moveEnnemies();
	
	/**
	 * Permet de r�cup�rer le joueur.
	 * @return le joueur de la map.
	 */
	public Player getPlayer();
	
	/**
	 * Permet de tuer le joueur et de le retirer de la liste des objets.
	 * Permet aux ennemies de tuer le joueur s'ils le rencontrent.
	 */
	public void killPlayer();
	
	/**
	 * D�termine si le joueur a un mouvement possible dans l'une des 4
	 * directions. Est utilis� pour d�clencher la fin d'une map.
	 * @return TRUE si le joueur peut encore bouger, false sinon.
	 */
	public boolean playerCanMove();
	
	/**
	 * Permet de savoir si la map a �t� finie : toute la glace a �t� cass�e et le joueur
	 * a rejoint la fin ("PathMapEnd").
	 * @return TRUE si la map est compl�t�e, FALSE sinon
	 */
	public boolean isMapCompleted();


	
}
