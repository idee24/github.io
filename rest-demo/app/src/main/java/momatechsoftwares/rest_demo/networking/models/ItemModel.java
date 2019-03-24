package momatechsoftwares.rest_demo.networking.models;

public class ItemModel {

    private String itemName;
    private String itemSize;

    public ItemModel() {
    }

    public ItemModel(String itemName, String itemSize) {
        this.itemName = itemName;
        this.itemSize = itemSize;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "itemName='" + itemName + '\'' +
                ", itemSize='" + itemSize + '\'' +
                '}';
    }
}
