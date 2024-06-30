public class Product {
    private String name;
    private double price;
    private String image;
    private String category;
    private double rating;
    private int quantity;


    public Product(String name, double price, String image, String category, double rating,int quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.category = category;
        this.rating = rating;
        this.quantity = quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
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
