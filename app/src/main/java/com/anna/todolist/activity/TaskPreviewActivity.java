package com.anna.todolist.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anna.todolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskPreviewActivity extends TaskCreateActivity {
    @BindView(R.id.btn_save)
    Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mTaskTitle.setEnabled(false);
        mTaskNote.setEnabled(false);

        mTaskReminder.setVisibility(View.GONE);
        mTaskReminderDate.setVisibility(View.GONE);
        mTaskReminderTime.setVisibility(View.GONE);
        mSaveButton.setVisibility((View.GONE));
    }
}
