import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Buyer implements Cartable {
    private String name;
    private String buyerId;
    private double balance;
    private String password;
    private ArrayList<Product> cart;
    private String address;



    public Buyer(String name,String buyerId, double initialBalance, String password, String address) {
        this.name = name;
        this.buyerId = buyerId;
        this.balance = initialBalance;
        this.password = password;
        this.cart = new ArrayList<>();
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuyerId(String buyerId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(buyerId.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            this.buyerId = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    public void setPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        this.password = password;
    }

    public void setAddress(String Address) {
        this.address = address;
    }
    public void deductBalance(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance for purchase!");
        }
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
    public void removeAll() {
        cart.clear();
    }
}

