public class Product {
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