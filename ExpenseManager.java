import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public void addExpense(String category, double amount, String description, Date date) {
        Expense expense = new Expense(category, amount, description, date);
        expenses.add(expense);
    }

    public void removeExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
        }
    }

    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (int i = 0; i < expenses.size(); i++) {
                System.out.print("Index: " + i + " | ");
                expenses.get(i).displayExpense();
            }
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public void displayExpensesByCategory(String category) {
        boolean found = false;
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                expense.displayExpense();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No expenses found in this category.");
        }
    }
}
