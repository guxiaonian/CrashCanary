package fairy.easy.crashcanary.crash;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Date;

import fairy.easy.crashcanary.CrashInfoActivity;
import fairy.easy.crashcanary.R;
import fairy.easy.crashcanary.crash.db.CrashBean;
import fairy.easy.crashcanary.crash.db.dao.impl.ICrashDao;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by 谷闹年 on 2019/8/28.
 */
public class CrashHelper implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private final ICrashDao mCrashDao;
    private final Context mContext;
    private int id;

    public CrashHelper(ICrashDao mCrashDao, Context mContext) {
        this.mCrashDao = mCrashDao;
        this.mContext = mContext;
        init();
    }

    private void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }


    private void notification(String name) {
        if (SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        id++;
        Intent intent = new Intent(mContext, CrashInfoActivity.class);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        if (SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default";
            String channelName = "default";
            manager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(mContext, "default")
                .setSmallIcon(R.drawable.crash_canary_notification)
                .setContentTitle(name + " crashed")
                .setContentText("Click for more details")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();

        manager.notify(id, notification);

    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        mCrashDao.addCrashBean(crashBean(throwable));
        notification(throwable.getStackTrace()[2].getFileName().replace(".java", ""));
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (mDefaultCrashHandler != null) {
                mDefaultCrashHandler.uncaughtException(thread, throwable);
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    private CrashBean crashBean(Throwable throwable) {
        long current = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        return new CrashBean(time, throwable.getMessage(), Log.getStackTraceString(throwable), 1);
    }
}
