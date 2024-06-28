import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ShopGUI {
    private JFrame frame;
    private JPanel mainPanel, signInPanel, signUpPanel, managerSignInPanel, cartPanel;
    private CardLayout cardLayout;
    private Buyer currentBuyer;
    private UserPanel userPanel;
    private StoreManager storeManager;

    public ShopGUI() {
        frame = new JFrame("Shop");
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        mainPanel = new JPanel(new BorderLayout());
        signInPanel = new JPanel();
        signUpPanel = new JPanel();
        managerSignInPanel = new JPanel();
        cartPanel = new JPanel();
        storeManager = new StoreManager();


        frame.add(mainPanel, "Main");
        frame.add(signInPanel, "SignIn");
        frame.add(signUpPanel, "SignUp");
        frame.add(managerSignInPanel, "ManagerSignIn");
        frame.add(cartPanel, "Cart");


        setupMainPanel();
        setupSignInPanel();
        setupSignUpPanel();
        setupManagerSignInPanel();
        setupCartPanel();

        cardLayout.show(frame.getContentPane(), "Main");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void setupMainPanel() {


        mainPanel.setLayout(new BorderLayout());

        JPanel productDisplayPanel = createProductDisplayPanel();
        JPanel controlPanel = createControlPanel();

        mainPanel.add(productDisplayPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.NORTH);
    }

    private JPanel createProductDisplayPanel() {
        JPanel productDisplayPanel = new JPanel();
        productDisplayPanel.setLayout(new GridLayout(0, 2, 10, 10));

        Product product1 = new Product("Product 1", 100, "C:\\Users\\alito\\IdeaProjects\\ShopProject\\src\\image1", "Category 1", 4.5);
        Product product2 = new Product("Product 2", 200, "image2.jpg", "Category 2", 3.8);
        Product product3 = new Product("Product 3", 220, "image1.jpg", "Category 3", 3.3);
        storeManager.addProduct(product1);
        storeManager.addProduct(product2);
        storeManager.addProduct(product3);
        for (Product product : storeManager.getAllProducts()) {
            JPanel productPanel = createProductPanel(product);
            productDisplayPanel.add(productPanel);
        }

        return productDisplayPanel;
    }
    private JPanel createProductPanel(Product product) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("$" + product.getPrice());
        priceLabel.setForeground(Color.BLUE);

        JLabel imageLabel = new JLabel(new ImageIcon(product.getImage()));

        JPanel ratingPanel = new JPanel();
        for (int i = 0; i < 5; i++) {
            JLabel starLabel = new JLabel("\u2605");
            starLabel.setForeground(i < product.getRating()-.49 ? Color.YELLOW : Color.LIGHT_GRAY);
            ratingPanel.add(starLabel);
        }

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> handleAddToCart(product));

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(priceLabel, BorderLayout.SOUTH);

        productPanel.add(imageLabel);
        productPanel.add(infoPanel);
        productPanel.add(ratingPanel);
        productPanel.add(addToCartButton);

        productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        return productPanel;
    }




    private void handleAddToCart(Product product) {
        if (currentBuyer != null) {
            currentBuyer.addToCart(product);
            JOptionPane.showMessageDialog(frame, "Added to cart!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please sign in first!");
        }
    }
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        JButton signInButton = new JButton("Sign In");
        JButton signUpButton = new JButton("Sign Up");
        JButton managerSignInButton = new JButton("Manager Sign In");
        JButton cartButton = new JButton("Cart");

        signInButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "SignIn"));
        signUpButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "SignUp"));
        managerSignInButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "ManagerSignIn"));
        cartButton.addActionListener(e -> {
            if (currentBuyer != null) {
                cardLayout.show(frame.getContentPane(), "Cart");
                setupCartPanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Please sign in first!");
            }
        });

        controlPanel.add(signInButton);
        controlPanel.add(signUpButton);
        controlPanel.add(managerSignInButton);
        controlPanel.add(cartButton);

        return controlPanel;
    }
    private void setupSignInPanel() {
        signInPanel.setLayout(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton signInButton = new JButton("Sign In");
        JButton backButton = new JButton("Back");

        signInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (storeManager.verifyUserCredentials(username, password)) {
                currentBuyer = new Buyer(username, "buyerId", 1000, password);
                userPanel = new UserPanel(currentBuyer);
                JOptionPane.showMessageDialog(frame, "Sign in successful!");
                cardLayout.show(frame.getContentPane(), "Main");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));

        signInPanel.add(new JLabel("Username:"));
        signInPanel.add(usernameField);
        signInPanel.add(new JLabel("Password:"));
        signInPanel.add(passwordField);
        signInPanel.add(signInButton);
        signInPanel.add(backButton);
    }

    private void setupSignUpPanel() {
        signUpPanel.setLayout(new GridLayout(4, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton signUpButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back");

        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            storeManager.registerUser(username, password);
            JOptionPane.showMessageDialog(frame, "Sign up successful! Please sign in.");
            cardLayout.show(frame.getContentPane(), "SignIn");
        });

        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));

        signUpPanel.add(new JLabel("Username:"));
        signUpPanel.add(usernameField);
        signUpPanel.add(new JLabel("Password:"));
        signUpPanel.add(passwordField);
        signUpPanel.add(signUpButton);
        signUpPanel.add(backButton);
    }

    private void setupManagerSignInPanel() {
        managerSignInPanel.setLayout(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton signInButton = new JButton("Sign In");
        JButton backButton = new JButton("Back");

        signInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if ("manager".equals(username) && "managerpass".equals(password)) {
                JOptionPane.showMessageDialog(frame, "Manager sign in successful!");
                cardLayout.show(frame.getContentPane(), "Main");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));

        managerSignInPanel.add(new JLabel("Username:"));
        managerSignInPanel.add(usernameField);
        managerSignInPanel.add(new JLabel("Password:"));
        managerSignInPanel.add(passwordField);
        managerSignInPanel.add(signInButton);
        managerSignInPanel.add(backButton);
    }

    private void setupCartPanel() {
        cartPanel.removeAll();
        cartPanel.setLayout(new BorderLayout());

        if (currentBuyer != null) {
            JPanel cartItemsPanel = new JPanel();
            cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
            JLabel titleLabel = new JLabel("Your Cart");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

            for (Product product : currentBuyer.getCart()) {
                JLabel itemLabel = new JLabel(product.getName() + " - $" + product.getPrice());
                cartItemsPanel.add(itemLabel);
            }
            JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            cartPanel.add(titleLabel, BorderLayout.NORTH);
            cartPanel.add(scrollPane, BorderLayout.CENTER);
        }
        else {
            JLabel emptyCartLabel = new JLabel("Your cart is empty.");
            cartPanel.add(emptyCartLabel, BorderLayout.CENTER);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));
        backButton.setBackground(Color.LIGHT_GRAY);

        cartPanel.add(backButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShopGUI::new);
    }
}
