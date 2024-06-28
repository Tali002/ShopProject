import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreManager {
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
        sortedList.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
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
