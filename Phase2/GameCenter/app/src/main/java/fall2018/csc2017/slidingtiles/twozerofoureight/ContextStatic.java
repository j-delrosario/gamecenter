package fall2018.csc2017.slidingtiles.twozerofoureight;

import android.app.Application;
import android.content.Context;

/**
 * An application making the context available globally.
 */
public class ContextStatic extends Application {

    /**
     * A static context.
     */
    private static Context context;

    /**
     * make the context.
     */
    public void onCreate() {
        super.onCreate();
        ContextStatic.context = getApplicationContext();
    }

    /**
     * Get the context.
     * @return context.
     */
    public static Context getAppContext() {
        return ContextStatic.context;
    }
}
