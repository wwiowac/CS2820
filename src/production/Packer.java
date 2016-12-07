package production;

import java.awt.*;

/**
 * @author Jacob Guth
 */
public class Packer implements EventConsumer{

    private Master master;

    /**
     * @constructor
     * @param master
     */
    public Packer(Master master){
        this.master = master;
        //update cell types for visualizer
        for(int x=1; x<3; x++){
            for(int y=11; y<13; y++){
                master.floor.updateItemAt(new Point(x,y), Cell.Type.PACKER);
            }
        }
    }

    /**
     * @description: Print information about the bin/order that has been packaged
     * @param task
     * @param event
     */
    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch(task.type)
        {
            case OrderPacked:
                Order order = task.order;
                Customer customer = order.getCustomer();
                String name = customer.getName();
                System.out.println(name + "'s" + " order has been packaged and labeled");
        }
    }
}
