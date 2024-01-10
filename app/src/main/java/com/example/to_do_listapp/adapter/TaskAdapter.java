package com.example.to_do_listapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_listapp.R;
import com.example.to_dolist.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;
    private OnItemClickListener clickListener;


    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView dueDateTextView;
        private final TextView categoryTextView;
        private final TextView priorityTextView;
        private final TextView statusTextView;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            priorityTextView = itemView.findViewById(R.id.priorityTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickListener != null) {
                    clickListener.onItemClick(taskList.get(position));// Mark the task as not new after clicking
                    notifyItemChanged(position); // Update the view
                }
            });
        }

        void bind(Task task) {
            titleTextView.setText(task.getTitle());
            dueDateTextView.setText(String.format("Due Date: %s", task.getDueDate()));
            categoryTextView.setText(String.format("Category: %s", task.getCategory()));
            priorityTextView.setText(String.format("Priority: %s", task.getPriority()));
            statusTextView.setText(task.getStatus());

        }
    }


    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

}

