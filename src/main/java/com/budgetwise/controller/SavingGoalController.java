package com.budgetwise.controller;

import com.budgetwise.model.SavingGoal;
import com.budgetwise.repository.SavingGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin
public class SavingGoalController {

    @Autowired
    private SavingGoalRepository goalRepo;

    // ✅ ADD GOAL
    @PostMapping("/add")
    public SavingGoal addGoal(@RequestBody SavingGoal goal) {
        return goalRepo.save(goal);
    }

    // ✅ VIEW ALL GOALS
    @GetMapping("/all")
    public List<SavingGoal> getAllGoals() {
        return goalRepo.findAll();
    }

    // ✅ UPDATE SAVED AMOUNT
    @PutMapping("/update/{id}")
    public SavingGoal updateGoal(@PathVariable Long id,
                                 @RequestParam double savedAmount) {

        SavingGoal goal = goalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        goal.setSavedAmount(savedAmount);
        return goalRepo.save(goal);
    }

    // ✅ DELETE GOAL
    @DeleteMapping("/delete/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalRepo.deleteById(id);
        return "Saving goal deleted successfully";
    }

    // ✅ CHECK IF TARGET EXCEEDED
    @GetMapping("/status/{id}")
    public String checkStatus(@PathVariable Long id) {
        SavingGoal goal = goalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (goal.isExceeded()) {
            return "Target Exceeded ❌";
        }
        return "On Track ✅";
    }
}