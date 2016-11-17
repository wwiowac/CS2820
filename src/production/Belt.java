package production; /**
 *@author Jacob Guth
 *
 * Updated 11/16/2106
 *
 * TODO: Implement updateStatus() that communicates to the production.Floor the content
 *       of the production.Belt
 */
import java.lang.*;
import java.util.HashMap;
import java.awt.*;

public class Belt implements EventConsumer{
    
    private Master master;
    private Floor floor;
    
    //picker and packer locations
    private final Point begin;	
    private final Point end;			

    private final Integer BELT_LENGTH;
    private final Integer BELT_WIDTH;
    private final Integer NUM_CELLS;

    
    private HashMap<String, BeltCell> cells;    //Maps each beltCell id to its object
    private HashMap<String, Package> packages;  //Maps the ID of each package on the belt to its object
    private HashMap<String, BeltCell> packageCells; //Maps each package on the belt to the beltCell it occupies
    private HashMap<Integer, Point> indexLocations; //Maps each index on the belt to its point location
    

    //booleans controller for belt movement
    private boolean canMove;

    /**
     * @constructor -  initializes a belt with a designated production.Master and production.Floor
     */
    public Belt(Master master, Floor floor){
        
        this.master = master;
        this.floor = floor;
        
        //Begin and end points of the belt as specified in FloorPlan.png
        this.begin = new Point(0,100);
        this.end = new Point(0,0);
        
        canMove = true;

        //production.Belt length depends on begin and end point. production.Belt width set to 20 as shown in FloorPlan.png, number of cells set to 50
        BELT_LENGTH = (int) Math.abs(end.getY() - begin.getY());
        BELT_WIDTH = 20;
        NUM_CELLS = 50;

        cells = new HashMap<>();
        packages = new HashMap<>();
        packageCells = new HashMap<>();
        indexLocations = new HashMap<>();

        initializeBelt();
    }

    /**
     * Increments the location of each beltCell on the belt if the belt can move
     */
    private void move(){
        if(canMove){
            for(BeltCell beltCell : cells.values() ){
                beltCell.incrementIndex();
            }
        }   
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
                Event spawnedEvent = new Event(new Task(Task.TaskType.MoveBelt), this);
                master.scheduleEvent(spawnedEvent,1);       
        }
    }

    /**
     * @description: starts belt movement
     */
    public void resume(){
        canMove = true;
    }
    /**
     * @description: stops belt movement
     */
    public void stop(){
        canMove = false;
    }


    /**
     * Adds a production.Package to the specified beltCell on the belt
     * @param inventoryPackage: a production.Package of InventoryItems
     * @param cell_id: String ID of the beltCell to be added to 
     */
    public void addPackage(Package inventoryPackage, String cell_id){
        String packageID = inventoryPackage.getID();
        
        cells.get(cell_id).addPackage(inventoryPackage);
        packages.put(packageID, inventoryPackage);
        packageCells.put(packageID, cells.get(cell_id));
    }
    
    /**
     * Removes and returns the production.Package specified by packageID from the belt
     * @param packageID: a production.Package of InventoryItems
     * @return: package identified by packageID to that was removed
     */
    public Package removePackage(String packageID){

        packages.remove(packageID);
        packageCells.remove(packageID);
        
        return packageCells.get(packageID).removePackage();
    }

    /**
     * @param packageID: string ID of a package 
     * @return: Point where @param inventoryPackage resides
     */
    public Point getPackageLocation(String packageID){
        if(!packageCells.containsKey(packageID)){
            System.out.println("production.Package "+packageID+"not found on the belt");
            return null;
        }else{
            return packageCells.get(packageID).getLocation();
        }
    }

    /**
     * Updates the current status of the production.Belt to the production.Floor
     *
     * NOTE: further implementation I believe will require implementation of an updateStatus in
     * production.Floor.java
     */
    public void updateStatus(){
    }

    
    /**
     *  Initialize the belt separated by beltCells. It will also initialize  
     *  indexLocations representing the location of each index. Will complete once 
     *  we decide on the length of the belt, the number of cells we want, etc. 
     */
    private void initializeBelt(){
        
        int cellHeight = BELT_LENGTH/NUM_CELLS;
        
        //define indexLocations with keys NUM_CELLS (50) indices mapped to values top-left corner Point locations
        for(int yLoc = 0, index = 0; yLoc < BELT_LENGTH-cellHeight; yLoc+= cellHeight, index++){
            indexLocations.put(index, new Point(0,yLoc));
        }
        
        //define cells with keys ID (same as starting index) mapped to the newly created cell
        for(int i = 0; i<=NUM_CELLS; i++){
            Point location = indexLocations.get(i);
            String ID = Integer.toString(i);
            BeltCell cell = new BeltCell(cellHeight, i, location, ID);
            cells.put(ID, cell);
        }
    }

    /**
    * production.Belt is comprised of beltCells, each with an index indicating its location on the belt and
    * an id. 
    *
    * @description: class production.Cell has methods that identify and locate the packages they are occupied with
    */
    private class BeltCell {
        
        private Point location;
        private int index;
  
        private final String ID;

        private final int width;
        private final int height;

        private boolean occupied;

        private Package inventoryPackage;

        /**
         * @constructor: initializes a BeltCell with a designated height, initial index, initial location, and ID
         *
         */
        public BeltCell(int height, int index, Point location, String ID){
            
            this.location = location;
            this.index = index;
            this.ID = ID;
            this.height = height;
            this.width = BELT_WIDTH;
            this.inventoryPackage = null;
            occupied = false;
        }

        public String getID(){
            return ID;
        }
        
        public Point getLocation(){
            return location;
        }
        
        public int getWidth(){
            return width;
        }
        
        public int getHeight(){
            return height;
        }
        
        /**
         * @param inventoryPackage: production.Package to be added to this production.Cell
         */
        public void addPackage(Package inventoryPackage){
            this.inventoryPackage = inventoryPackage;
            occupied = true;
        }
        
        /**
         * @return: production.Package that was removed from this beltCell
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
                System.out.println("production.Cell "+ID+"does not contain a package");
            }
            return null;
        }
        /**
         * Increment this BeltCells index by one; if it's at the end of the belt, move it to
         * the beginning
         */
        public void incrementIndex(){
            if(index == NUM_CELLS -1){
                index = 0;
            }else{
                index++;
            }
            updateLocation();
        }

         /**
          * Updates the location of this cell
          */
        public void updateLocation(){
            location = indexLocations.get(index);  
        }
    }
}