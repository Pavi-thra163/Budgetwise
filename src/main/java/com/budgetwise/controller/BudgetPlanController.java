package com.budgetwise.controller;

import com.budgetwise.model.BudgetPlan;
import com.budgetwise.repository.BudgetPlanRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-plans")
@CrossOrigin(origins = "*")
public class BudgetPlanController {

    private final BudgetPlanRepository repo;

    public BudgetPlanController(BudgetPlanRepository repo) {
        this.repo = repo;
    }

    // GET all budget plans (for Financial Trends)
    @GetMapping
    public List<BudgetPlan> getAllPlans() {
        return repo.findAll();
    }
}