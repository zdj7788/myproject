package com.example.waitthings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskEditText, dueDateEditText, priorityEditText;
    private Button saveButton;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskEditText = findViewById(R.id.task_edit_text);
        dueDateEditText = findViewById(R.id.due_date_edit_text);
        priorityEditText = findViewById(R.id.priority_edit_text);
        saveButton = findViewById(R.id.save_button);

        dbHelper = new TaskDatabaseHelper(this);

        // 任务期限点击事件，弹出日期选择器
        dueDateEditText.setOnClickListener(v -> showDatePickerDialog());

        saveButton.setOnClickListener(v -> {
            String taskName = taskEditText.getText().toString().trim();
            String dueDate = dueDateEditText.getText().toString().trim();
            String priorityText = priorityEditText.getText().toString().trim();

            // 检查是否填写所有字段
            if (taskName.isEmpty() || dueDate.isEmpty() || priorityText.isEmpty()) {
                Toast.makeText(AddTaskActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                return;
            }

            int priority;
            try {
                priority = Integer.parseInt(priorityText);
            } catch (NumberFormatException e) {
                Toast.makeText(AddTaskActivity.this, "优先级必须是数字", Toast.LENGTH_SHORT).show();
                return;
            }

            // 添加任务到数据库
            Task newTask = new Task(taskName, dueDate, priority, false);
            dbHelper.addTask(newTask);

            // 显示任务添加成功的提示
            Toast.makeText(AddTaskActivity.this, "任务添加成功", Toast.LENGTH_SHORT).show();

            // 任务添加完成后跳回主界面
            finish();  // 结束当前界面
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        // 设置最小日期为当前日期
        long currentTime = System.currentTimeMillis();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddTaskActivity.this,
                (view, year, month, dayOfMonth) -> {
                    // 格式化日期为 yyyy-MM-dd 格式
                    String date = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                    dueDateEditText.setText(date);  // 将选择的日期显示在EditText中
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // 设置最小日期为当前日期，避免选择过去的日期
        datePickerDialog.getDatePicker().setMinDate(currentTime);

        datePickerDialog.show();  // 显示日期选择器
    }
}
