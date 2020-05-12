package com.ahmet.maras_belediye.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.ahmet.maras_belediye.MainActivity;
import com.ahmet.maras_belediye.R;
import com.ahmet.maras_belediye.sync.AppReminderIntent;
import com.ahmet.maras_belediye.sync.ReminderTask;

public class NotificationUtilities {
    private static final int    REMINDER_NOTIFICATION_ID         = 1138;
    private static final int    REMINDER_PENDING_INTENT_ID       = 3417;
    private static final String REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserBecauseCharging(Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    REMINDER_NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ok)
                .setLargeIcon(largeIcon(context))
                .setContentTitle("Haberlere baktınız mı?")
                .setContentText("Yeni haberler için tıklayınız.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        ("Yeni haberler için tıklayınız.")))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        notificationBuilder
                .addAction(OpenAppAction(context))
                .addAction(ignoreOpenAction(context));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(REMINDER_NOTIFICATION_ID, notificationBuilder.build());
}
    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private static final int    ACTION_PENDING_INTENT_ID         = 1;

    private static NotificationCompat.Action OpenAppAction(Context context) {

        Intent AppIntent = new Intent(context, AppReminderIntent.class);
        AppIntent.setAction(ReminderTask.ACTION_OPEN_APP);

        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_PENDING_INTENT_ID,
                AppIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action AppAction = new NotificationCompat.Action(R.drawable.eczane,
                "Open app!",
                incrementWaterPendingIntent);

        return AppAction;
    }
    private static final int    ACTION_IGNORE_PENDING_INTENT_ID        = 14;

    private static NotificationCompat.Action ignoreOpenAction(Context context) {

        Intent ignoreReminderIntent = new Intent(context, AppReminderIntent.class);
        ignoreReminderIntent.setAction(ReminderTask.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.sebzemeyve,
                "No, thanks.",
                ignoreReminderPendingIntent);

        return ignoreReminderAction;
    }
    private static Bitmap largeIcon(Context context) {
        Resources res       = context.getResources();
        Bitmap    largeIcon = BitmapFactory.decodeResource(res,
                R.drawable.ok);
        return largeIcon;
    }
}
