package Inventory;

public class InventoryItem{
    private String id;
    private String name;
    private float price;
    private MockShelf shelfID;

    public InventoryItem(String id, String name, float price, MockShelf shelfID){
        this.id = id;
        this.name = name;
        this.price = price;
        this.shelfID = shelfID;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    //only getter needed because item won't move once placed on shelf until removed
    public MockShelf getShelfID() {
        return shelfID;
    }
}