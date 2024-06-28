import java.util.ArrayList;

public class Buyer {
    private String name;
    private String buyerId;
    private double balance;
    private String password;
    private ArrayList<Product> cart;



    public Buyer(String name,String buyerId, double initialBalance, String password) {
        this.name = name;
        this.buyerId = buyerId;
        this.balance = initialBalance;
        this.password = password;
        this.cart = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }
    public void addToCart(Product product) {
        cart.add(product);
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    public ArrayList<Product> getCart() {
        return new ArrayList<>(cart);
    }
}
