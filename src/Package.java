/*
* @author Jacob Guth
*
* description: Class Package defines a group of InventoryItems for Belt implementation
*/
import java.util.HashMap;
import java.util.ArrayList;

public class Package{
    
    private String ID;
    private ArrayList<InventoryItem> contents;
    //Maps each InventoryItem in the package to its respective object
    private HashMap<String, InventoryItem> items;
    
    /*
    * Constructor: initializes a Package with an ArrayList of InventoryItems
    */
    public Package(ArrayList<InventoryItem> contents, String ID){
        this.ID = ID;
        for(InventoryItem item : contents){
            items.put(item.getId(), item);
        }
    }
    
    public String getID(){
        return ID;
    }
    //returns the InventoryItem contents of this package
    public ArrayList<InventoryItem> getContents(){
        return contents;
    }

    //adds InventoryItem @param item to this package
    public void addItem(InventoryItem item){
        items.put(item.getId(), item);
    }

    //removes the InventoryItem with id @param id from this package
    public void removeItem(String id){
        items.remove(id);
    }

    //returns true if this package contains InventoryItem with @param id: false otherwise
    public boolean contains(String id){
        return items.containsKey(id);
    }
}