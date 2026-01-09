package com.budgetwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.budgetwise.model.Expense;
import com.budgetwise.repository.ExpenseRepository;

@RestController
@RequestMapping("/expense")
@CrossOrigin
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;

    // Add Expense
    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseRepo.save(expense);
    }

    // View all Expenses
    @GetMapping("/all")
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }
}
