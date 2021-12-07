package models;

public interface Player extends Movable {
	/**
	 * Enumeration contenant les divers effets de potions
	 *
	 */
	public enum EFFECTS {
		LIGHTNESS
	}
	
	/**
	 * Permet d'obtenir l'effet du joueur (utilisé par les PATH pour savoir 
	 * s'ils doivent perdre un PV ou non).
	 * @return l'Effet que le joueur possède (peut être null)
	 */
	public EFFECTS getEffect();
	
	/**
	 * Permet de donner un effet au joueur en donnant une durée.
	 * @param effect affecté au joueur.
	 * @param effectDuration nombre de déplacements que le joueur poura faire en 
	 * conservant l'effet.
	 */
	public void setEffect(EFFECTS effect, int effectDuration);
	
	/**
	 * Permet de savoir si le joueur est en vie ou non (pour l'évènement de fin de partie).
	 * @return TRUE si le joueur est en vie, FALSE sinon.
	 */
	public boolean isAlive();
	
	/**
	 * Permet de modifier le statut du joueur. Utilisé par un ennemi pour tuer le joueur
	 * s'il est à côté de lui.
	 * @param isAlive nouveau statut du joueur.
	 */
	public void setAlive(boolean isAlive);
}

