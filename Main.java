import java.util.*;

public class Main {
    public static void main(String[] args) {
        StoreManager storeManager = new StoreManager();
        storeManager.addProduct(new Product("Laptop", 1000.0, "laptop.jpg", "Electronics", 4.5));
        storeManager.addProduct(new Product("Phone", 800.0, "phone.jpg", "Electronics", 4.2));

        ArrayList<Product> searchedProducts = storeManager.searchProducts("Laptop");
        System.out.println("Searched products: " + searchedProducts);

        ArrayList<Product> sortedByPrice = storeManager.sortByPrice();
        System.out.println("Products sorted by price: " + sortedByPrice);

        ArrayList<Product> sortedByRating = storeManager.sortByRating();
        System.out.println("Products sorted by rating: " + sortedByRating);

        ProductDisplayPage displayPage = new ProductDisplayPage();
        displayPage.addProduct(new Product("Tablet", 600.0, "tablet.jpg", "Electronics", 4.0));
        displayPage.addProduct(new Product("Headphones", 150.0, "headphones.jpg", "Electronics", 4.8));

        System.out.println("All products:");
        displayPage.displayProducts();

        System.out.println("Searched products (keyword: 'Headphones'):");
        displayPage.searchProducts("Headphones");

        displayPage.sortProductsByPrice();
        System.out.println("Products sorted by price:"); // برعکس سسورت میکنه
        displayPage.displayProducts();

        displayPage.goToCartPanel();
        displayPage.goToUserProfilePanel();
    }
}



