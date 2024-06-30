import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class ShopGUI {
    private JFrame frame;
    private JPanel mainPanel, signInPanel, signUpPanel, managerSignInPanel, cartPanel, profilePanel, editProfilePanel, addBalancePanel;
    private CardLayout cardLayout;
    private Buyer currentBuyer;
    private UserPanel userPanel;
    private StoreManager storeManager;
    private JPanel productDisplayPanel;

    public ShopGUI() {
        frame = new JFrame("Shop");
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        // Initialize panels
        mainPanel = new JPanel();
        signInPanel = new JPanel();
        signUpPanel = new JPanel();
        managerSignInPanel = new JPanel();
        cartPanel = new JPanel();
        profilePanel = new JPanel();
        editProfilePanel = new JPanel();
        addBalancePanel = new JPanel();

        // Add panels to frame
        frame.add(mainPanel, "Main");
        frame.add(signInPanel, "SignIn");
        frame.add(signUpPanel, "SignUp");
        frame.add(managerSignInPanel, "ManagerSignIn");
        frame.add(cartPanel, "Cart");
        frame.add(profilePanel, "Profile");
        frame.add(editProfilePanel, "EditProfile");
        frame.add(addBalancePanel, "AddBalance");

        // Initialize store manager
        storeManager = new StoreManager();

        // Setup main panel
        setupMainPanel();

        // Setup sign in panel
        setupSignInPanel();

        // Setup sign up panel
        setupSignUpPanel();

        // Setup manager sign in panel
        setupManagerSignInPanel();

        // Setup cart panel
        setupCartPanel();

        // Setup profile panel
        setupProfilePanel();

        // Setup edit profile panel
        setupEditProfilePanel();

        // Setup add balance panel
        setupAddBalancePanel();

        // Display main panel
        cardLayout.show(frame.getContentPane(), "Main");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void setupMainPanel() {
        mainPanel.setLayout(new BorderLayout());

        // Create product display area
        productDisplayPanel = new JPanel();
        productDisplayPanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(productDisplayPanel);

        Product product1 = new Product("Product 1", 100, "image1.jpg", "Category 1", 4.5);
        Product product2 = new Product("Product 2", 200, "image2.jpg", "Category 2", 3.8);
        Product product3 = new Product("Product 3", 180, "image3.jpg", "Category 3", 3.3);

        storeManager.addProduct(product1);
        storeManager.addProduct(product2);
        storeManager.addProduct(product3);

        displayProducts(storeManager.getAllProducts());

        JPanel controlPanel = createControlPanel();

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.EAST);
    }
    private void displayProducts(ArrayList<Product> products) {
        productDisplayPanel.removeAll();
        for (Product product : products) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();

            // Product image

            ClassLoader classLoader = getClass().getClassLoader();
            URL imageURL = classLoader.getResource(product.getImage());
            ImageIcon imageIcon = null;
            if (imageURL != null) {
                imageIcon = new ImageIcon(imageURL);
            } else {
                System.err.println("Warning: Image not found - " + product.getImage());
            }

            // Product image (check if image loaded)
            JLabel imageLabel = null;
            if (imageIcon != null) {
                imageLabel = new JLabel(imageIcon);
            } else {
                imageLabel = new JLabel("Image unavailable");
            }
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            productPanel.add(imageLabel, c);


            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BorderLayout());

            JLabel nameLabel = new JLabel(product.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            detailsPanel.add(nameLabel, BorderLayout.NORTH);


            JLabel priceLabel = new JLabel("$" + product.getPrice());
            if (product.getPrice() < 100) {
                priceLabel.setForeground(Color.GREEN);
            } else if (product.getPrice() > 200) {
                priceLabel.setForeground(Color.RED);
            } else {
                priceLabel.setForeground(Color.BLACK);
            }
            detailsPanel.add(priceLabel, BorderLayout.CENTER);


            JPanel ratingPanel = createRatingStars(product.getRating());
            detailsPanel.add(ratingPanel, BorderLayout.SOUTH);

            c.gridx = 1;
            c.gridy = 0;
            productPanel.add(detailsPanel, c);


            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.addActionListener(e -> {
                if (currentBuyer != null) {
                    currentBuyer.addToCart(product);
                    JOptionPane.showMessageDialog(frame, "Added to cart!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please sign in first!");
                }
            });
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.EAST;
            c.weightx = 1.0;
            c.gridx = 0;
            c.gridy = 1;
            productPanel.add(addToCartButton, c);

            productDisplayPanel.add(productPanel);
        }
        productDisplayPanel.revalidate();
        productDisplayPanel.repaint();
    }

    private JPanel createRatingStars(double rating) {
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        for (int i = 0; i < Math.floor(rating); i++) {
            JLabel starLabel = new JLabel("\u2605"); // Unicode star symbol
            starLabel.setForeground(Color.YELLOW);
            ratingPanel.add(starLabel);
        }
        if (rating - Math.floor(rating) > 0) {
            JLabel halfStarLabel = new JLabel("\u260A"); // Unicode half star symbol
            halfStarLabel.setForeground(Color.YELLOW);
            ratingPanel.add(halfStarLabel);
        }
        return ratingPanel;
    }


    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(4, 2));

        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "SignIn"));

        JButton signUpButton = new JButton("Create Account");
        signUpButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "SignUp"));

        JButton managerSignInButton = new JButton("Manager Sign In");
        managerSignInButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "ManagerSignIn"));

        JButton cartButton = new JButton("View Cart");
        cartButton.addActionListener(e -> {
            if (currentBuyer != null) {
                cardLayout.show(frame.getContentPane(), "Cart");
                setupCartPanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Please sign in first!");
            }
        });

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText();
            displayProducts(storeManager.searchProducts(keyword));
        });

        JButton sortPButton = new JButton("Sort by Price");
        sortPButton.addActionListener(e -> displayProducts(storeManager.sortByPrice()));

        JButton sortRButton = new JButton("Sort by Rating");
        sortRButton.addActionListener(e -> displayProducts(storeManager.sortByRating()));

        controlPanel.add(signInButton);
        controlPanel.add(signUpButton);
        controlPanel.add(managerSignInButton);
        controlPanel.add(cartButton);
        controlPanel.add(sortPButton);
        controlPanel.add(sortRButton);
        controlPanel.add(searchButton);
        controlPanel.add(searchField);

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
                currentBuyer = new Buyer(username, "buyerId", 1000, "pass","add"); // Simplified for example
                userPanel = new UserPanel(currentBuyer);
                JOptionPane.showMessageDialog(frame, "Sign in successful!");
                cardLayout.show(frame.getContentPane(), "Profile");
                setupProfilePanel();
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
            // Simplified for example, normally you would have a separate check for managers
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
            JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
            cartPanel.add(scrollPane, BorderLayout.CENTER);

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));
            cartPanel.add(backButton, BorderLayout.SOUTH);
        }
//        else {
//            JOptionPane.showMessageDialog(frame, "Please sign in first!");
//            cardLayout.show(frame.getContentPane(), "Main");
//        }
    }

    private void setupProfilePanel() {
        profilePanel.removeAll();
        profilePanel.setLayout(new GridLayout(8, 2));

        if (currentBuyer != null) {
            profilePanel.add(new JLabel("Username:"));
            profilePanel.add(new JLabel(currentBuyer.getName()));

            profilePanel.add(new JLabel("Name:"));
            profilePanel.add(new JLabel(currentBuyer.getName()));

            profilePanel.add(new JLabel("Buyer ID:"));
            profilePanel.add(new JLabel(currentBuyer.getBuyerId()));

            profilePanel.add(new JLabel("Balance:"));
            profilePanel.add(new JLabel(String.valueOf(currentBuyer.getBalance())));

            JButton editProfileButton = new JButton("Edit Profile");
            editProfileButton.addActionListener(e -> {
                setupEditProfilePanel();
                cardLayout.show(frame.getContentPane(), "EditProfile");
            });

            JButton addBalanceButton = new JButton("Add Balance");
            addBalanceButton.addActionListener(e -> {
                setupAddBalancePanel();
                cardLayout.show(frame.getContentPane(), "AddBalance");
            });

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));

            profilePanel.add(editProfileButton);
            profilePanel.add(addBalanceButton);
            profilePanel.add(backButton);
        }
//        else {
//            JOptionPane.showMessageDialog(frame, "Please sign in first!");
//            cardLayout.show(frame.getContentPane(), "Main");
//        }
    }

    private void setupEditProfilePanel() {
        editProfilePanel.removeAll();
        editProfilePanel.setLayout(new GridLayout(5, 2));

        if (currentBuyer != null) {
            JTextField nameField = new JTextField(currentBuyer.getName());
            JTextField buyerIdField = new JTextField(currentBuyer.getBuyerId());
            JPasswordField passwordField = new JPasswordField(currentBuyer.getPassword());

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> {
                currentBuyer.setName(nameField.getText());
                currentBuyer.setBuyerId(buyerIdField.getText());
                currentBuyer.setPassword(new String(passwordField.getPassword()));
                JOptionPane.showMessageDialog(frame, "Profile updated!");
                cardLayout.show(frame.getContentPane(), "Profile");
            });

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Profile"));

            editProfilePanel.add(new JLabel("Name:"));
            editProfilePanel.add(nameField);
            editProfilePanel.add(new JLabel("Buyer ID:"));
            editProfilePanel.add(buyerIdField);
            editProfilePanel.add(new JLabel("Password:"));
            editProfilePanel.add(passwordField);
            editProfilePanel.add(saveButton);
            editProfilePanel.add(backButton);
        }
//        else {
//            JOptionPane.showMessageDialog(frame, "Please sign in first!");
//            cardLayout.show(frame.getContentPane(), "Main");
//        }
    }

    private void setupAddBalancePanel() {
        addBalancePanel.removeAll();
        addBalancePanel.setLayout(new GridLayout(4, 2));

        if (currentBuyer != null) {
            JTextField cardNumberField = new JTextField();
            JPasswordField cardPasswordField = new JPasswordField();
            JTextField amountField = new JTextField();

            JButton addButton = new JButton("Add");
            addButton.addActionListener(e -> {
                double amount = Double.parseDouble(amountField.getText());
                if (isValidAmount(amount)) {currentBuyer.addBalance(amount);}
                JOptionPane.showMessageDialog(frame, "Balance added!");
                cardLayout.show(frame.getContentPane(), "Profile");
            });

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Profile"));

            addBalancePanel.add(new JLabel("Card Number:"));
            addBalancePanel.add(cardNumberField);
            addBalancePanel.add(new JLabel("Card Password:"));
            addBalancePanel.add(cardPasswordField);
            addBalancePanel.add(new JLabel("Amount:"));
            addBalancePanel.add(amountField);
            addBalancePanel.add(addButton);
            addBalancePanel.add(backButton);
        }
//        else {
//            JOptionPane.showMessageDialog(frame, "Please sign in first!");
//            cardLayout.show(frame.getContentPane(), "Main");
//        }
    }
    private boolean isValidAmount(double amount) {
        try {
            return amount > 0; // Ensure positive value
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid amount format! Please enter a number.");
            return false;
        }}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShopGUI::new);
    }
}
