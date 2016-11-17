import java.awt.*;
import java.util.*;
import java.util.List;
/**
 * 
 * @author Gabriel White
 *
 */
public class Floor {

	private Master master;
	InventoryManagement inventory;

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

    /**
     * Constructor
     * @param m (Master)
     */
	public Floor(Master m, InventoryManagement i) {
		master = m;
        inventory = i;
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

    public Shelf raiseShelf(Point location) {
        if (grid[location.x][location.y] == Item.ROBOTLOWEREDSHELF) {
            grid[location.x][location.y] = Item.ROBOTRAISEDSHELF;
            return inventory.getShelfByLocation(location);
        }
        return null;
    }

    /**
     *
     * @param oldpos
     * @return false if movement is prohibited (another robot in the way)
     */
	public boolean moveRobot(Point oldpos, Point newpos, boolean carryshelf) {
        // Set state of new location and verify move can take place
        if (grid[oldpos.x][oldpos.y] == Item.ROBOTRAISEDSHELF) {
            if (grid[newpos.x][newpos.y] == Item.EMPTY) {
                grid[newpos.x][newpos.y] = Item.ROBOTRAISEDSHELF;
            } else {
                return false;
            }
        } else {
            if (grid[newpos.x][newpos.y] == Item.EMPTY) {
                grid[newpos.x][newpos.y] = Item.ROBOT;
            } else if (grid[newpos.x][newpos.y] == Item.SHELF) {
                grid[newpos.x][newpos.y] = Item.ROBOTLOWEREDSHELF;
            } else {
                return false;
            }
        }
        // Set state of old location
        if (grid[oldpos.x][oldpos.y] == Item.ROBOT) {
            grid[oldpos.x][oldpos.y] = Item.EMPTY;
        } else if (grid[oldpos.x][oldpos.y] == Item.ROBOTLOWEREDSHELF) {
            grid[oldpos.x][oldpos.y] = Item.SHELF;
        } else if (grid[oldpos.x][oldpos.y] == Item.ROBOTRAISEDSHELF) {
            grid[oldpos.x][oldpos.y] = Item.EMPTY;
        }
        return true;
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
		CHARGER,
        ROBOTRAISEDSHELF,
        ROBOTLOWEREDSHELF
	}
}
