/*
*@author Jacob Guth
*
*Rough draft of Belt.java 11/8/2016
*Implementation moving forward will depend on Bin, Order, and Package definitions
*/

import java.util*;
public class Belt implements EventConsumer{

	private Station begin;
	private Station end;
	
	private Integer currentTime;
	private Integer length;
	private Integer numCells;
	
	private HashMap<int, Cell> cells;
	
	private HashMap<Package, Cell> packages;
	
	private boolean isMoving;
	private boolean canMove;
	
	public Belt(Station begin, Station end){
		
		this.begin = begin;
		this.end = end;
		
		currentTime = 0;
		isMoving = false;
		canMove = true;
		length = (end.getLocation().getY() - (begin.getLocation().getY() + begin.getHeight()));
		cells = new HashMap<>()
		initializeBelt();
		numCells = cells.length();
	}
	
	private void move(){
		if(canMove){
			updateLocations();
			currentTime++;
		}	
	}
	
	public void resume(){
		isMoving = true;
	}
	
	public void stop(){
		isMoving = false;
	}
	
	public void addPackage(Package package, Cell cell){	
	}
	
	public void removePackage(Package package, Cell cell){
	}
	
	public Location getPackageLocation(Package package){
	}
	
	public void updateStatus(){
	}
	
	private void updateLocations(){
		for(Cell cell : cells.values() ){
			cell.incrementIndex();
		}
	}
	
	/*
	*This method will initialize the belt separated by cells. Will complete once 
	*we decide on the length of the belt, the number of cells we want, etc.
	*/
	private void initializeBelt(){	
	}
	
	/*
	*Belt is comprised of Cells, each with an index indicating its location on the belt and
	*an id. 
	*/
	class Cell{
		
		private int index;
		private int id;
		private int width;
		private boolean occupied;
		private Package package;
		
		public Cell(int width, id){
			this.width = width;
			this.id = id;
			occupied = false;
			package = null;
			
			cells.put(id, this);	
		}
		
		public void addPackage(Package package){
		}
		
		public void removePackage(){
		}
		
		public boolean isOccupied(
			return occupied;
		)
		
		public Package getPackage(){
			try{
				return Package;
			}catch(Exception e){
				System.out.println("Cell "+id+"does not contain a package");
			}
		}
		
		public void incrementIndex(){
			if(index == numCells){
				index = 0;
			}else{
				index++;
			}
		}
	}
	
}