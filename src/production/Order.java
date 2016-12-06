package production;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kyle on 12/5/16.
 */
public class Order {
    private Customer customer;
    private ArrayList<InventoryItem> ordersItems = new ArrayList<InventoryItem>();

    public Order(){
        customer = CustomerList.RandomCustomer();
        generateOrderItems();

    }

    public ArrayList<InventoryItem> getOrdersItems() {
        return ordersItems;
    }

    private void generateOrderItems(){
        for(int i = 0; i < new Random().nextInt(4) + 1; i++) {
            InventoryItem item = InventoryList.catalog[new Random().nextInt(InventoryList.catalog.length)];
            ordersItems.add(item);
        }
    }


}
