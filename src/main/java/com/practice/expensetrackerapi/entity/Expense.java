package com.practice.expensetrackerapi.entity;
import java.time.LocalDateTime;

public class Expense {
    private String name;
    private String description;
    private double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String month;
    private double budget;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public double getAmount(){
        return amount;
    }
    public void setAmount(Double amount){
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = LocalDateTime.now();
    }

    public Month getMonth(){
        return Month.valueOf(month);
    }

    public void setMonth(Month month){
        this.month = month.toString();
    }

    @Override
    public String toString() {
        return "Expense{" +
                "name" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount" + amount + '\'' +
                ", created=" + createdAt +
                ", updated=" + updatedAt +
                ",monthName=" + month +
                '}';
    }
}
