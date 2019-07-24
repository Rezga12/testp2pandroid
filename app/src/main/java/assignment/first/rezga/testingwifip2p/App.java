package assignment.first.rezga.testingwifip2p;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shako on 5/3/19.
 */


public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
