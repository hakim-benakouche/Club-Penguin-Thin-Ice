package models.impl.items;

public class PathMapEnd extends Path {

	public PathMapEnd(int x, int y) {
		super(1, x, y);
	}
	
	@Override
	public char getChar() {
		return 'W';
	}
}
