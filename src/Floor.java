import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
/**
 * 
 * @author Gabriel White
 *
 */
public class Floor {

	private Master master;
	//warehouse dimensions
	static final int warehouseWidth = 200;
	static final int warehouseDepth = 100;
	private Item[][] grid = new Item[warehouseWidth][warehouseDepth];

	//warehouse locations and names
	final Point chargers = new Point(100,10);
	final Point shippingDock = new Point(10,0);
	final Point belt = new Point(20,70);
	final Point pack = new Point(50,40);
	final Point pick = new Point(50,90);
	final Point receivingDock = new Point(190,0);
	final Point highway1 = new Point(130,85);
	final Point highway2 = new Point(80,65);
	final Point highway3 = new Point(175,65);
	final Point highway4 = new Point(130,25);

	//lists of objects
	List<ShelfArea> shelfAreas;
	HashMap<Robot, Integer[]> robot_locs;

    /**
     * Constructor
     * @param m (Master)
     */
	public Floor(Master m) {
		master = m;
		shelfAreas = new ArrayList<ShelfArea>();
		robot_locs = new HashMap<>();
		shelfAreas.add(new ShelfArea(new Point(100,70),160));
		shelfAreas.add(new ShelfArea(new Point(120,70),160));
		shelfAreas.add(new ShelfArea(new Point(140,70),160));
	}

	/**
	 * get methods for the various locations
	 * @return Point objects
	 */
	public Point getPicker() { return pick; }
	public Point getPacker() { return pack; }
	public Point getShippingDock() { return shippingDock; }
	public Point getReceivingDock() { return receivingDock; }
	public Point getCharger() { return chargers; }
	public Point getBelt() { return belt; }

	/**
	 * Useful for product distribution on shelves.
	 * @return Point object inside shelf area
	 */
	public Point randomInShelfArea() {
		Random r = new Random();
		int s = r.nextInt(shelfAreas.size());
		return shelfAreas.get(s).randomPoint();
	}

	/**
	 * Path finder for robot
	 * @param current
	 * @return Point object of the next area for the robot to travel
	 */
	public Point path(Point current) {
		Point nextArea = new Point(0,0);
		if(current == shelfAreas.get(0).corner || current == shelfAreas.get(1).corner || current == shelfAreas.get(2).corner) nextArea = highway3;
		else if(current == chargers) nextArea = highway2;
		else if(current == highway2) nextArea = pick;
		else if(current == pick) nextArea = highway1;
		else if(current == highway1) {
			Random r = new Random();
			int s = r.nextInt(shelfAreas.size());
			nextArea = shelfAreas.get(s).corner;
		}
		else if(current == highway3) nextArea = highway4;
		else if(current == highway4) nextArea = highway2;

		return nextArea;
	}

	/**
	 * This will 'set' the point on the grid with the item.
	 * The grid will be used for the visualizer
	 * @param p Point to update
	 * @param i Item to put in its location
	 */
	public void updateItemAt(Point p, Item i) {
		grid[p.x][p.y] = i;
	}

	public boolean isEmptyLocation(Point p) {
		return (grid[p.x][p.y] == Item.EMPTY);
	}

    /**
     * All of the types of things that are represented on the floor
     */
	public enum Item {
		EMPTY,
		SHELF,
		ROBOT,
		PICKER,
		BELT,
		PACKAGE,
		CHARGER
	}

	//for testing purposes
	public static void main(String[] args) {


	}
	/**
	 * Mock ShelfArea class for testing
	 * @author Gabriel
	 *
	 */
	public class ShelfArea {
		  int width; // height will always be 2 -- just two shelves
		  Point corner;  // lower left corner of shelf area
		  /**
		   * @param corner - lower left corner of shelf area
		   * @param width - how many squares wide shelf area is
		   */
		  ShelfArea(Point corner, int width) {

			this.corner = new Point(corner.x,corner.y);
			this.width = width;

		       }

		  Point randomPoint() {
				Random R = new Random();
				int column = R.nextInt(width);
				int row = R.nextInt(2);
				Point P = new Point(corner.x+column,corner.y-row);
				return P;
			    }
		  }
}
