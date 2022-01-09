package models.impl.items;

import models.Movable;
import models.impl.Map.DIRECTION;

/**
 * Classe utilis� par tous les items pouvant se d�placer au cours du jeu 
 * (Player, Ennemi, Ice Bloc, Mower...).
 * M�me comportement que ItemImpl, permet de d�finir un comportement par defaut.
 * Chaque classe peut red�finir la m�thode move si elle le souhaite.
 *
 */
public abstract class MovableImpl extends ItemImpl implements Movable  {
	
	public MovableImpl(int x, int y, char car, String name, int priority, boolean isReacheable, boolean isPushable) {
		super(x, y, car, name, priority, isReacheable, isPushable);
	}

	@Override
	 public void move(DIRECTION d){
		if (d == DIRECTION.UP) {
			this.setX(this.getX() -1);
		} else if (d == DIRECTION.DOWN) {
			this.setX(this.getX() +1);
		} else if (d == DIRECTION.LEFT) {
			this.setY(this.getY() -1);
		} else if (d == DIRECTION.RIGHT) {
			this.setY(this.getY() +1);
		}
	}
}
