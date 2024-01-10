package com.example.to_do_listapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_dolist.model.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

public class AddTaskBottomSheetFragment extends BottomSheetDialogFragment {
    private TextInputEditText titleEditText;
    private Spinner prioritySpinner;
    private Spinner categorySpinner;

    private TextInputEditText descriptionEditText;
    private String selectedDate;

    private int year, month, day;

    public AddTaskBottomSheetFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task_bottom, container, false);

        titleEditText = view.findViewById(R.id.titleEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);

        ImageView calendarImageView = view.findViewById(R.id.calendarImageView);

        Button saveButton = view.findViewById(R.id.saveButton);


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        calendarImageView.setOnClickListener(v -> showDatePickerDialog());
        prioritySpinner = view.findViewById(R.id.prioritySpinner);
        categorySpinner = view.findViewById(R.id.categorySpinner);






        saveButton.setOnClickListener(v -> {
            if (validateInput()) {
                addTaskToList();
                dismiss();
            }
        });

        return view;
    }
    private OnTaskAddedListener onTaskAddedListener;

    public interface OnTaskAddedListener {
        void onTaskAdded(Task newTask);
    }

    // Setter method to set the listener
    public void setOnTaskAddedListener(OnTaskAddedListener listener) {
        this.onTaskAddedListener = listener;
    }
    private boolean validateInput() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString().trim();
        String description = Objects.requireNonNull(descriptionEditText.getText()).toString().trim();
        String dueDate = selectedDate;
        String priority = prioritySpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();

        if (title.isEmpty()) {
            showSnackbar();
            return false;
        }
        if (description.isEmpty()) {
            showSnackbar();
            return false;
        }
        if (dueDate.isEmpty()) {
            showSnackbar();
            return false;
        }
        if (priority.isEmpty()) {
            showSnackbar();
            return false;
        }
        if (category.isEmpty()) {
            showSnackbar();
            return false;
        }

        // Add similar checks for other fields as needed

        return true;
    }

    private void showSnackbar() {
        Snackbar.make(requireView(), "Title cannot be empty", Snackbar.LENGTH_SHORT).show();
    }

    private void showDatePickerDialog() {
        @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    //dueDateEditText.setText(String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year));
                    selectedDate = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);
                    AddTaskBottomSheetFragment.this.year = year;
                    month = monthOfYear;
                    day = dayOfMonth;
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void addTaskToList() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString();
        String description = Objects.requireNonNull(descriptionEditText.getText()).toString();
        String dueDate = selectedDate;
        String priority = prioritySpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();

        Task newTask = new Task(title, description, dueDate, priority, category);
        if (onTaskAddedListener != null) {
            onTaskAddedListener.onTaskAdded(newTask);
        }
    }
}
