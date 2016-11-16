import java.awt.Point;

/**
 * 
 * @author Gabriel
 * Generates each Shelf in a ShelfArea
 */
public class Shelf {
	Point home;// home location of shelf
	/**
	 * Constructor needs home Point where shelf is born and 
	 * returns after moving around
	 */ 
	public Shelf(Point home) {
		this.home = new Point(home.x,home.y);
	}
}