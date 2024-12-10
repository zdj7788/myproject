package com.example.waitthings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TaskDatabaseHelper dbHelper;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.add_button);

        dbHelper = new TaskDatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 加载任务列表
        loadTasks();

        // 添加任务按钮点击，跳转到 AddTaskActivity
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    private void loadTasks() {
        List<Task> tasks = dbHelper.getTasks();
        taskAdapter = new TaskAdapter(tasks, task -> {
            dbHelper.updateTaskCompletionStatus(task.getId(), !task.isCompleted());
            loadTasks();
        }, dbHelper);

        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }
}
