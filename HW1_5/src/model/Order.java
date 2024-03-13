package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Customer customer;
    private List<Product> productList;
    private LocalDateTime time;
    private Bill bill;

    public Order() {
    }

    public Order(Customer customer, List<Product> productList, LocalDateTime time, Bill bill) {
        this.customer = customer;
        this.productList = productList;
        this.time = time;
        this.bill = bill;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
