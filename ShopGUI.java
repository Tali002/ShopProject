import javax.swing.*;
import java.awt.*;

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

        mainPanel = new JPanel();
        signInPanel = new JPanel();
        signUpPanel = new JPanel();
        managerSignInPanel = new JPanel();
        cartPanel = new JPanel();

        frame.add(mainPanel, "Main");
        frame.add(signInPanel, "SignIn");
        frame.add(signUpPanel, "SignUp");
        frame.add(managerSignInPanel, "ManagerSignIn");
        frame.add(cartPanel, "Cart");

        storeManager = new StoreManager();

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

        JPanel productDisplayPanel = new JPanel();
        productDisplayPanel.setLayout(new GridLayout(0, 1));

        Product product1 = new Product("Product 1", 100, "image1.jpg", "Category 1", 4.5);
        Product product2 = new Product("Product 2", 200, "image2.jpg", "Category 2", 3.8);
        storeManager.addProduct(product1);
        storeManager.addProduct(product2);

        for (Product product : storeManager.getAllProducts()) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new GridLayout(1, 4));
            productPanel.add(new JLabel(product.getName()));
            productPanel.add(new JLabel("$" + product.getPrice()));
            productPanel.add(new JLabel(new ImageIcon(product.getImage())));
            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.addActionListener(e -> {
                if (currentBuyer != null) {
                    currentBuyer.addToCart(product);
                    JOptionPane.showMessageDialog(frame, "Added to cart!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please sign in first!");
                }
            });
            productPanel.add(addToCartButton);
            productDisplayPanel.add(productPanel);
        }

        mainPanel.add(productDisplayPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "SignIn"));
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "SignUp"));
        JButton managerSignInButton = new JButton("Manager Sign In");
        managerSignInButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "ManagerSignIn"));
        JButton cartButton = new JButton("Cart");
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

        mainPanel.add(controlPanel, BorderLayout.NORTH);
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
            cartItemsPanel.setLayout(new GridLayout(0, 1));
            for (Product product : currentBuyer.getCart()) {
                cartItemsPanel.add(new JLabel(product.getName() + " - $" + product.getPrice()));
            }

            cartPanel.add(cartItemsPanel, BorderLayout.CENTER);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));

        cartPanel.add(backButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShopGUI::new);
    }
}
