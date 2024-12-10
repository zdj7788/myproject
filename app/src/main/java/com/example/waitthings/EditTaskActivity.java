package com.example.waitthings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, taskDueDateEditText, taskPriorityEditText;
    private Button saveButton;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskNameEditText = findViewById(R.id.edit_task_name);
        taskDueDateEditText = findViewById(R.id.edit_task_due_date);
        taskPriorityEditText = findViewById(R.id.edit_task_priority);
        saveButton = findViewById(R.id.save_button);

        // 获取传递的任务数据
        taskId = getIntent().getIntExtra("task_id", -1);
        String taskName = getIntent().getStringExtra("task_name");
        String taskDueDate = getIntent().getStringExtra("task_due_date");
        int taskPriority = getIntent().getIntExtra("task_priority", 1);

        // 设置字段默认值
        taskNameEditText.setText(taskName);
        taskDueDateEditText.setText(taskDueDate);
        taskPriorityEditText.setText(String.valueOf(taskPriority));

        taskDueDateEditText.setOnClickListener(v -> {
            showDatePickerDialog();  // 点击输入框弹出日期选择器
        });

        // 保存按钮点击事件
        saveButton.setOnClickListener(v -> {
            String name = taskNameEditText.getText().toString().trim();
            String dueDate = taskDueDateEditText.getText().toString().trim();
            String priorityText = taskPriorityEditText.getText().toString().trim();

            if (name.isEmpty() || dueDate.isEmpty() || priorityText.isEmpty()) {
                Toast.makeText(EditTaskActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidDate(dueDate)) {
                Toast.makeText(EditTaskActivity.this, "请选择有效的截止日期", Toast.LENGTH_SHORT).show();
                return;
            }

            int priority;
            try {
                priority = Integer.parseInt(priorityText);
            } catch (NumberFormatException e) {
                Toast.makeText(EditTaskActivity.this, "优先级必须是数字", Toast.LENGTH_SHORT).show();
                return;
            }

            Task updatedTask = new Task(taskId, name, dueDate, priority, false);
            TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(EditTaskActivity.this);
            dbHelper.updateTask(updatedTask);

            // 返回主界面
            finish();
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                EditTaskActivity.this,
                (view, year, month, dayOfMonth) -> {
                    // 设置截止日期格式
                    String date = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                    taskDueDateEditText.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // 设置最大日期为今天及以后
        calendar.add(Calendar.DATE, 0);  // 保证选择今天及以后的日期
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());  // 设置最小日期为今天
        datePickerDialog.show();
    }

    // 验证日期是否合法（截止日期不能小于今天）
    private boolean isValidDate(String dueDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date selectedDate = dateFormat.parse(dueDate);
            Date currentDate = new Date();

            // 判断选择的日期是否小于当前日期
            return selectedDate != null && !selectedDate.before(currentDate);
        } catch (Exception e) {
            return false;
        }
    }
}
