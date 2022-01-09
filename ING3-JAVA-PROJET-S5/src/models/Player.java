package models;

public interface Player extends Movable {
	/**
	 * Enumeration contenant les divers effets de potions
	 *
	 */
	public enum EFFECTS {
		LIGHTNESS, HEAVINESS
	}
	
	/**
	 * Permet d'obtenir l'effet du joueur (utilis� par les PATH pour savoir 
	 * s'ils doivent perdre un PV ou non).
	 * @return l'Effet que le joueur poss�de (peut �tre null)
	 */
	public EFFECTS getEffect();
	
	/**
	 * Permet de donner un effet au joueur en donnant une dur�e.
	 * @param effect affect� au joueur.
	 * @param effectDuration nombre de d�placements que le joueur poura faire en 
	 * conservant l'effet.
	 */
	public void setEffect(EFFECTS effect, int effectDuration);
	
	/**
	 * Permet de savoir si le joueur est en vie ou non (pour l'�v�nement de fin de partie).
	 * @return TRUE si le joueur est en vie, FALSE sinon.
	 */
	public boolean isAlive();
	
	/**
	 * Permet de modifier le statut du joueur. Utilis� par un ennemi pour tuer le joueur
	 * s'il est � c�t� de lui.
	 * @param isAlive nouveau statut du joueur.
	 */
	public void setAlive(boolean isAlive);
}

