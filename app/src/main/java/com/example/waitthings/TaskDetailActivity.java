package com.example.waitthings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView taskNameTextView, taskDueDateTextView, taskPriorityTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // 初始化控件
        taskNameTextView = findViewById(R.id.task_name);
        taskDueDateTextView = findViewById(R.id.task_due_date);
        taskPriorityTextView = findViewById(R.id.task_priority);
        backButton = findViewById(R.id.back_button);

        // 获取传递过来的 Task 对象
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");

        if (task != null) {
            // 设置任务的详细信息到视图
            taskNameTextView.setText(task.getName());
            taskDueDateTextView.setText("到期日期：" + task.getDueDate());
            taskPriorityTextView.setText("优先级：" + task.getPriority());
        }

        // 设置返回按钮的点击事件
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回到上一界面
                finish();
            }
        });
    }
}
