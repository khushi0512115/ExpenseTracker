import java.util.Date;

public class Expense {
    private String category;
    private double amount;
    private String description;
    private Date date;

    public Expense(String category, double amount, String description, Date date) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public void displayExpense() {
        System.out.println("Category: " + category + " | Amount: $" + amount + " | Description: " + description + " | Date: " + date);
    }
}
