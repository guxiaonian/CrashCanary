package fairy.easy.crashinfo;

import android.app.Application;

import fairy.easy.crashcanary.CrashCanary;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashCanary.install(this);

    }
}