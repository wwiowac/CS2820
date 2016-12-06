package production;/*
* @author Jacob Guth
*
* Defines a group of InventoryItems for Belt implementation
*/
import java.util.HashMap;
import java.util.ArrayList;

public class Package{
    
    private String ID;
    private ArrayList<InventoryItem> contents;
    private HashMap<String, InventoryItem> items;   //Maps each InventoryItem in the package to its respective object
    
    /**
     * @constructor: initializes a Package with an ArrayList of InventoryItems
     */
    public Package(ArrayList<InventoryItem> contents, String ID){
        this.ID = ID;
        for(InventoryItem item : contents){
            items.put(item.getId(), item);
        }
    }

    public Package(InventoryItem content) {
        this.contents = new ArrayList<>();
        this.contents.add(content);
    }
    
    public String getID(){
        return ID;
    }
    
    public ArrayList<InventoryItem> getContents(){
        return contents;
    }

    /**
     * Adds an InventoryItem to this package
     * @param item 
     */
    public void addItem(InventoryItem item){
        items.put(item.getId(), item);
    }

    /**
     * Removes an item from this package
     * @param id : String id of the InventoryItem to be removed
     */
    public void removeItem(String id){
        items.remove(id);
    }

    /**
     * Returns true if this package contains the specified InventoryItem; false otherwise
     * @param id : String id of the InventoryItem in question
     * @return boolean 
     */
    public boolean contains(String id){
        return items.containsKey(id);
    }
}