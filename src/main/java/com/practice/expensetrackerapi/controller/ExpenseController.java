package com.practice.expensetrackerapi.controller;

import com.practice.expensetrackerapi.entity.Expense;
import com.practice.expensetrackerapi.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/saveExpenses")
    public ResponseEntity <String> save(@RequestBody Expense expense){
        try {
           String response = expenseService.saveExpenses(expense);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/nameOfExpense")
    public ResponseEntity <String> getExpenseFromName(@RequestParam String name){
       try{
           return ResponseEntity.ok().build();
       }catch (IllegalArgumentException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @GetMapping("/listOfExpenses")
    public ResponseEntity <List<Expense>> lists(){
        try {
            List<Expense> response = expenseService.expenseList();
            return  ResponseEntity.ok().body(response);
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/updateDescription")
    public ResponseEntity <String> updateDescription(@RequestParam String name, @RequestParam String description){
        try{
            String response = expenseService.updateDescription(name, description);
            return ResponseEntity.ok().body(response);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateAmount")
    public ResponseEntity <String> updateAmount(@RequestParam String name, @RequestParam double amount){
        try{
            String response = expenseService.updateAmount(name, amount);
            return ResponseEntity.ok().body(response);
        }catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/deleteExpense")
    public ResponseEntity <String> deleteFromExpenseList(@RequestParam String name){
        try{
            String response = expenseService.deleteExpense(name);
            return ResponseEntity.ok().body(response);
        }
        catch(IllegalArgumentException e){
         return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/summaryOfMonth")
    public ResponseEntity <String> summaryOfMonth(@RequestParam String month){
        try{
            String response = expenseService.summaryOfMonth(month);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/summary")
    public ResponseEntity <String> summaryOfAllMonth(){
      try{
          String response = expenseService.summaryOfAllMonth();
          return ResponseEntity.ok().body(response);
      }
      catch(Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
      }
    }

    @PutMapping("/setBudget")
    public ResponseEntity <String> budget(@RequestParam int budget){
        try{
            expenseService.setBudget(budget);
            return ResponseEntity.ok().body("Budget set!");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("You are crossing the budget.");
        }
    }
}



