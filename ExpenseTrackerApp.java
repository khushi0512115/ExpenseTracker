import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class ExpenseTrackerApp {
    private static DefaultListModel<String> listModel;
    private static JTextArea expenseArea;
    private static JTextField categoryField, amountField, descriptionField;
    private static ExpenseManager expenseManager;

    public static void main(String[] args) {
        expenseManager = new ExpenseManager();
        listModel = new DefaultListModel<>();
        JFrame frame = new JFrame("Expense Tracker");

        // Corrected path with double backslashes
        String imagePath = "C:\\Users\\yash garg\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-03-08 110714.png"; // Path to your icon image
        String backgroundImagePath = "C:\\Users\\yash garg\\Downloads\\budget_781760.png"; // Path to your background image

        // Custom JPanel to draw the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the panel is properly painted before drawing the image

                // Load the image from the background path
                ImageIcon backgroundIcon = new ImageIcon(backgroundImagePath);
                Image backgroundImage = backgroundIcon.getImage();

                // Draw the image to fill the panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Create a JLabel for the app name (centered in the panel)
        JLabel appNameLabel = new JLabel("Expense Tracker");
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));  // Large font size for the app name
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label horizontally
        appNameLabel.setVerticalAlignment(SwingConstants.CENTER); // Center the label vertically
        appNameLabel.setForeground(Color.BLUE); // Set label text color to white
        appNameLabel.setOpaque(false); // Make sure the label is transparent so it blends with the background

        // Panel for input fields (with some padding)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1, 03, 03));  // Set padding between input fields
        inputPanel.setOpaque(false); // Make the panel transparent

        // Create larger font for labels
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font fieldFont = new Font("Arial", Font.PLAIN, 20); // For text fields

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(labelFont); // Set font for label
        inputPanel.add(categoryLabel);
        
        categoryField = new JTextField();
        categoryField.setFont(fieldFont); // Set font for text field
        categoryField.setPreferredSize(new Dimension(100, 100));  // Set preferred size for the text field
        inputPanel.add(categoryField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(labelFont); // Set font for label
        inputPanel.add(amountLabel);

        amountField = new JTextField();
        amountField.setFont(fieldFont); // Set font for text field
        amountField.setPreferredSize(new Dimension(200, 30));  // Set preferred size for the text field
        inputPanel.add(amountField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont); // Set font for label
        inputPanel.add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setFont(fieldFont); // Set font for text field
        descriptionField.setPreferredSize(new Dimension(200, 30));  // Set preferred size for the text field
        inputPanel.add(descriptionField);

        // Add Buttons for Actions (without the icon)
        JButton addButton = new JButton("Add Expense");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));  // Set font for button
        addButton.setBackground(Color.GREEN); // Set a background color
        addButton.setForeground(Color.WHITE); // Set button text color
        addButton.setFocusPainted(false); // Remove the button border focus
        addButton.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Add border
        addButton.setPreferredSize(new Dimension(200, 40)); // Set button size
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));  // Set font for button
        calculateButton.setBackground(Color.CYAN); // Set a background color
        calculateButton.setForeground(Color.BLACK); // Set button text color
        calculateButton.setFocusPainted(false); // Remove the button border focus
        calculateButton.setPreferredSize(new Dimension(200, 100)); // Set button size
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotal();
            }
        });

        // List to display all expenses
        JList<String> expenseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);

        // Layout configuration
        backgroundPanel.setLayout(new BorderLayout());
        
        // Add the app name in the center (vertically and horizontally)
        backgroundPanel.add(appNameLabel, BorderLayout.NORTH);
        backgroundPanel.add(inputPanel, BorderLayout.CENTER);
        backgroundPanel.add(scrollPane, BorderLayout.SOUTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Make button panel transparent
        buttonPanel.add(addButton);
        buttonPanel.add(calculateButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        frame.setContentPane(backgroundPanel); // Set the custom background panel as the content pane
        frame.setSize(300, 100); // Adjust frame size to accommodate the app name
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void addExpense() {
        try {
            String category = categoryField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();
            Date date = new Date();

            // Create a new expense object
            expenseManager.addExpense(category, amount, description, date);

            // Display in the list
            listModel.addElement("Category: " + category + " | Amount: $" + amount + " | " + description);

            // Clear the fields
            categoryField.setText("");
            amountField.setText("");
            descriptionField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void calculateTotal() {
        double total = expenseManager.calculateTotal();
        JOptionPane.showMessageDialog(null, "Total Expenses: $" + total, "Total", JOptionPane.INFORMATION_MESSAGE);
    }
}
