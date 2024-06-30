import java.util.ArrayList;

public class UserPanel {
    private ArrayList<Product> products;
    private Buyer user;

    public UserPanel(Buyer user) {
        this.products = new ArrayList<>();
        this.user = user;
    }
}
