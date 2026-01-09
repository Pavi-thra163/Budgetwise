package com.budgetwise.controller;

import com.budgetwise.model.BudgetPlan;
import com.budgetwise.model.Expense;
import com.budgetwise.repository.BudgetPlanRepository;
import com.budgetwise.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/financial-trends")
@CrossOrigin
public class FinancialTrendController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetPlanRepository budgetPlanRepository;

    /* ===============================
       1. CATEGORY-WISE EXPENSE TREND
       =============================== */
    @GetMapping("/expense-by-category")
    public Map<String, Double> expenseByCategory() {

        Map<String, Double> result = new HashMap<>();

        for (Expense expense : expenseRepository.findAll()) {
            String category = expense.getCategory();
            double amount = expense.getAmount();

            result.put(category, result.getOrDefault(category, 0.0) + amount);
        }
        return result;
    }

    /* ===============================
       2. BUDGET vs EXPENSE COMPARISON
       =============================== */
    @GetMapping("/budget-vs-expense")
    public Map<String, Double> budgetVsExpense() {

        double totalBudget = 0;
        for (BudgetPlan plan : budgetPlanRepository.findAll()) {
            totalBudget += plan.getSpentAmount();
        }

        double totalExpense = 0;
        for (Expense expense : expenseRepository.findAll()) {
            totalExpense += expense.getAmount();
        }

        Map<String, Double> comparison = new LinkedHashMap<>();
        comparison.put("Total Budget", totalBudget);
        comparison.put("Total Expense", totalExpense);

        return comparison;
    }
}