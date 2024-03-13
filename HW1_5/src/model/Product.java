package model;

public class Product {
    private String name;
    private int stock;
    private double price;
    private ProductType type;

    public Product() {
    }

    public Product(String name, int stock, double price, ProductType type) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }
}
