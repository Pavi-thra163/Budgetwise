
package com.budgetwise.repository;

import com.budgetwise.model.BudgetPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetPlanRepository
        extends JpaRepository<BudgetPlan, Integer> {
    BudgetPlan findByCategory(String category);
}