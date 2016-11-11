/*
*@author Jacob Guth
*
* Rough draft of Belt.java 11/8/2016
*
* NOTE: Further implementation will require a definition of a Package, an object that contains multiple 
* InventoryItem references
*
* TODO: implement initializeBelt(), a way to keep track of and increment time
*/

import java.util*;
public class Belt {
	
	private Floor floor;
	
	
	//picker and packer locations
	private Point begin;	
	private Point end;			
		
	private Integer currentTime;
	private Integer length;
	private Integer numCells;
	
	//Maps each cell id to its Cell object
	private HashMap<int, Cell> cells;
	
	//Maps each package id to the Cell it occupies
	private HashMap<int, Cell> packages;
	
	//Maps Belt cell indices to a Point location
	private ArrayList<Point> indexLocations;
	
	//booleans controllers for belt movement
	private boolean isMoving;
	private boolean canMove;
	
	/*
	* constructor -  initializes a belt with a designated Floor layout, begin/end points, and number of cells
	* 
	*/
	public Belt(Floor floor, Point begin, Point end, int numCells){
		
		this.floor = floor;
		this.begin = begin;
		this.end = end;
		this.numCells = numCells;
		
		currentTime = 0;
		isMoving = false;
		canMove = true;
		length = end.getY() - begin.getY();
		
		cells = new HashMap<>()
		packages = new HashMap<>();
		
		initializeBelt();
		numCells = cells.length();
	}
	
	/*
	* description: if the belt can move at currentTime, update the locations of each cell in the belt
	*/
	private void move(){
		if(canMove){
			updateLocations();
			currentTime++;
		}	
	}
	
	/*
	* description: starts/stops belt movement
	*/
	public void resume(){
		canMove = true;
	}

	public void stop(){
		canMove = false;
	}
	
	
	/*
	*@param package: an ArrayList of inventory items that represents a package (bin, box, etc.)
	*
	* description: adds/removes a package from the specified cell on the belt
	*/
	public void addPackage(Package package, Cell cell){	
	}
	
	public void removePackage(Package package, Cell cell){
	}
	
	
	/*
	* @param id: a cell id
	* @return: Cell object associate with @param id
	*/
	public Cell getCell(int id){
		return cells.get(id);
	}
	
	
	/*
	*@param package: A package of InventoryItems (bin, box, order, etc.)
	*@return: Point object associated with the cell @param package currently occupies
	*/
	public Point getPackageLocation(Package package){
		return packages.get(package.id).getLocation();
	}
	
	
	/*
	* description: updates the current status of the Belt to the Floor
	*
	* NOTE: further implementation I believe will require implementation of an updateStatus in
	* Floor.java
	*
	*/
	public void updateStatus(){
	}
	
	/*
	*description: increments the location of each Cell on the belt
	*/
	private void updateLocations(){
		for(Cell cell : cells.values() ){
			cell.incrementIndex();
		}
	}
	
	
	/*
	* description: This method will initialize the belt separated by cells. It will also initialize  
	* indexLocations representing the location of each index. Will complete once 
	* we decide on the length of the belt, the number of cells we want, etc. 
	*/
	private void initializeBelt(){	
	}
	
	/*
	* description: increment the time
	*/
	private void tick(){
		this.currentTime++;
	}
	
	/*
	*Belt is comprised of Cells, each with an index indicating its location on the belt and
	*an id. 
	*
	* description: class Cell has methods that identify and locate the packages they are occupied with
	*/
	private class Cell{
		
		private Point location;
		
		private int index;
		private int id;
		private int width;
		private int height;
		private boolean occupied;
		private Package package;
		
		/*
		* Constructor: initializes a cell with a designated width, height, id, and location
		*
		*/
		public Cell(int width, int height, int id, Point location){
			this.width = width;
			this.id = id;
			this.location = location;
			occupied = false;
			package = null;
			
			cells.put(id, this);	
		}
		
		/*
		* @param package: Package to be added to this Cell
		*/
		public void addPackage(Package package){
			this.package = package;
			occupied = true;
		}
		
		/*
		* description: removes and returns the package from this cell
		*/
		public Package removePackage(){
			Package temp = this.package;
			this.package = null;
			occupied = false;
			return temp;
		}
		
		public boolean isOccupied(
			return occupied;
		)
		
		/*
		* @return: returns the package occupying this cell, if there is one; Prints  a message otherwise
		*/
		public Package getPackage(){
			try{
				return package;
			}catch(Exception e){
				System.out.println("Cell "+id+"does not contain a package");
			}
		}
		
		/*
		* @return: the location of this cell
		*/
		public Point getLocation(){
			return location;
		}
		
		/*
		* description: increment this cell's index by one; if it's at the end of the belt, move it to
		* the beginning
		*/
		public void incrementIndex(){
			if(index == numCells){
				index = 0;
			}else{
				index++;
			}
			updateLocation();
		}
		
		
		 /*
		 * description: updates the location of this cell
		 */
		public void updateLocation(){
			this.location = indexLocations.get(this.index);
		}
	}
	
}