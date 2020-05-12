package com.ahmet.maras_belediye.sync;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class AppReminderIntent extends IntentService {
    public AppReminderIntent() {
        super("AppReminderIntent");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderTask.executeTask(this, action);
    }
}
