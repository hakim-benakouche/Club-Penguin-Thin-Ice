package views;

public interface View {
	/**
	 * Méthode utilisée pour afficher la map (dès qu'un tour a été fini).
	 */
	public void displayMap();
	
	/**
	 * Méthode utilisée pour afficher le score de la map (le nombre de
	 * glace cassée sur le nombre total de glace).
	 */
	public void displayCurrentMapScore();
	
	/**
	 * Permet d'afficher le nombre total de point (points cumulés)
	 * @param totalPoint score total.
	 */
	public void displayTotalPoint(int totalPoint);
}
