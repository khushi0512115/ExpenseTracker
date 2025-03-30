
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;
    private double budgetLimit;

    public ExpenseManager() {
        expenses = new ArrayList<>();
        budgetLimit = 0; // Default budget is 0 (user can set it)
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

    public double calculateTotal() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setBudgetLimit(double budget) {
        this.budgetLimit = budget;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public boolean isBudgetExceeded() {
        return calculateTotal() > budgetLimit;
    }
}
