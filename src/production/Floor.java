package production;

import java.awt.*;

/**
 * @author Gabriel White
 */
public class Floor {

    private Master master;

    //warehouse dimensions
    static final int warehouseWidth = 200;
    static final int warehouseDepth = 100;
    private Cell[][] grid;

    //warehouse locations and names
    final Point chargers = new Point(100, 10);
    final Point shippingDock = new Point(10, 0);
    final Point belt = new Point(20, 70);
    final Point pack = new Point(50, 40);
    final Point pick = new Point(50, 90);
    final Point receivingDock = new Point(190, 0);
    final Point highway1 = new Point(130, 85);
    final Point highway2 = new Point(80, 65);
    final Point highway3 = new Point(175, 65);
    final Point highway4 = new Point(130, 25);

    /**
     * Constructor
     *
     * @param m (Master)
     */
    public Floor(Master m) {
        master = m;

        grid = new Cell[warehouseWidth][warehouseDepth];
        // Initialize the floor to be empty
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * get methods for the various locations
     *
     * @return Point objects
     */
    public Point getPicker() {
        return pick;
    }

    public Point getPacker() {
        return pack;
    }

    public Point getShippingDock() {
        return shippingDock;
    }

    public Point getReceivingDock() {
        return receivingDock;
    }

    public Point getCharger() {
        return chargers;
    }

    public Point getBelt() {
        return belt;
    }

    /**
     * Returns a 2D array of Cells, which represent the state of the floor
     *
     * @author Jacob Roschen
     *
     * @return
     */
    public Cell[][] getGrid() {
        return grid;
    }

    public Shelf raiseShelf(Point location) {
        if (grid[location.x][location.y].type == Cell.Type.ROBOTLOWEREDSHELF) {
            grid[location.x][location.y].type = Cell.Type.ROBOTRAISEDSHELF;
            Shelf s = master.inventory.getShelfByLocation(location);
            s.setAvailable(false);
            return s;
        }
        return null;
    }

    public void lowerShelf(Point location) {
        grid[location.x][location.y].type = Cell.Type.ROBOTLOWEREDSHELF;
        Shelf s = master.inventory.getShelfByLocation(location);
        s.setAvailable(true);
    }

    /**
     * Tries to update the floor map for the new and old robot positions
     *
     * @author Jacob Roschen
     * @author Wes Weirather
     *
     * @param newpos Old robot location
     * @param oldpos New robot location
     * @return false if movement is prohibited (another robot in the way)
     */
    public boolean moveRobot(Point oldpos, Point newpos, boolean hasShelf) {
        // Set state of new location and verify move can take place
        if (grid[oldpos.x][oldpos.y].type == Cell.Type.ROBOTRAISEDSHELF) {
            if (grid[newpos.x][newpos.y].type == Cell.Type.EMPTY) {
                grid[newpos.x][newpos.y].type = Cell.Type.ROBOTRAISEDSHELF;
            } else {
                return false;
            }
        } else {
            switch (grid[newpos.x][newpos.y].type) {
                case EMPTY:
                    grid[newpos.x][newpos.y].type = Cell.Type.ROBOT;
                    break;
                case SHELF:
                    grid[newpos.x][newpos.y].type = Cell.Type.ROBOTLOWEREDSHELF;
                    break;
                case HOME:
                    grid[newpos.x][newpos.y].type = Cell.Type.ROBOTHOME;
                    break;
                default:
                    return false;
            }
        }

        // Set state of old location
        switch (grid[oldpos.x][oldpos.y].type) {
            case ROBOT:
            case ROBOTRAISEDSHELF:
                grid[oldpos.x][oldpos.y].type = Cell.Type.EMPTY;
                break;
            case ROBOTLOWEREDSHELF:
                grid[oldpos.x][oldpos.y].type = Cell.Type.SHELF;
                break;
            case ROBOTHOME:
                grid[oldpos.x][oldpos.y].type = Cell.Type.HOME;
        }

        return true;
    }

    /**
     * This will 'set' the point on the grid with the item.
     * The grid will be used for the visualizer
     *
     * @author Jacob Roschen
     *
     * @param p Point to update
     * @param i Type to put in its location
     */
    public void updateItemAt(Point p, Cell.Type i) {
        grid[p.x][p.y].type = i;
    }

    public boolean isEmptyLocation(Point p) {
        return (grid[p.x][p.y].type == Cell.Type.EMPTY);
    }
}
