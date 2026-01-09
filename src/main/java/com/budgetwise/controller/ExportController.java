package com.budgetwise.controller;

import com.budgetwise.model.BudgetPlan;
import com.budgetwise.model.Expense;
import com.budgetwise.repository.BudgetPlanRepository;
import com.budgetwise.repository.ExpenseRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/export")
@CrossOrigin
public class ExportController {

    private final ExpenseRepository expenseRepo;
    private final BudgetPlanRepository budgetRepo;

    public ExportController(ExpenseRepository expenseRepo,
                            BudgetPlanRepository budgetRepo) {
        this.expenseRepo = expenseRepo;
        this.budgetRepo = budgetRepo;
    }

    @GetMapping("/pdf")
    public void exportPdf(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=BudgetWise_Report.pdf"
            );
            response.setHeader(
                    "Cache-Control",
                    "no-cache,no-store,must-revalidate"
            );

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            document.add(new Paragraph("BudgetWise - Expense Report\n\n", titleFont));

            List<Expense> expenses = expenseRepo.findAll();
            document.add(new Paragraph("Expenses:\n"));

            for (Expense e : expenses) {
                document.add(new Paragraph(
                        e.getCategory() + " - ₹" + e.getAmount()
                ));
            }

            document.add(new Paragraph("\nBudget Plans:\n"));

            List<BudgetPlan> budgets = budgetRepo.findAll();
            for (BudgetPlan b : budgets) {
                document.add(new Paragraph(
                        b.getCategory() + " - Limit ₹" + b.getBudgetAmount()
                ));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}