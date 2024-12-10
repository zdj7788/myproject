package com.example.waitthings;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskClickListener onTaskClickListener;
    private TaskDatabaseHelper dbHelper;

    public TaskAdapter(List<Task> taskList, OnTaskClickListener onTaskClickListener, TaskDatabaseHelper dbHelper) {
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
        this.dbHelper = dbHelper;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.nameTextView.setText(task.getName());
        holder.dueDateTextView.setText(task.getDueDate());
        holder.priorityTextView.setText("优先级：" + task.getPriority());
        holder.checkBox.setChecked(task.isCompleted());

        // 点击任务，切换完成状态
        holder.itemView.setOnClickListener(v -> onTaskClickListener.onTaskClick(task));

        // 删除任务按钮点击事件
        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteTask(task.getId());
            taskList.remove(position); // 从列表中移除任务
            notifyItemRemoved(position); // 刷新视图
        });

        // 编辑任务按钮点击事件
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditTaskActivity.class);
            intent.putExtra("task_id", task.getId());
            intent.putExtra("task_name", task.getName());
            intent.putExtra("task_due_date", task.getDueDate());
            intent.putExtra("task_priority", task.getPriority());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, dueDateTextView, priorityTextView;
        CheckBox checkBox;
        ImageButton deleteButton, editButton;

        TaskViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.task_name);
            dueDateTextView = itemView.findViewById(R.id.task_due_date);
            priorityTextView = itemView.findViewById(R.id.task_priority);
            checkBox = itemView.findViewById(R.id.task_check_box);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button); // 编辑按钮
        }
    }
}
