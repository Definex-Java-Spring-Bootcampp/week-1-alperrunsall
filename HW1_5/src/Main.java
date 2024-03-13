import model.ProductType;
import model.Bill;
import model.Customer;
import model.Product;
import model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();
        products.add(new Product("Telefon", 1, 10000d, ProductType.ELECTRONIC));
        products.add(new Product("Kulaklık", 2, 650d, ProductType.ELECTRONIC));
        products.add(new Product("Gömlek", 100, 20.5d, ProductType.CLOTHING));
        products.add(new Product("Şort", 100, 99d, ProductType.CLOTHING));
        products.add(new Product("Ceket", 100, 500d, ProductType.CLOTHING));
        products.add(new Product("Ayakkabı", 100, 599.99d, ProductType.CLOTHING));
        products.add(new Product("Köpek Maması", 100, 700d, ProductType.PET));
        products.add(new Product("Mama Kabı", 100, 99.99d, ProductType.PET));
        products.add(new Product("Buzdolabı", 100, 15000d, ProductType.BeyazEsya));
        products.add(new Product("Fırın", 100, 5000d, ProductType.BeyazEsya));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Cem", "Bir", "cemdrman@gmail.com", 26));
        customers.add(new Customer("Alper", "Bir", "alperrunsall@gmail.com", 24));
        customers.add(new Customer("Alper", "iki", "alperrunsall@gmail.com", 40));
        customers.add(new Customer("Cem", "İki", "cemdrman@gmail.com", 30));
        customers.add(new Customer("Cem", "Üç", "cemdrman@gmail.com", 24));
        customers.add(new Customer("Alper", "Üç", "alperrunsall@gmail.com", 19));
        customers.add(new Customer("Cem", "Dört", "cemdrman@gmail.com", 28));

        List<Order> orders = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();

        int orderCount = 10;

        for (int i = 0; i < orderCount; i++) {
            Random random = new Random();
            Customer customer = customers.get(random.nextInt(customers.size()));

            int maxProductCount = 6;
            int productCount = random.nextInt(maxProductCount) + 1;

            double totalPrice = 0d;

            List<Product> productList = new ArrayList<>();
            for (int j = 1; j <= productCount; j++) {
                Product product = products.get(random.nextInt(products.size()));
                if (product.getStock() > 0) {
                    productList.add(product);
                    product.setStock(product.getStock() - 1);
                    totalPrice += product.getPrice();
                }
            }


            Bill bill = new Bill();

            bill.setAmount(totalPrice);
            bills.add(bill);

            Order newOrder = new Order(customer,
                    productList,
                    LocalDateTime.now(),
                    bill);

            orders.add(newOrder);
        }

        System.out.println("Müşteri Sayısı= " + customers.size());
        int cemProductCount = orders.stream()
                .filter(order -> "Cem".equals(order.getCustomer().getName()))
                .map(order -> order.getProductList())
                .mapToInt(list -> list.size())
                .sum();

        double cemProductAmountTotal = orders.stream()
                .filter(order -> "Cem".equals(order.getCustomer().getName()))
                .filter(order -> order.getCustomer().getAge() > 25)
                .filter(order -> order.getCustomer().getAge() < 30)
                .map(order -> order.getBill())
                .mapToDouble(bill -> bill.getAmount())
                .sum();

        System.out.println("İsmi Cem olanların aldıkları ürün sayısı= " + cemProductCount);
        System.out.println("İsmi Cem olan, yaşı 30'dan küçük 25'ten büyüklerin alışveriş tutarı= " + cemProductAmountTotal + "TL");

        bills.stream()
                .filter(bill -> bill.getAmount() > 1500)
                .forEach(bill -> System.out.println("\n******1500TL ÜZERİ FATURA******\n" + bill.getAmount()));

    }
}