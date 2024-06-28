import java.util.ArrayList;

public class ProductDisplayPage {
    private ArrayList<Product> products;

    public ProductDisplayPage() {
        this.products = new ArrayList<>();
    }

    public void displayProducts() { // میشه بسطش داد!
        for (Product product : products) {
            System.out.println(product.getName() + " - Price: $" + product.getPrice());
        }
    }
    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void searchProducts(String keyword) {
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(product.getName() + " - Price: $" + product.getPrice());
            }
        }
    }

    public void sortProductsByPrice() {
        products.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void goToCartPanel() {
        System.out.println("Navigating to cart panel...");
    }

    public void goToUserProfilePanel() {
        System.out.println("Navigating to user profile panel...");
    }
}
