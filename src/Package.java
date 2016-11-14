/*
* @author Jacob Guth
*
* description: Class Package defines a group of InventoryItems for Belt implementation
*/

public class Package{
	
	//Maps each InventoryItem in the package to its respective object
	private HashMap<String, InventoryItem> items;
	
	private Point location;
	
	/*
	* Constructor: initializes a Package with an ArrayList of InventoryItems
	*/
	public Package(ArrayList<InventoryItem> items){
		items = new HashMap<>();
		
		//fill items
		for(InventoryItem item: items){
			this.items.put(item.getId(), item);
		}
	}
	
	//returns Point location of this package
	public Point getLocation(){
		return this.location;
	}
	
	//sets the location of this package to @param location
	public void setLocation(Point location){
		this.location = location;
	}
	
	//returns the InventoryItem contents of this package
	public HashMap<String, InventoryItem> getItems(){
		return this.items;
	}
	
	//adds InventoryItem @param item to this package
	public void addItem(InventoryItem item){
		this.items.put(item.getId(), item);
	}
	
	//removes the InventoryItem with id @param id from this package
	public void removeItem(String id){
		this.items.remove(id);
	}
	
	//returns true if this package contains InventoryItem with @param id: false otherwise
	public boolean contains(String id){
		return items.containsKey(id);
	}
}