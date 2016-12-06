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
        Customer customer = generateCustomer();
        generateOrderItems();

    }

    public ArrayList<InventoryItem> getOrdersItems() {
        return ordersItems;
    }


    private Customer generateCustomer(){
        return CustomerList.RandomCustomer();
    }

    private void generateOrderItems(){
        for(int i = 0; i < new Random().nextInt(5); i++) {
            InventoryItem item = InventoryList.catalog[new Random().nextInt(InventoryList.catalog.length)];
            ordersItems.add(item);
        }
    }
}
