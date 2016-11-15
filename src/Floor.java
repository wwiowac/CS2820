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
	//warehouse dimensions
	static final int warehouseWidth = 200;
	static final int warehouseDepth = 100;
	
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
	Map<String,Cell> warehousePoints;
	
	Random rand;
    /**
     * Constructor
     * @param m (Master)
     */
	public Floor(Master m) {
		rand = new Random();
		int r = rand.nextInt();
		master = m;
		shelfAreas = new ArrayList<ShelfArea>();
		warehousePoints = new HashMap<String,Cell>();
		shelfAreas.add(new ShelfArea(new Point(100,70),140,rand));
		shelfAreas.add(new ShelfArea(new Point(120,70),140,rand));
		shelfAreas.add(new ShelfArea(new Point(140,70),140,rand));
		for(int i = 0; i < warehouseWidth;i++) {//generates the entire warehouse, represented by Cell objects.
			for(int j = 0; j < warehouseDepth;j++) {
				Point P = new Point(i,j);
				Cell N = new Cell(i,j);
				for(ShelfArea current: shelfAreas) {
					if(current.hasWithin(P)) {
						N = current.getCell(P);
						assert N != null;
					}
				}
				warehousePoints.put(P.toString(), N);
			}
		}
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
		else if(current == highway1) {//chooses a random shelf area
			Random r = new Random();
			int s = r.nextInt(shelfAreas.size());
			nextArea = shelfAreas.get(s).corner;
		}
		else if(current == highway3) nextArea = highway4;
		else if(current == highway4) nextArea = highway2;
		
		return nextArea;
	}
	
	/**
	 * 
	 * @param P Point object of current location
	 * @return true if the Cell is empty, false if occupied
	 */
	public boolean isEmptyLocation(Point P) {
		if(getCell(P) == null) return true;
		else return false;
	}
	
	/**
	 * 
	 * @param P
	 * @return Cell at Point P
	 */
	public Cell getCell(Point P) {
		return warehousePoints.get(P.toString());  
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return getCell call
	 */
	public Cell getCell(int x, int y) {
		return getCell(new Point(x,y));
	}
	
	/**
	 * 
	 * @param P Point object to be updated
	 * @param i Cell object location
	 */
	public void updateItemAt(Point P) {
		warehousePoints.put(P.toString(), getCell(P));
	}
	
}