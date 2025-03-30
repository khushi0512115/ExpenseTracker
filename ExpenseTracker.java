
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class ExpenseTracker extends JFrame {
    private JTextField categoryField, amountField, descriptionField, budgetField;
    private JTable table;
    private DefaultTableModel model;
    private JLabel totalExpenseLabel, budgetLabel;
    private double totalExpenses = 0;
    private double budgetLimit = 0;
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public ExpenseTracker() {
        setTitle("Expense Tracker");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Inputs)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.setBackground(Color.DARK_GRAY);
        
        inputPanel.add(createLabel("Category:", Color.WHITE));
        categoryField = new JTextField();
        inputPanel.add(categoryField);
        
        inputPanel.add(createLabel("Amount (₹):", Color.WHITE));
        amountField = new JTextField();
        inputPanel.add(amountField);
        
        inputPanel.add(createLabel("Description:", Color.WHITE));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);
        
        inputPanel.add(createLabel("Set Budget (₹):", Color.YELLOW));
        budgetField = new JTextField();
        inputPanel.add(budgetField);

        JButton setBudgetButton = new JButton("Set Budget");
        setBudgetButton.setBackground(Color.ORANGE);
        setBudgetButton.addActionListener(e -> setBudget());
        inputPanel.add(setBudgetButton);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setBackground(Color.GREEN);
        addExpenseButton.addActionListener(e -> addExpense());
        inputPanel.add(addExpenseButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"Category", "Amount (₹)", "Description", "Date"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel (Total Expenses and Budget Limit)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.DARK_GRAY);
        totalExpenseLabel = createLabel("Total Expenses: ₹0", Color.WHITE);
        budgetLabel = createLabel("Budget Limit: ₹0", Color.WHITE);
        
        JButton calculateTotalButton = new JButton("Calculate Total");
        calculateTotalButton.setBackground(Color.CYAN);
        calculateTotalButton.addActionListener(e -> calculateTotal());

        bottomPanel.add(totalExpenseLabel);
        bottomPanel.add(budgetLabel);
        bottomPanel.add(calculateTotalButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Chart Panel (Budget vs. Expenses)
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart("Budget vs Expenses", "Category", "Amount (₹)", dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 200));

        JPanel chartContainer = new JPanel();
        chartContainer.setLayout(new BorderLayout());
        chartContainer.setBackground(Color.BLACK);
        chartContainer.add(createLabel("Budget vs Expenses", Color.WHITE), BorderLayout.NORTH);
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        
        add(chartContainer, BorderLayout.EAST);

        setVisible(true);
    }

    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void setBudget() {
        try {
            budgetLimit = Double.parseDouble(budgetField.getText());
            budgetLabel.setText("Budget Limit: ₹" + budgetLimit);
            updateGraph();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid budget amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addExpense() {
        String category = categoryField.getText();
        String amountText = amountField.getText();
        String description = descriptionField.getText();
        String date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(new Date());

        if (category.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter category and amount!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            totalExpenses += amount;
            model.addRow(new Object[]{category, "₹" + amount, description, date});
            totalExpenseLabel.setText("Total Expenses: ₹" + totalExpenses);
            updateGraph();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotal() {
        JOptionPane.showMessageDialog(this, "Total Expenses: ₹" + totalExpenses, "Total", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateGraph() {
        dataset.clear();
        dataset.addValue(totalExpenses, "Total Expenses", "Expenses");
        dataset.addValue(budgetLimit - totalExpenses, "Remaining Budget", "Budget");
        chart.fireChartChanged();
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}
