public class Buyer {
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
    }
}