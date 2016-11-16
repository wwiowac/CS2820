/*
*@author Jacob Guth
*
* Rough draft of Belt.java 
*
* Updated 11/16/2106
*
* TODO: implement EventConsumerInterface
*/
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.*;

public class Belt {

    private Floor floor;
    
    //picker and packer locations
    private final Point begin;	
    private final Point end;			

    private Integer currentTime;

    private final Integer BELT_LENGTH;
    private final Integer BELT_WIDTH;
    private final Integer NUM_CELLS;

    //Maps each beltCell id to its object
    private HashMap<String, BeltCell> cells;
    //Maps the ID of each package on the belt to its object
    private HashMap<String, Package> packages;
    //Maps each package on the belt to the beltCell it occupies
    private HashMap<String, BeltCell> packageCells;
    //Maps each index on the belt to its point location
    private HashMap<Integer, Point> indexLocations;
    

    //booleans controller for belt movement
    private boolean canMove;

    /*
    * constructor -  initializes a belt with a designated Floor layout
    * 
    */
    public Belt(Floor floor){

        this.floor = floor;
        
        //
        this.begin = new Point(0,100);
        this.end = new Point(100,0);

        currentTime = 0;
        canMove = true;

        //Belt length depends on begin and end point. Belt width set to 20 (as shown in FloorPlan.png), and number of cells set to 50
        BELT_LENGTH = (int) Math.abs(end.getY() - begin.getY());
        BELT_WIDTH = 20;
        NUM_CELLS = 50;


        cells = new HashMap<>();
        packages = new HashMap<>();
        packageCells = new HashMap<>();

        initializeBelt();
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
    *@param inventoryPackage: a Package of InventoryItems
    *
    * description: adds a Package to the specified beltCell on the belt
    */
    public void addPackage(Package inventoryPackage, String cell_id){
        String packageID = inventoryPackage.getID();
        
        cells.get(cell_id).addPackage(inventoryPackage);
        packages.put(packageID, inventoryPackage);
        packageCells.put(packageID, cells.get(cell_id));
    }
    
    /*
    * @param inventoryPackage: a Package of InventoryItems
    * 
    * description: removes and returns the Package specified by packageID from the belt
    */
    public Package removePackage(String packageID){

        packages.remove(packageID);
        packageCells.remove(packageID);
        
        return packageCells.get(packageID).removePackage();
    }


    /*
    * @param id: a cell id
    * @return: Cell object associate with @param id
    */
    public BeltCell getCell(String id){
        return cells.get(id);
    }


    /*
    *@param packageID: string ID of a package 
    *@return: Point where @param inventoryPackage resides
    */
    public Point getPackageLocation(String packageID){
        if(!packageCells.containsKey(packageID)){
            System.out.println("Package "+packageID+"not found on the belt");
            return null;
        }else{
            return packageCells.get(packageID).getLocation();
        }
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
    *description: increments the location of each beltCell on the belt
    */
    private void updateLocations(){
        for(BeltCell beltCell : cells.values() ){
            beltCell.incrementIndex();
        }
    }


    /*
    * description: This method will initialize the belt separated by beltCells. It will also initialize  
    * indexLocations representing the location of each index. Will complete once 
    * we decide on the length of the belt, the number of cells we want, etc. 
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

    /*
    *Belt is comprised of beltCells, each with an index indicating its location on the belt and
    *an id. 
    *
    * description: class Cell has methods that identify and locate the packages they are occupied with
    */
    private class BeltCell {
        
        private Point location;
        private int index;
  
        private final String ID;

        private final int width;
        private final int height;

        private boolean occupied;

        private Package inventoryPackage;

        /*
        * Constructor: initializes a cell with a designated height, initial index, initial location, and ID
        *
        */
        public BeltCell(int height, int index, Point location, String ID){
            
            this.location = location;
            this.index = index;
            this.height = height;
            this.ID = ID;
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
        /*
        * @param package: Package to be added to this Cell
        */
        public void addPackage(Package inventoryPackage){
            this.inventoryPackage = inventoryPackage;
            occupied = true;
        }

        /*
        * description: removes and returns the package from this cell
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

        /*
        * @return: returns the package occupying this cell, if there is one; Prints  a message otherwise
        */
        public Package getPackage(){
            try{
                return inventoryPackage;
            }catch(Exception e){
                System.out.println("Cell "+ID+"does not contain a package");
            }
            return null;
        }
        /*
        * description: increment this cell's index by one; if it's at the end of the belt, move it to
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

         /*
         * description: updates the location of this cell
         */
        public void updateLocation(){
            location = indexLocations.get(index);  
        }
    }
}