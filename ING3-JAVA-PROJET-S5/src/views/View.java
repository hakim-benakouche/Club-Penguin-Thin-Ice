package views;

public interface View {
	/**
	 * M�thode utilis�e pour afficher la map (d�s qu'un tour a �t� fini).
	 */
	public void displayMap();
	
	/**
	 * M�thode utilis�e pour afficher le score de la map (le nombre de
	 * glace cass�e sur le nombre total de glace).
	 */
	public void displayCurrentMapScore();
	
	/**
	 * Permet d'afficher le nombre total de point (points cumul�s)
	 * @param totalPoint score total.
	 */
	public void displayTotalPoint(int totalPoint);
}
