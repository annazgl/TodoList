package com.anna.todolist.database;

import com.anna.todolist.model.TodoTask;

import java.util.Date;
import java.util.List;

public interface ITaskDatabase {
    List<TodoTask> getTasks();

    List<TodoTask> getFutureTasksWithReminder(Date now);

    TodoTask getTask(int position);

    void addTask(TodoTask task);

    void updateTask(TodoTask task, int position);
}
