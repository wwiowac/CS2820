package production;/*
* @author Jacob Guth
*
* Defines a group of InventoryItems for Belt implementation
*/
import java.util.ArrayList;

public class Bin {

    private Order order;
    private ArrayList<InventoryItem> contents;
    
    /**
     * @constructor: initializes a Bin with an ArrayList of InventoryItems
     */
    public Bin(Order order){
        this.order = order;
    }

    private boolean isFinished(){
        if(contents.size() != order.getOrdersItems().size()) {
            return false;
        }
        for(InventoryItem item : order.getOrdersItems()){
            if(!contents.contains(item)){
                return false;
            }
        }
        return true;
    }

    /**
     * Adds an InventoryItem to this package
     * @param item 
     */
    public boolean addItem(InventoryItem item){
        contents.add(item);
        return isFinished();
    }
}