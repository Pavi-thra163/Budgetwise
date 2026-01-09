package com.budgetwise.controller;

import com.budgetwise.model.BudgetPlan;
import com.budgetwise.model.Expense;
import com.budgetwise.repository.BudgetPlanRepository;
import com.budgetwise.repository.ExpenseRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExcelExportController {

    private final ExpenseRepository expenseRepo;
    private final BudgetPlanRepository budgetRepo;

    public ExcelExportController(ExpenseRepository expenseRepo,
                                 BudgetPlanRepository budgetRepo) {
        this.expenseRepo = expenseRepo;
        this.budgetRepo = budgetRepo;
    }

    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(
                "Content-Disposition", "attachment; filename=BudgetWise_Report.xlsx");

        Workbook workbook = new XSSFWorkbook();

        /* ================= EXPENSE SHEET ================= */
        Sheet expenseSheet = workbook.createSheet("Expenses");
        Row expHeader = expenseSheet.createRow(0);
        expHeader.createCell(0).setCellValue("Category");
        expHeader.createCell(1).setCellValue("Amount");
        expHeader.createCell(2).setCellValue("Date");

        List<Expense> expenses = expenseRepo.findAll();
        int r = 1;
        for (Expense e : expenses) {
            Row row = expenseSheet.createRow(r++);
            row.createCell(0).setCellValue(e.getCategory());
            row.createCell(1).setCellValue(e.getAmount());
            row.createCell(2).setCellValue(e.getDate().toString());
        }

        /* ================= BUDGET SHEET ================= */
        Sheet budgetSheet = workbook.createSheet("Budgets");
        Row budHeader = budgetSheet.createRow(0);
        budHeader.createCell(0).setCellValue("Category");
        budHeader.createCell(1).setCellValue("Budget Amount");

        List<BudgetPlan> budgets = budgetRepo.findAll();
        int b = 1;
        for (BudgetPlan bp : budgets) {
            Row row = budgetSheet.createRow(b++);
            row.createCell(0).setCellValue(bp.getCategory());
            row.createCell(1).setCellValue(bp.getBudgetAmount());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}