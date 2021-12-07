package models;

import models.impl.Map.DIRECTION;

public interface Item {
	/**
	   * Permet de savoir si le joueur peut marcher sur l'objet ou non.
	   * @return vrai ou faux respectivement si Item peut être piétinné ou non.
	   */
	public boolean isReacheable();
	
	/**
	   * Permet de savoir si le joueur peut pousser sur l'objet ou non.
	   * Permet le déclanchement de la méthode "onPush()".
	   * @return vrai ou faux respectivement si Item peut être poussé ou non.
	   */
	public boolean isPushable();
	
	/**
	   * Permet de récupérer le caractère associé à l'objet pour l'affichage console.
	   * @return le caractère associé.
	   */
	public char getChar();
	
	/**
	   * Permet de récuperer le nom de l'Item (utile pour du débug).
	   * @return le nom associé.
	   */
	public String getName();
	
	/**
	   * Permet d'obtenir la coordonné x de l'objet.
	   * @return l'entier coordonné X.
	   */
	public int getX();
	
	/**
	   * Permet d'obtenir la coordonné Y de l'objet.
	   * @return l'entier coordonné Y.
	   */
	public int getY();
	
	/**
	   * Permet de définir la coordonné x de l'objet.
	   * @param x Nouveau coordonné x.
	   */
	public void setX(int x);
	
	/**
	   * Permet définir la coordonné Y de l'objet.
	   * @param y Nouveau coordonné y. 
	   */
	public void setY(int y);
	
	/**
	 * Permet de connaître la priorité de l'objet. Dans le cas ou deux objets sont 
	 * sur la même "case", le joueur intéragira avec celui ayant la plus haute priorité.
	 * Sert aussi pour l'ordre d'affichage
	 * @return int priorité associée.
	 */
	public int getPriority();
	
	/**
	 * Méthode appelée lorsque le joueur entre dans la case contenant l'item.
	 * @param p référence au joueur entrant dans l'item.
	 */
	public void onEnter(Player p);
	
	/**
	 * Méthode appelée lorsque le joueur quitte la case contenant l'item.
	 * @param p référence au joueur sortant de l'item.
	 */
	public void onLeave(Player p);
	
	/**
	 * Méthode appelée lorsque le joueur pousse l'objet (si celui-ci non reacheable et pushable, 
	 * voir plus haut).
	 * @param to Direction dans laquelle le joueur a poussé l'Item.
	 */
	public void onPush(DIRECTION to);
	
}
