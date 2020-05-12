package com.ahmet.maras_belediye.sync;

import android.content.Context;
import android.content.Intent;

import com.ahmet.maras_belediye.MainActivity;
import com.ahmet.maras_belediye.utils.NotificationUtilities;

public class ReminderTask {
    public static final String ACTION_OPEN_APP = "open-app";
    public static final String ACTION_DISMISS_NOTIFICATION  = "dismiss-notification";
    public static final String ACTION_CHARGING_REMINDER     = "charging-reminder";

    public static void executeTask(Context context, String action){
        if (ACTION_OPEN_APP.equals(action)){
            openApp(context);
        }
        else if (ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtilities.clearAllNotifications(context);
        }
        else if (ACTION_CHARGING_REMINDER.equals(action)){
            issueChargingReminder(context);
        }
    }
    private static void openApp(Context context){
        NotificationUtilities.clearAllNotifications(context);
        Intent intent1 = new Intent(context, MainActivity.class);
        context.startActivity(intent1);
    }
    private static void issueChargingReminder(Context context) {
        NotificationUtilities.remindUserBecauseCharging(context);
    }
}
