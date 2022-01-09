package models;

import models.impl.Map.DIRECTION;

public interface Item {
	/**
	   * Permet de savoir si le joueur peut marcher sur l'objet ou non.
	   * @return vrai ou faux respectivement si Item peut �tre pi�tinn� ou non.
	   */
	public boolean isReacheable();
	
	/**
	   * Permet de savoir si le joueur peut pousser sur l'objet ou non.
	   * Permet le d�clanchement de la m�thode "onPush()".
	   * @return vrai ou faux respectivement si Item peut �tre pouss� ou non.
	   */
	public boolean isPushable();
	
	/**
	   * Permet de r�cup�rer le caract�re associ� � l'objet pour l'affichage console.
	   * @return le caract�re associ�.
	   */
	public char getChar();
	
	/**
	   * Permet de r�cuperer le nom de l'Item (utile pour du d�bug).
	   * @return le nom associ�.
	   */
	public String getName();
	
	/**
	   * Permet d'obtenir la coordonn� x de l'objet.
	   * @return l'entier coordonn� X.
	   */
	public int getX();
	
	/**
	   * Permet d'obtenir la coordonn� Y de l'objet.
	   * @return l'entier coordonn� Y.
	   */
	public int getY();
	
	/**
	   * Permet de d�finir la coordonn� x de l'objet.
	   * @param x Nouveau coordonn� x.
	   */
	public void setX(int x);
	
	/**
	   * Permet d�finir la coordonn� Y de l'objet.
	   * @param y Nouveau coordonn� y. 
	   */
	public void setY(int y);
	
	/**
	 * Permet de conna�tre la priorit� de l'objet. Dans le cas ou deux objets sont 
	 * sur la m�me "case", le joueur int�ragira avec celui ayant la plus haute priorit�.
	 * Sert aussi pour l'ordre d'affichage
	 * @return int priorit� associ�e.
	 */
	public int getPriority();
	
	/**
	 * M�thode appel�e lorsque le joueur entre dans la case contenant l'item.
	 * @param p r�f�rence au joueur entrant dans l'item.
	 */
	public void onEnter(Player p);
	
	/**
	 * M�thode appel�e lorsque le joueur quitte la case contenant l'item.
	 * @param p r�f�rence au joueur sortant de l'item.
	 */
	public void onLeave(Player p);
	
	/**
	 * M�thode appel�e lorsque le joueur pousse l'objet (si celui-ci non reacheable et pushable, 
	 * voir plus haut).
	 * @param to Direction dans laquelle le joueur a pouss� l'Item.
	 */
	public void onPush(DIRECTION to);
	
}
