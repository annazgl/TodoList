package com.anna.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.anna.todolist.database.ITaskDatabase;
import com.anna.todolist.model.TodoTask;
import com.anna.todolist.service.TodoNotificationService;

import java.util.Date;
import java.util.List;

public class NotificationsPlanner {
    private ITaskDatabase mTaskDatabase;
    private Context mContext;

    public NotificationsPlanner(ITaskDatabase taskDatabase, Context context) {
        mTaskDatabase = taskDatabase;
        mContext = context;
    }

    public void planNotifications() {
        // 1. Pobrać powiadomienia, które mają włączone przypomnienie- ale tylko z czasem
        // późniejszym niż teraz
        List<TodoTask> tasks = mTaskDatabase.getFutureTasksWithReminder(new Date());

        // 2. Dla tych zaplanować za pomocą AlarmManager'a
        // uruchomienie usługi TodoNotificationService
        AlarmManager alarmManager =
                (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        for (TodoTask task : tasks) {
            Intent serviceIntent = new Intent(mContext, TodoNotificationService.class);
            serviceIntent.putExtra("id", task.getId());

            PendingIntent pendingIntent =
                    PendingIntent.getService(mContext, task.getId(),
                            serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                // Dla systemów poniżej API 19 metoda set działa tak samo
                // jak metoda setExact(ta pojawiła się dopiero w API 19)
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        task.getReminderDate().getTime(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                        task.getReminderDate().getTime(), pendingIntent);
            }
        }
    }
}