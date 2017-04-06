package com.anna.todolist.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.anna.todolist.NotificationsPlanner;
import com.anna.todolist.database.ITaskDatabase;
import com.anna.todolist.database.SqliteTaskDatabase;

public class BootCompleteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ITaskDatabase taskDatabase = new SqliteTaskDatabase(context);

        new NotificationsPlanner(taskDatabase, context).planNotifications();
    }
}
