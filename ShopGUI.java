import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

//refrences:
//https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/start/index.html
//https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F/java/awt/GridBagLayout.html
//https://www.baeldung.com/get-started-with-java-series
//https://www.vogella.com/tutorials/java.html
//https://stackoverflow.com/questions/15363706/how-to-program-this-gui-in-java-swing
//https://stackoverflow.com/questions/56961921/creating-a-jbutton-with-the-click-of-a-jbutton
//https://stackoverflow.com/questions/34205782/sort-the-contents-of-a-jtable
//https://stackoverflow.com/questions/54132546/java-login-form
//https://stackoverflow.com/questions/16636711/java-is-this-good-use-of-bcrypt
//https://docs.oracle.com/javase/8/docs/technotes/guides/collections/index.html


public class ShopGUI {
    private JFrame frame;
    private JPanel mainPanel, signInPanel, signUpPanel, managerSignInPanel, cartPanel, managerProfilePanel, profilePanel, editProfilePanel, addBalancePanel;
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
        managerProfilePanel = new JPanel();
        cartPanel = new JPanel();
        profilePanel = new JPanel();
        editProfilePanel = new JPanel();
        addBalancePanel = new JPanel();

        // Add panels to frame
        frame.add(mainPanel, "Main");
        frame.add(signInPanel, "SignIn");
        frame.add(signUpPanel, "SignUp");
        frame.add(managerSignInPanel, "ManagerSignIn");
        frame.add(managerProfilePanel, "Manager");
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

        // Setup manager panel
        setupManagerProfilePanel();

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

        Product product1 = new Product("Product 1", 100, "resources/image1.jpeg", "Category 1", 4.5,10);
        Product product2 = new Product("Product 2", 200, "resources/image2.jpeg", "Category 2", 3.8,10);
        Product product3 = new Product("Product 3", 180, "resources/image3.jpeg", "Category 3", 3.3,10);

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
            BufferedImage image = null;
            if (imageURL != null) {
                try {
                    image = ImageIO.read(imageURL);
                } catch (IOException e) {
                    System.err.println("Error reading image: " + e.getMessage());
                }
            }
            if (imageURL != null) {
                int maxWidth = 120;
                int maxHeight = 120;

                // Scale the image (maintain aspect ratio)
                double aspectRatio = (double) image.getWidth() / (double) image.getHeight();
                int scaledWidth;
                int scaledHeight;
                if (aspectRatio > 1) { // Wider than tall
                    scaledWidth = maxWidth;
                    scaledHeight = (int) (maxWidth / aspectRatio);
                } else { // Taller than wide or equal
                    scaledHeight = maxHeight;
                    scaledWidth = (int) (maxHeight * aspectRatio);
                }

                Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);            }
            else {
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

    private void setupManagerProfilePanel() {

        managerProfilePanel.setLayout(new BorderLayout());

        // Product management panel
        JPanel productManagementPanel = new JPanel();
        productManagementPanel.setLayout(new BoxLayout(productManagementPanel, BoxLayout.Y_AXIS));

        JButton addProductButton = new JButton("Add Product");
        JButton removeProductButton = new JButton("Remove Product");
        JButton viewUsersButton = new JButton("View Registered Users");

        addProductButton.addActionListener(e -> addProduct());
        removeProductButton.addActionListener(e -> removeProduct());
        viewUsersButton.addActionListener(e -> viewRegisteredUsers());

        productManagementPanel.add(addProductButton);
        productManagementPanel.add(removeProductButton);
        productManagementPanel.add(viewUsersButton);

        managerProfilePanel.add(productManagementPanel, BorderLayout.NORTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));
        managerProfilePanel.add(backButton, BorderLayout.SOUTH);
    }

    private void addProduct() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField imageField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField ratingField = new JTextField();
        JTextField quantityField = new JTextField();


        Object[] fields = {
                "Name:", nameField,
                "Price:", priceField,
                "Image Path:", imageField,
                "Category:", categoryField,
                "Rating:", ratingField ,
                "Quantity", quantityField
        };

        int result = JOptionPane.showConfirmDialog(frame, fields, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String image = imageField.getText();
            String category = categoryField.getText();
            double rating = Double.parseDouble(ratingField.getText());
            int quantity = (int) Double.parseDouble(quantityField.getText());

            Product newProduct = new Product(name, price, image, category, rating,quantity);
            storeManager.addProduct(newProduct);
            displayProducts(storeManager.getAllProducts());
        }
    }

    private void removeProduct() {
        String name = JOptionPane.showInputDialog(frame, "Enter the name of the product to remove:");
        if (name != null && !name.isEmpty()) {
            for (Product product : storeManager.getAllProducts()) {
                if (product.getName().equals(name)) {
                    storeManager.removeProduct(product);
                }
            }
            displayProducts(storeManager.getAllProducts());
        }
    }

    private void viewRegisteredUsers() {
//        ArrayList<User> users = storeManager.getAllUsers();
//        StringBuilder userList = new StringBuilder("Registered Users:\n");
//        for (User user : users) {
//            userList.append(user.getUsername()).append("\n");
//        }
//        JOptionPane.showMessageDialog(frame, userList.toString());
    }

    private void setupSignInPanel() {
        signInPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:", JLabel.LEFT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        signInPanel.add(usernameLabel, c);

        JTextField usernameField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        signInPanel.add(usernameField, c);

        JLabel passwordLabel = new JLabel("Password:", JLabel.LEFT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        signInPanel.add(passwordLabel, c);

        JPasswordField passwordField = new JPasswordField(20);
        c.gridx = 1;
        c.gridy = 1;
        signInPanel.add(passwordField, c);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        signInPanel.add(backButton, c);

        // Sign up button with validation
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a username!");
                return;
            }
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a password!");
                return;
            }
            boolean loggedIn = signInUser(username, password);

            if (loggedIn) {
                JOptionPane.showMessageDialog(frame, "Sign in successful!");
                currentBuyer = new Buyer(username, "buyerId", 1000, password,"add");
                userPanel = new UserPanel(currentBuyer);
                cardLayout.show(frame.getContentPane(), "Profile");
                setupProfilePanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Sign in failed! Please check your username and password.");
            }
        });
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        signInPanel.add(signInButton, c);
    }
    private boolean signInUser(String username, String password) {
        return storeManager.verifyUserCredentials(username, password);
    }


    private void setupSignUpPanel() {
        signUpPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Username label and input field
        JLabel usernameLabel = new JLabel("Username:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        signUpPanel.add(usernameLabel, c);

        JTextField usernameField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        signUpPanel.add(usernameField, c);

        JLabel passwordLabel = new JLabel("Password:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        signUpPanel.add(passwordLabel, c);

        JPasswordField passwordField = new JPasswordField(20);
        c.gridx = 1;
        c.gridy = 1;
        signUpPanel.add(passwordField, c);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        signUpPanel.add(backButton, c);

        // Sign up button with validation
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a username!");
                return;
            }
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a password!");
                return;
            }
            boolean userCreated = createUser(username, password);

            if (userCreated) {
                JOptionPane.showMessageDialog(frame, "Sign up successful!");
                cardLayout.show(frame.getContentPane(), "SignIn");
            } else {
                JOptionPane.showMessageDialog(frame, "Sign up failed! Please try again.");
            }
        });
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        signUpPanel.add(signUpButton, c);
    }
    private boolean createUser(String username, String password) {
        storeManager.registerUser(username, password);
        return storeManager.verifyUserCredentials(username, password);
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
                cardLayout.show(frame.getContentPane(), "Manager");
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
            JPanel cartItemsPanel = createCartItemsPanel(currentBuyer.getCart());
            JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
            cartPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            JButton finalizeButton = new JButton("Finalize Cart");
            finalizeButton.addActionListener(e -> {
                if (finalizeCart(currentBuyer)) {
                    JOptionPane.showMessageDialog(frame, "Cart finalized successfully!");
                    cardLayout.show(frame.getContentPane(), "Main");

                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to finalize cart. Please check your balance!");
                }
            });
            buttonPanel.add(finalizeButton);
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Main"));
            buttonPanel.add(backButton);
            cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        }
    }

    private boolean finalizeCart(Buyer currentBuyer) {
        double totalPrice = calculateTotalPrice(currentBuyer.getCart());
        if (currentBuyer.getBalance() < totalPrice) {
            return false;
        }
        currentBuyer.deductBalance(totalPrice);

        return true;
    }

    private double calculateTotalPrice(ArrayList<Product> cartItems) {
        double totalPrice = 0.0;
        for (Product product : cartItems) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
    private JPanel createCartItemsPanel(ArrayList<Product> cartItems) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        for (Product product : cartItems) {
            panel.add(new JLabel(product.getName() + " - $" + product.getPrice()));
        }
        return panel;
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

            profilePanel.add(new JLabel("Address:"));
            profilePanel.add(new JLabel(currentBuyer.getAddress()));

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
        if (currentBuyer != null) {
            editProfilePanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            // Labels and input fields
            JLabel nameLabel = new JLabel("Name:");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            editProfilePanel.add(nameLabel, c);

            JTextField nameField = new JTextField(20);
            nameField.setText(currentBuyer.getName()); // Set initial value
            c.gridx = 1;
            c.gridy = 0;
            editProfilePanel.add(nameField, c);

            JLabel locationLabel = new JLabel("Location:");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 1;
            editProfilePanel.add(locationLabel, c);

            JTextField locationField = new JTextField(20);
            locationField.setText(currentBuyer.getAddress());
            c.gridx = 1;
            c.gridy = 1;
            editProfilePanel.add(locationField, c);

            // Save changes button
            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(e -> {
                String newName = nameField.getText();
                String newLocation = locationField.getText();

                if (newName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a name!");
                    return;
                }

                currentBuyer.setName(newName);
                currentBuyer.setAddress(newLocation);
                JOptionPane.showMessageDialog(frame, "Profile updated successfully!");
                cardLayout.show(frame.getContentPane(), "Profile");
                System.out.println(currentBuyer.getName());

            });

            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.EAST;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 2;
            editProfilePanel.add(saveButton, c);

            nameField.setText(currentBuyer.getName());
        }

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
