package com.anna.todolist.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;


import com.anna.todolist.R;
import com.anna.todolist.activity.TaskPreviewActivity;
import com.anna.todolist.database.ITaskDatabase;
import com.anna.todolist.database.SqliteTaskDatabase;
import com.anna.todolist.model.TodoTask;

public class TodoNotificationService extends IntentService {
    private ITaskDatabase mTaskDatabase;

    public TodoNotificationService() {
        super("TodoNotificationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTaskDatabase = new SqliteTaskDatabase(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int taskId = intent.getIntExtra("id", -1);
        TodoTask task = mTaskDatabase.getTask(taskId);

        if(task == null){
            //Jeżeli task nie istnieje to nie robimy nic dalej
            return;
        }

        Intent previewIntent = new Intent(this, TaskPreviewActivity.class);
        previewIntent.putExtra("pos", taskId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, taskId, previewIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(task.getName())
                .setContentText("Przypominacz")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setTicker("Ticker text")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(taskId, notification);
    }
}