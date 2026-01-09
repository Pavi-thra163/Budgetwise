package com.budgetwise.controller;

import com.budgetwise.model.BudgetPlan;
import com.budgetwise.repository.BudgetPlanRepository;
import com.budgetwise.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiAlertController {

    private final ExpenseRepository expenseRepo;
    private final BudgetPlanRepository budgetRepo;

    public AiAlertController(ExpenseRepository expenseRepo,
                             BudgetPlanRepository budgetRepo) {
        this.expenseRepo = expenseRepo;
        this.budgetRepo = budgetRepo;
    }

    @GetMapping("/alert/{category}")
    public String checkAlert(@PathVariable String category) {

        double spent = expenseRepo.getTotalByCategory(category);
        BudgetPlan budget = budgetRepo.findByCategory(category);

        if (budget == null)
            return "⚠ No budget set for " + category;

        if (spent > budget.getBudgetAmount())
            return "🚨 ALERT: Budget exceeded for " + category;

        if (spent > budget.getBudgetAmount() * 0.8)
            return "⚠ Warning: 80% budget used for " + category;

        return "✅ Spending under control";
    }
}