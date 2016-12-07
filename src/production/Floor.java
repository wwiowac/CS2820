package production;

import java.awt.*;

/**
 * @author Gabriel White
 */
public class Floor {

    private Master master;

    //warehouse dimensions
    private static final int warehouseWidth = 60;
    private static final int warehouseDepth = 30;
    private Cell[][] grid;

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

    public Cell getCell(Point p){return grid[p.x][p.y];}

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

    /**
     * Checks to see if the specified point p is empty
     *
     * @author Jacob Roschen
     *
     * @param p Point to check
     * @return True if the point is valid and empty
     */
    public boolean isEmptyLocation(Point p) {
        if(p.x < 0 || p.x >= warehouseWidth || p.y < 0 || p.y >= warehouseDepth) return false;

        return (grid[p.x][p.y].type == Cell.Type.EMPTY);
    }

    /**
     * Checks to see if the robot can move to the specified location. Is a helper method for checkAndUpdateCost()
     * @author Jacob Roschen
     *
     * @param c The cell to check if one can move to
     * @param hasShelf Where you can move is determined by if the robot has a shelf
     * @return
     */
    boolean canMove(Point p, boolean hasShelf) {
        Cell c = grid[p.x][p.y];
        if (c.type == Cell.Type.ROBOT ||
                c.type == Cell.Type.EMPTY ||
                c.type == Cell.Type.ROBOTLOWEREDSHELF ||
                c.type == Cell.Type.ROBOTRAISEDSHELF ||
                c.type == Cell.Type.HOME ||
                (c.type == Cell.Type.SHELF && !hasShelf)) {
            return true;
        }

        return false;
    }
}
