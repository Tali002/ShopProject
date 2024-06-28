import java.util.*;

class Product {
    private String name;
    private double price;
    private String image;
    private String category;
    private double rating;

    public Product(String name, double price, String image, String category, double rating) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.category = category;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                '}';
    }

}

class StoreManager {
    private ArrayList<Product> products;
    private Map<String, String> userCredentials;

    public StoreManager() {
        this.products = new ArrayList<>();
        this.userCredentials = new HashMap<>();

    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public ArrayList<Product> searchProducts(String query) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(query)) {
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> sortByPrice() {
        ArrayList<Product> sortedList = new ArrayList<>(products);
        sortedList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        return sortedList;
    }

    public ArrayList<Product> sortByRating() {
        ArrayList<Product> sortedList = new ArrayList<>(products);
        sortedList.sort((p1, p2) -> Double.compare(p2.getRating(), p1.getRating()));
        return sortedList;
    }
    public void registerUser(String username, String hashedPassword) {
        userCredentials.put(username, hashedPassword);
    }

    public boolean verifyUserCredentials(String username, String hashedPassword) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(hashedPassword);
    }
}

class Main {
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
class Buyer {
    private String buyerId;
    private double balance;
    private String password;

    public Buyer(String buyerId, double initialBalance, String password) {
        this.buyerId = buyerId;
        this.balance = initialBalance;
        this.password = password;
    }


    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }}
class ProductDisplayPage {
    private ArrayList<Product> products;

    public ProductDisplayPage() {
        this.products = new ArrayList<>();
    }

    public void displayProducts() {
        for (Product product : products) {
            System.out.println(product.getName() + " - Price: $" + product.getPrice());
        }
    }

    public void searchProducts(String keyword) {
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(product.getName() + " - Price: $" + product.getPrice());
            }
        }
    }

    public void sortProductsByPrice() {
        products.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
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

