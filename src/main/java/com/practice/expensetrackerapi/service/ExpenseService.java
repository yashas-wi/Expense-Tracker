package com.practice.expensetrackerapi.service;

import com.practice.expensetrackerapi.entity.Expense;
import com.practice.expensetrackerapi.entity.Month;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExpenseService {
    private final List<Expense> expenseList;
    private final AtomicInteger budget = new AtomicInteger(-1);

    public ExpenseService() {
        this.expenseList = new ArrayList<>();
    }

    public String saveExpenses(Expense expense) {
        if(!checkBudgetStatus()) throw new IllegalArgumentException("Budget not set!");
        try {
            try {
                Month month = expense.getMonth(); // This may throw
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to retrieve month.");
            }

            if (expense.getName().isEmpty())
                throw new IllegalArgumentException("Expense name cannot be empty");
            else if (expense.getDescription().isEmpty())
                throw new IllegalArgumentException("Expense description cannot be empty");
            else if (expense.getAmount() == 0.0)
                throw new IllegalArgumentException("Expense amount cannot be zero");

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        String summaryMsg = summaryOfMonth(expense.getMonth().toString());
        double spent = Double.parseDouble(summaryMsg.split("is: ")[1]);
        String response = "";
        if(spent + expense.getAmount() > budget.get()) {
            response += "You are over budget.\n";
        }


        expenseList.add(expense);
        expense.setCreatedAt(LocalDateTime.now());

        return response + "List Added Successfully";
    }

    public List<Expense> expenseList() {

        if(!checkBudgetStatus()) throw new IllegalArgumentException("Budget not set!");
        return expenseList;
    }

    public String updateDescription(String name, String description) {
        if(!checkBudgetStatus()) throw new IllegalArgumentException("Budget not set!");
        for (Expense expense : expenseList) {
            if (expense.getName().equalsIgnoreCase(name)) {
                expense.setDescription(description);
                expense.setUpdatedAt(LocalDateTime.now());
                return "Updated Successfully";
            }
        }
        throw new IllegalArgumentException("List not found");
    }

    public String updateAmount(String name, double amount) {
        if(!checkBudgetStatus()) throw new IllegalArgumentException("Budget not set!");
        for (Expense expense : expenseList) {
            if (expense.getName().equalsIgnoreCase(name)) {
                expense.setAmount(amount);
                expense.setUpdatedAt(LocalDateTime.now());

                return "Updated Successfully";
            }
        }
        throw new IllegalArgumentException("List not found");
    }

    public String deleteExpense(String name) {
        if(!checkBudgetStatus()) throw new IllegalArgumentException("Budget not set!");
        for (Expense expense : expenseList) {
            if (expense.getName().equalsIgnoreCase(name)) {
                expenseList.remove(expense);
                return "Deleted Successfully";
            }
        }
        throw new IllegalArgumentException("List not found");
    }

    public String summaryOfMonth(String month) {
        if(!checkBudgetStatus()) throw new IllegalArgumentException("Budget not set!");
        try {
            Month match = Month.valueOf(month.toUpperCase());
            double totalAmount = 0;
            for (Expense expense : expenseList) {
                if (expense.getMonth() == match) {
                    totalAmount = totalAmount + expense.getAmount();
                }
            }
            return "Total Expenses of " + match + " is: " + totalAmount;
        } catch (IllegalArgumentException e) {
            return "Month name is not appropriate.";
        }
    }


    public String summaryOfAllMonth() {
        if(!checkBudgetStatus()) throw new IllegalArgumentException("Budget not set!");
        Map<Month, Double> map = new HashMap<>();

        for (Expense expense : expenseList) {
            Month month = expense.getMonth();
            map.put(month, map.getOrDefault(month, 0D) + expense.getAmount());
        }
        String template = "Total Expense for ";
        StringBuilder result = new StringBuilder();

        for (Month m : Month.values()) {
            if (map.containsKey(m)) {
                double amt = map.get(m);
                result.append(template).append(m).append(" is ").append(amt).append("\n");
            }
        }
        return result.toString();
    }

    public boolean checkBudgetStatus() {
        return budget.get() != -1;
    }

    public void setBudget(int budgetRequest) {
        if(budget.get() != -1) throw new IllegalArgumentException("Budget already set!");
        if(budgetRequest <= 0) throw new IllegalArgumentException("Budget has inappropriate value!");

        budget.set(budgetRequest);
    }
}
