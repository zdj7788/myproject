package com.example.waitthings;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String name;
    private String dueDate;
    private int priority;
    private boolean isCompleted;

    // 构造函数
    public Task(String name, String dueDate, int priority, boolean isCompleted) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public Task(int id, String name, String dueDate, int priority, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
