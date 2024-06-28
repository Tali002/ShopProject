import java.util.ArrayList;

public class UserPanel {
    private ArrayList<Product> products;
    private Buyer user;

    public UserPanel(Buyer user) {
        this.products = new ArrayList<>();
        this.user = user;
    }

    public void displayUserProfile() {
        System.out.println("User Profile:");
        System.out.println("Username: " + user.getName());
        System.out.println("Balance: $" + user.getBalance());
    }

    public void displayCart() {
        System.out.println("Cart Items:");
        for (Product product : user.getCart()) {
            System.out.println(product.getName() + " - Price: $" + product.getPrice());
        }
    }

    public void displayProducts() {
        System.out.println("All Products:");
        for (Product product : products) {
            System.out.println(product.getName() + " - Price: $" + product.getPrice());
        }
    }

    public ArrayList<Product> searchProducts(String keyword) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    public void sortProductsByPrice() {
        products.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
    }

    public void rateProduct(Product product, double rating) {
        if (rating >= 1.0 && rating <= 5.0) {
            product.setRating(rating);
            System.out.println("Product '" + product.getName() + "' rated with " + rating + " stars.");
        } else {
            System.out.println("Invalid rating! Please provide a rating between 1.0 and 5.0.");
        }
    }

    public void contactSeller() {
        System.out.println("Contacting the seller. Please wait...");
    }
}
