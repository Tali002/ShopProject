import java.util.ArrayList;

public interface Cartable {
    void addToCart(Product product);
    ArrayList<Product> getCart();
}