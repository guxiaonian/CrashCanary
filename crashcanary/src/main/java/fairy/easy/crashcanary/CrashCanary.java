package fairy.easy.crashcanary;

import android.content.Context;

import fairy.easy.crashcanary.crash.CrashFactory;
import fairy.easy.crashcanary.crash.CrashHelper;
import fairy.easy.crashcanary.crash.db.dao.impl.ICrashDao;


/**
 * Created by 谷闹年 on 2019/8/28.
 */
public class CrashCanary {
    private CrashCanary() {
        throw new AssertionError();
    }


    private static ICrashDao crashDao;

    public static void install(Context context) {
        crashDao = new ICrashDao(context);
        Thread.setDefaultUncaughtExceptionHandler(new CrashHelper(new ICrashDao(context), context));
        CrashFactory.setEnabled(context, CrashInfoActivity.class, true);
    }

    public static ICrashDao getCrashDao() {
        return crashDao;
    }
}


