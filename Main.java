import java.util.*;

public class Main {
    public static void main(String[] args) {
        Buyer buyer = new Buyer("B123", "John Doe", 500.0, "mypassword");

        StoreManager storeManager = new StoreManager();
        storeManager.addProduct(new Product("Laptop", 1000.0, "laptop.jpg", "Electronics", 4.5));
        storeManager.addProduct(new Product("Phone", 800.0, "phone.jpg", "Electronics", 4.2));

        UserPanel userPanel = new UserPanel(buyer);
        userPanel.addProduct(storeManager.searchProducts("Laptop").get(0));
        userPanel.addProduct(storeManager.searchProducts("Phone").get(0));

        userPanel.displayUserProfile();

        userPanel.addToCart(storeManager.searchProducts("Laptop").get(0));
        userPanel.addToCart(storeManager.searchProducts("Phone").get(0));

        userPanel.displayCart();
        userPanel.displayProducts();

        ArrayList<Product> searchedProducts = userPanel.searchProducts("Phone");
        System.out.println("Searched products (keyword: 'Phone'): " + searchedProducts);

        userPanel.sortProductsByPrice();
        System.out.println("Products sorted by price:");
        userPanel.displayProducts();

        userPanel.setRating(searchedProducts.get(0), 4.0);

        userPanel.contactSeller();
    }
}
