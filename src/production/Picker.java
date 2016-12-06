package production;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kyle on 12/5/16.
 */
public class Picker implements EventConsumer{
    private Master master;
    private Bin bin;
    private ArrayList<Bin> binList = new ArrayList<>();
    final private Point initialBinLocation = new Point(0, 28);
    final private Point dropoffLocation = new Point(3, 28);


    public Picker(Master master){
        this.master = master;
        for(int x=1; x<3; x++){
            for(int y= 27; y<29; y++){
                master.floor.updateItemAt(new Point(x,y), Cell.Type.PICKER);
            }
        }
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type){
            case  PickItemFromShelf:
                Bin b = addItemToBin(task.item);
                if(b != null) {
                    event.addFirstTask(new Task(Task.TaskType.AddBinToBelt, initialBinLocation, b), master.belt);
                }

                master.scheduleEvent(event);
                break;
            case CreateBinForOrder:
                //create new bin associated with a specific order
                Bin newBin = new Bin(task.order);
                binList.add(newBin);

//                master.scheduleEvent(event, 1);
                break;
        }


    }

    /**
     * Returns the bin if the bin is ready to be put on the bin
     * @param item
     * @return
     */
    private Bin addItemToBin(InventoryItem item) {
        for (Bin bin : binList) {
            if (bin.getOrder().getOrdersItems().contains(item)) {
                boolean result = bin.addItem(item);
                if(result)
                    return bin;
                else
                    return null;
            }
        }
        return null;
    }

    public Point getDropoffLocation() {
        return dropoffLocation;
    }
}
