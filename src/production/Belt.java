package production; /**
 *@author Jacob Guth
 *
 *
 * TODO: Implement updateStatus() that communicates to the Floor the content of the Belt
 */
import java.lang.*;
import java.util.HashMap;
import java.awt.*;
import java.util.LinkedList;

public class Belt implements EventConsumer {
    
    private Master master;
    private Floor floor;

    private Point[] locations;
    
    private LinkedList<BeltCell> cells;    //Maps beltCells as ordered in
    

    //booleans controller for belt movement
    private int items;

    /**
     * @constructor -  initializes a belt with a designated Master and Floor
     */
    public Belt(Master master, Floor floor){
        
        this.master = master;
        this.floor = floor;

        cells = new LinkedList<>();

        locations = new Point[30];
        for (int i=0; i<30; i++) {
            locations[i] = new Point(0, 29 - i);
            cells.addFirst(new BeltCell());
        }
        floor.setBelt(locations);

        updateStatus();

        items = 0;
    }

    /**
     * Increments the location of each beltCell on the belt if the belt can move
     */
    private void move(){
        if (cells.removeLast().isOccupied()) {
            items--;
        }
        cells.addFirst(new BeltCell());
        updateStatus();
    }
    /**
     * @description: Queues 1 belt movement in master  
     * @param task
     * @param event
     */
    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case MoveBelt:
                move();
                if (items > 0) {
                    Event spawnedEvent = new Event(new Task(Task.TaskType.MoveBelt), this);
                    master.scheduleEvent(spawnedEvent, 1);
                }
                break;
            case ItemToBelt:
                task.robot.getShelf().removeItem(event.order.item);
                Package newpackage = new Package(event.order.item);
                addPackage(newpackage);
                master.scheduleEvent(event);
                break;
        }
    }


    /**
     * Adds a Package to the first beltCell on the belt
     * @param inventoryPackage: a Package of InventoryItems
     */
    public void addPackage(Package inventoryPackage){
        cells.get(0).addPackage(inventoryPackage);
        if (items == 0) {
            Event spawnedEvent = new Event(new Task(Task.TaskType.MoveBelt), this);
            master.scheduleEvent(spawnedEvent, 1);
        }
        items++;
        updateStatus();
    }

    /**
     * Updates the current status of the Belt to the Floor
     *
     * NOTE: further implementation I believe will require implementation of an updateStatus in
     * Floor.java
     */
    public void updateStatus() {
        for (int i=0; i<cells.size(); i++) {
            if (cells.get(i).isOccupied()) {
                floor.updateItemAt(locations[i], Cell.Type.OCCUPIEDBELT);
            } else {
                floor.updateItemAt(locations[i], Cell.Type.EMPTYBELT);
            }
        }
    }

    /**
    * Belt is comprised of beltCells, each with an index indicating its location on the belt and
    * an id. 
    *
    * @description: class Cell has methods that identify and locate the packages they are occupied with
    */
    private class BeltCell {

        private boolean occupied;

        private Package inventoryPackage;

        /**
         * @constructor: initializes a BeltCell with a designated height, initial index, initial location, and ID
         *
         */
        public BeltCell(){
        }
        
        /**
         * @param inventoryPackage: Package to be added to this Cell
         */
        public void addPackage(Package inventoryPackage){
            this.inventoryPackage = inventoryPackage;
            occupied = true;
        }
        
        /**
         * @return: Package that was removed from this beltCell
         */
        public Package removePackage(){
            Package temp = inventoryPackage;
            inventoryPackage = null;
            occupied = false;
            return temp;
        }

        public boolean isOccupied(){
            return occupied;
        }

        /**
         * @return: The package occupying this cell, if there is one; Prints  a message and returns null otherwise
         */
        public Package getPackage(){
            try{
                return inventoryPackage;
            }catch(Exception e){
                System.out.println("Cell does not contain a package");
            }
            return null;
        }
    }
}