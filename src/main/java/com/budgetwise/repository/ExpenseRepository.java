
package com.budgetwise.repository;

import com.budgetwise.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e")
    double getTotalExpense();

    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE e.category = :category")
    double getTotalByCategory(@Param("category") String category);
}